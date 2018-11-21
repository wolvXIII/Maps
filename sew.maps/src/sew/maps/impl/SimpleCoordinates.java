/*
 * Sébastien Eon 2013 / CC0-1.0
 */
package sew.maps.impl;

import sew.maps.Coordinates;

public class SimpleCoordinates implements Coordinates {

	private final double latitude;
	private final double longitude;

	public SimpleCoordinates(double latitude, double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}

	@Override
	public double getLatitude() {
		return this.latitude;
	}

	@Override
	public double getLongitude() {
		return this.longitude;
	}

	@Override
	public String toString() {
		return new StringBuffer().append(this.latitude).append(',').append(this.longitude).toString();
	}

}
