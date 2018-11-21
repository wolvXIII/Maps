/*
 * Sébastien Eon 2013 / CC0-1.0
 */
package sew.maps.openstreetmaps;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

import sew.rest.Request;

import ej.microui.display.Image;
import sew.maps.Coordinates;
import sew.maps.MapsProvider;
import sew.maps.Place;
import sew.maps.impl.SimpleCoordinates;
import sew.maps.impl.SimplePlace;

public class OpenStreetMapsProvider implements MapsProvider {

	private static final String KEY = "bOAEUEQzNlXx7Fm8Mq60VCe4AYN5EbUl";
	public static final boolean DEBUG = true;

	@Override
	public Image getMap(Place place, int width, int height, int zoom, MapType type) {
		return getMap(place.getCoordinates(), width, height, zoom, type);
	}

	@Override
	public Image getMap(String place, int width, int height, int zoom, MapType type) {
		Place[] searchResult = search(place);
		if (searchResult != null && searchResult.length != 0) {
			return getMap(searchResult[0].getCoordinates(), width, height, zoom, type);
		}
		return null;
	}

	@Override
	public Image getMap(Coordinates coordinates, int width, int height, int zoom, MapType type) {
		// http://ojw.dev.openstreetmap.org/StaticMap/?lat=39.096216&lon=-94.55383&z=11&layer=cloudmade_2&mode=Export&show=1
		Request request = new Request("http://open.mapquestapi.com/staticmap/v4/getmap").append("key", KEY)
				.append("center", coordinates.getLatitude() + "," + coordinates.getLongitude())
				.append("size", width + "," + height).append("zoom", zoom).append("imagetype", "png")
				.append("type", getMapTypeString(type));
		// String request = new RequestBuffer("http://ojw.dev.openstreetmap.org/StaticMap/")
		// .append("lat", coordinates.getLatitude()).append("lon", coordinates.getLongitude()).append("w", width)
		// .append("h", height).append("z", zoom).append("layer", "cloudmode_2").append("mode", "Export")
		// .append("show", 1).toString();
		try {
			byte[] result = request.send();

			Image image = Image.createImage(new ByteArrayInputStream(result), result.length);
			return image;
		} catch (IOException e) {
			if (DEBUG) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Place[] search(String research) {
		// http://nominatim.openstreetmap.org/search?format=xml&q=le%20pas%20des%20haies%20grandchamp
		Request request = new Request("http://nominatim.openstreetmap.org/search").append("format", "json").append("q",
				research);
		try {
			byte[] result = request.send();

			JSONArray root = new JSONArray(new String(result));
			int resultsLength = root.length();
			if (resultsLength > 0) {
				JSONObject placeObject = root.getJSONObject(0);
				double latitude = Double.parseDouble(placeObject.getString("lat"));
				double longitude = Double.parseDouble(placeObject.getString("lon"));
				String address = placeObject.getString("display_name");
				Place place = new SimplePlace(new SimpleCoordinates(latitude, longitude), address, null);
				return new Place[] { place };
			}

			return null;
		} catch (IOException | JSONException e) {
			if (DEBUG) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Gets the type name.
	 *
	 * @param type
	 *            the type
	 *
	 * @return the name of the requested type.
	 */
	private String getMapTypeString(MapType type) {
		switch (type) {
		case TERRAIN:
			return "map";
		case HYBRID:
			return "hyb";
		case ROAD:
			return "map";
		case SATELLITE:
			return "sat";
		default:
			// cannot occur since using enums! (and managing all values)
			throw new RuntimeException();
		}
	}

	@Override
	public String getProvider() {
		return "Open Street Maps";
	}

}
