/*
 * Sébastien Eon 2013 / CC0-1.0
 */
package sew.maps.openstreetmapslite;

import java.io.IOException;
import java.io.InputStream;

import ej.ecom.io.Connector;
import ej.ecom.io.StreamConnection;
import ej.microui.display.Image;
import sew.maps.Coordinates;
import sew.maps.MapsProvider;
import sew.maps.Place;

public class OpenStreetMapsLiteProvider implements MapsProvider {

	public static final boolean DEBUG = true;

	@Override
	public Image getMap(Place location, int width, int height, int zoom, MapType type) {
		return getMap(location.getCoordinates(), width, height, zoom, type);
	}

	@Override
	public Image getMap(String location, int width, int height, int zoom, MapType type) {
		return null; // TODO
	}

	@Override
	public Image getMap(Coordinates coordinates, int width, int height, int zoom, MapType type) {
		// http://ojw.dev.openstreetmap.org/StaticMap/?lat=39.096216&lon=-94.55383&z=11&layer=cloudmade_2&mode=Export&show=1
		String request = new RequestBuffer("http://staticmap.openstreetmap.de/staticmap.php")
				.append("center", coordinates.toString()).append("size", width + "x" + height).append("zoom", zoom)
				.append("maptype", "mapnik").toString();
		if (DEBUG) {
			System.out.println("Request: " + request);
		}
		try {
			StreamConnection connection = (StreamConnection) Connector.open(request);
			InputStream inputStream = connection.openInputStream();

			Image image = Image.createImage(inputStream, Image.PNG);
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
		return null;
	}

	@Override
	public String getProvider() {
		return "Open Street Maps Lite";
	}

}
