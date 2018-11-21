/*
 * Sébastien Eon 2013 / CC0-1.0
 */
package sew.maps.tomtom;

import ej.components.BundleActivator;
import ej.components.RegistryFactory;
import sew.maps.MapsProvider;

public class Bundle implements BundleActivator {

	public void initialize(String parameters) {
		TomTomMapsProvider mapsProvider = new TomTomMapsProvider();
		RegistryFactory.getRegistry().register(MapsProvider.class, mapsProvider);
	}

	public void link(String parameters) {
	}

	public void start(String parameters) {
	}

	public void stop(String parameters) {
	}

}
