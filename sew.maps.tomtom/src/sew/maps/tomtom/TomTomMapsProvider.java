/*
 * Sébastien Eon 2013 / CC0-1.0
 */
package sew.maps.tomtom;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParserException;

import sew.rest.StreamUtilities;

import ej.ecom.io.Connector;
import ej.ecom.io.StreamConnection;
import ej.microui.display.Image;
import sew.maps.Coordinates;
import sew.maps.MapsProvider;
import sew.maps.Place;

public class TomTomMapsProvider implements MapsProvider {

	private static final String KEY = "2g5777wn63zsgra3msm4fmnv";

	public static final boolean DEBUG = true;

	@Override
	public Image getMap(Place place, int width, int height, int zoom, MapType type) {
		return getMap(place.getCoordinates(), width, height, zoom, type);
	}

	@Override
	public Image getMap(String location, int width, int height, int zoom, MapType type) {
		return null; // TODO
	}

	@Override
	public Image getMap(Coordinates coordinates, int width, int height, int zoom, MapType type) {
		// https://api.tomtom.com/lbs/map/3/basic/1/10/47/-11.png?key=2g5777wn63zsgra3msm4fmnv
		String request = new StringBuffer("https://api.tomtom.com/lbs/map/3/basic/").append(getMapTypeString(type))
				.append('/').append(zoom).append('/').append(coordinates.getLatitude()).append('/')
				.append(coordinates.getLongitude()).append(".png?key=").append(KEY).toString();
		try {
			StreamConnection connection = (StreamConnection) Connector.open(request);
			InputStream inputStream = connection.openInputStream();
			if (DEBUG) {
				byte[] result = StreamUtilities.readFully(inputStream);
				System.out.println("Search result:\n" + new String(result));
				inputStream = new ByteArrayInputStream(result);
			}

			Image image = Image.createImage(inputStream, Image.PNG);
			return image;
		} catch (IOException e) {
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
		case SATELLITE:
		case HYBRID:
			return "1-hybrid";
		case TERRAIN:
		case ROAD:
			return "1";
		default:
			// cannot occur since using enums! (and managing all values)
			throw new RuntimeException();
		}
	}

	@Override
	public Place[] search(String research) {
		// https://api.tomtom.com/lbs/services/geocode/4/geocode?query=le+pas+des+haies&key=2g5777wn63zsgra3msm4fmnv
		String request = new RequestBuffer("https://api.tomtom.com/lbs/services/geocode/4/geocode")
				// replace accented characters
				.append("query", research.replace(' ', '+')).append("format", "xml") // TODO manage JSON
				.append("key", KEY).toString();
		if (DEBUG) {
			System.out.println("Request:\n" + request);
		}
		try {
			StreamConnection connection = (StreamConnection) Connector.open(request);
			InputStream inputStream = connection.openInputStream();
			if (DEBUG) {
				byte[] result = StreamUtilities.readFully(inputStream);
				System.out.println("Search result:\n" + new String(result));
				inputStream = new ByteArrayInputStream(result);
			}

			return new XMLReader().readSearchResult(inputStream);
		} catch (IOException e) {
			if (DEBUG) {
				e.printStackTrace();
			}
		} catch (XmlPullParserException e) {
			if (DEBUG) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public String getProvider() {
		return "Google Maps";
	}

}
