/*
 * Sébastien Eon 2013 / CC0-1.0
 */
package sew.maps.openstreetmaps;

import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.components.registry.BundleActivator;
import ej.components.registry.BundleRegistry;
import sew.maps.MapsProvider;

public class Bundle implements BundleActivator {

	@Override
	public void initialize() {
		OpenStreetMapsProvider mapsProvider = new OpenStreetMapsProvider();
		ServiceLoaderFactory.getServiceLoader().getService(BundleRegistry.class).register(MapsProvider.class,
				mapsProvider);
	}

	@Override
	public void link() {
	}

	@Override
	public void start() {
	}

	@Override
	public void stop() {
	}

}
