/*
 * Sébastien Eon 2013 / CC0-1.0
 */
package sew.maps.impl;

import sew.maps.AddressComponent;
import sew.maps.Coordinates;
import sew.maps.Place;

public class SimplePlace implements Place {

	private final Coordinates coordinates;
	private final AddressComponent[] addressComponents;
	private final String formattedAddress;

	/**
	 * @param coordinates
	 * @param formattedAddress
	 * @param addressComponents
	 */
	public SimplePlace(Coordinates coordinates, String formattedAddress, AddressComponent[] addressComponents) {
		super();
		this.coordinates = coordinates;
		this.addressComponents = addressComponents;
		this.formattedAddress = formattedAddress;
	}

	/**
	 * @return the coordinates.
	 */
	@Override
	public Coordinates getCoordinates() {
		return this.coordinates;
	}

	/**
	 * @return the address components.
	 */
	@Override
	public AddressComponent[] getAddressComponents() {
		return this.addressComponents;
	}

	/**
	 * @return the formatted address.
	 */
	@Override
	public String getFormattedAddress() {
		return this.formattedAddress;
	}

}
