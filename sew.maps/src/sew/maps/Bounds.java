/*
 * Sébastien Eon 2013 / CC0-1.0
 */
package sew.maps;

/**
 *
 */
public interface Bounds {

	/**
	 * @return the nort east coordinates.
	 */
	Coordinates getNortEast();

	/**
	 * @return the south west coordinates.
	 */
	Coordinates getSouthWest();

	Coordinates getCenter();

}