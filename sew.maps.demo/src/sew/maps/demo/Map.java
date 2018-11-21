/*
 * Sébastien Eon 2013 / CC0-1.0
 */
package sew.maps.demo;

import java.util.Observable;

import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.components.registry.BundleRegistry;
import ej.microui.display.Image;
import sew.maps.MapsProvider;
import sew.maps.Place;
import sew.maps.MapsProvider.MapType;

/**
 *
 */
public class Map extends Observable {

	private final int width;
	private final int height;

	private int zoom;
	private final MapType type;

	private Place place;
	private Image image;
	private String message;

	public Map(int width, int height) {
		this.width = width;
		this.height = height;
		this.zoom = 10;
		this.type = MapType.TERRAIN;
	}

	public void search(String research) {
		setMessage("Searching " + research);
		MapsProvider mapsProvider = getMapsProvider();
		Place[] places = mapsProvider.search(research);
		if (places != null && places.length != 0) {
			this.place = places[0];
			updateMap(mapsProvider);
		} else {
			this.image = null;
			setChanged();
			notifyObservers();
		}
	}

	private MapsProvider getMapsProvider() {
		BundleRegistry registry = ServiceLoaderFactory.getServiceLoader().getService(BundleRegistry.class);
		MapsProvider mapsProvider = registry.getService(MapsProvider.class);
		return mapsProvider;
	}

	private void updateMap(MapsProvider mapsProvider) {
		setMessage("Downloading map at (" + this.place.getCoordinates() + "), zoom: " + this.zoom);
		this.image = mapsProvider.getMap(this.place, this.width, this.height, this.zoom, this.type);
		setChanged();
		notifyObservers();
	}

	public void zoomIn() {
		if (this.zoom < 18) {
			this.zoom++;
			updateMap(getMapsProvider());
		}
	}

	public void zoomOut() {
		if (this.zoom > 0) {
			this.zoom--;
			updateMap(getMapsProvider());
		}
	}

	/**
	 * Sets the message.
	 *
	 * @param message
	 *            the message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
		setChanged();
		notifyObservers();
	}

	/**
	 * Gets the message.
	 *
	 * @return the message.
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * Gets the image.
	 *
	 * @return the image.
	 */
	public Image getImage() {
		return this.image;
	}

}
