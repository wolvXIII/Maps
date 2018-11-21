/*
 * Sébastien Eon 2013 / CC0-1.0
 */
package sew.maps;

/**
 *
 */
public interface Place {

	/**
	 * @return the coordinates.
	 */
	Coordinates getCoordinates();

	/**
	 * @return the address components.
	 */
	AddressComponent[] getAddressComponents();

	/**
	 * @return the formatted address.
	 */
	String getFormattedAddress();

}