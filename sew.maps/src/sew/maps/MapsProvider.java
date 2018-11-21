/*
 * Sébastien Eon 2013 / CC0-1.0
 */
package sew.maps;

import ej.microui.display.Image;

public interface MapsProvider {

	public enum MapType {
		ROAD, TERRAIN, SATELLITE, HYBRID
	};

	Image getMap(Place location, int width, int height, int zoom, MapType type);

	Image getMap(String address, int width, int height, int zoom, MapType type);

	Image getMap(Coordinates coordinates, int width, int height, int zoom, MapType type);

	Place[] search(String research);

	String getProvider();

}
