/*
 * Sébastien Eon 2013 / CC0-1.0
 */
package sew.maps.google;

import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.components.registry.BundleActivator;
import ej.components.registry.BundleRegistry;
import sew.maps.MapsProvider;

public class Bundle implements BundleActivator {

	public void initialize() {
		GoogleMapsProvider mapsProvider = new GoogleMapsProvider();
		ServiceLoaderFactory.getServiceLoader().getService(BundleRegistry.class).register(MapsProvider.class,
				mapsProvider);
	}

	public void link() {
	}

	public void start() {
	}

	public void stop() {
	}

}
