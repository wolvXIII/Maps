/*
 * Sébastien Eon 2013 / CC0-1.0
 */
package sew.maps.google;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

import sew.rest.Request;

import ej.microui.display.Image;
import sew.maps.AddressComponent;
import sew.maps.Coordinates;
import sew.maps.MapsProvider;
import sew.maps.Place;
import sew.maps.impl.SimpleAddressComponent;
import sew.maps.impl.SimpleCoordinates;
import sew.maps.impl.SimplePlace;

public class GoogleMapsProvider implements MapsProvider {

	@Override
	public Image getMap(Place place, int width, int height, int zoom, MapType type) {
		return getMap(place.getCoordinates(), width, height, zoom, type);
	}

	@Override
	public Image getMap(Coordinates coordinates, int width, int height, int zoom, MapType type) {
		return getMap(coordinates.toString(), width, height, zoom, type);
	}

	@Override
	public Image getMap(String location, int width, int height, int zoom, MapType type) {
		Request request = new Request("http://maps.googleapis.com/maps/api/staticmap")
				.append("size", width + "x" + height).append("center", location.replace(' ', '+')).append("zoom", zoom)
				.append("maptype", getMapTypeString(type)).append("sensor", false).append("format", "png");
		try {
			byte[] bytes = request.send();

			Image image = Image.createImage(new ByteArrayInputStream(bytes), bytes.length);
			return image;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets the type name for google maps API.
	 *
	 * @param type
	 *            the type
	 *
	 * @return the name of the requested type.
	 */
	private String getMapTypeString(MapType type) {
		switch (type) {
		case TERRAIN:
			return "terrain";
		case HYBRID:
			return "hybrid";
		case ROAD:
			return "road";
		case SATELLITE:
			return "satellite";
		default:
			// cannot occur since using enums! (and managing all values)
			throw new RuntimeException();
		}
	}

	@Override
	public Place[] search(String research) {
		Request request = new Request("http://maps.google.com/maps/api/geocode/json").append("address", research)
				.append("sensor", false);
		try {
			String resultString = new String(request.send());

			JSONObject jsonObject = new JSONObject(resultString);
			JSONArray results = jsonObject.getJSONArray("results");
			int resultsLength = results.length();
			if (resultsLength > 0) {
				JSONObject firstResult = results.getJSONObject(0);
				Place place = readPlace(firstResult);
				return new Place[] { place };
			} // else no result found

		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Place readPlace(JSONObject placeObject) throws JSONException {
		JSONArray addressComponentsArray = placeObject.getJSONArray("address_components");
		int addressComponentsLength = addressComponentsArray.length();
		AddressComponent[] addressComponents = new AddressComponent[addressComponentsLength];
		for (int i = -1; ++i < addressComponentsLength;) {
			JSONObject addressComponentObject = addressComponentsArray.getJSONObject(i);
			AddressComponent addressComponent = readAddressComponent(addressComponentObject);
			addressComponents[i] = addressComponent;
		}
		String formattedAddress = placeObject.getString("formatted_address");
		JSONObject geometryObject = placeObject.getJSONObject("geometry");
		JSONObject locationObject = geometryObject.getJSONObject("location");
		float latitude = Float.parseFloat(locationObject.getString("lat"));
		float longitude = Float.parseFloat(locationObject.getString("lng"));
		Place place = new SimplePlace(new SimpleCoordinates(latitude, longitude), formattedAddress, addressComponents);
		return place;
	}

	private AddressComponent readAddressComponent(JSONObject addressComponentObject) throws JSONException {
		String longName = addressComponentObject.getString("long_name");
		String shortName = addressComponentObject.getString("short_name");
		JSONArray typesArray = addressComponentObject.getJSONArray("types");
		int typesLength = typesArray.length();
		String[] types = new String[typesLength];
		for (int j = -1; ++j < typesLength;) {
			String type = typesArray.getString(j);
			types[j] = type;
		}
		AddressComponent addressComponent = new SimpleAddressComponent(shortName, longName, types);
		return addressComponent;
	}

	// @Override
	// public Place[] search(String research) {
	// // http://maps.google.com/maps/api/geocode/json?address=le%20pas%20des%20haies%20grandchamp&sensor=false
	// String request = new RequestBuffer("http://maps.google.com/maps/api/geocode/xml")
	// // TODO manage JSON
	// // replace accented characters
	// .append("address", research.replace(' ', '+')).append("sensor", false).toString();
	// try {
	// if (DEBUG) {
	// System.out.println("Request: " + request);
	// }
	// URL url = new URL(request);
	// HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	// connection.setRequestMethod("GET");
	// connection.connect();
	// InputStream inputStream = connection.getInputStream();
	// if (DEBUG) {
	// byte[] result = StreamUtilities.readFully(inputStream);
	// System.out.println("Search result:\n" + new String(result));
	// inputStream = new ByteArrayInputStream(result);
	// }
	//
	// return new XMLReader().readSearchResult(inputStream);
	// } catch (IOException e) {
	// if (DEBUG) {
	// e.printStackTrace();
	// }
	// } catch (XmlPullParserException e) {
	// if (DEBUG) {
	// e.printStackTrace();
	// }
	// }
	// return null;
	// }

	@Override
	public String getProvider() {
		return "Google Maps";
	}

}
