/*
 * Sébastien Eon 2013 / CC0-1.0
 */
package sew.maps.openstreetmapslite;

import ej.components.BundleActivator;
import ej.components.RegistryFactory;
import sew.maps.MapsProvider;

public class Bundle implements BundleActivator {

	@Override
	public void initialize(String parameters) {
		OpenStreetMapsLiteProvider mapsProvider = new OpenStreetMapsLiteProvider();
		RegistryFactory.getRegistry().register(MapsProvider.class, mapsProvider);
	}

	@Override
	public void link(String parameters) {
	}

	@Override
	public void start(String parameters) {
	}

	@Override
	public void stop(String parameters) {
	}

}
