/*
 * Sébastien Eon 2013 / CC0-1.0
 */
package sew.maps.impl;

import sew.maps.AddressComponent;

public class SimpleAddressComponent implements AddressComponent {

	private final String shortName;
	private final String longName;
	private final String[] types;

	public SimpleAddressComponent(String name) {
		super();
		this.shortName = name;
		this.longName = name;
		this.types = null; // TODO split name
	}

	public SimpleAddressComponent(String shortName, String longName, String[] types) {
		super();
		this.shortName = shortName;
		this.longName = longName;
		this.types = types;
	}

	/**
	 * @return the short name.
	 */
	@Override
	public String getShortName() {
		return this.shortName;
	}

	/**
	 * @return the long name.
	 */
	@Override
	public String getLongName() {
		return this.longName;
	}

	/**
	 * @return the types.
	 */
	@Override
	public String[] getTypes() {
		return this.types;
	}

}
