/*
 * Sébastien Eon 2013 / CC0-1.0
 */
package sew.maps.impl;

import sew.maps.Bounds;
import sew.maps.Coordinates;

public class SimpleBounds implements Bounds {

	private final Coordinates northEast;
	private final Coordinates southWest;

	public SimpleBounds(Coordinates northEast, Coordinates southWest) {
		this.northEast = northEast;
		this.southWest = southWest;
	}

	/**
	 * @return the nort east coordinates.
	 */
	@Override
	public Coordinates getNortEast() {
		return this.northEast;
	}

	/**
	 * @return the south west coordinates.
	 */
	@Override
	public Coordinates getSouthWest() {
		return this.southWest;
	}

	@Override
	public Coordinates getCenter() {
		return new SimpleCoordinates(Math.abs(this.northEast.getLatitude() - this.southWest.getLatitude()) / 2,
				Math.abs(this.northEast.getLongitude() - this.southWest.getLongitude()) / 2);
	}

}
