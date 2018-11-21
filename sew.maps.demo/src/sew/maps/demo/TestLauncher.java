/*
 * Sébastien Eon 2013 / CC0-1.0
 */
package sew.maps.demo;

import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.components.registry.BundleRegistry;
import ej.components.registry.util.BundleRegistryHelper;
import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.microui.display.Display;
import ej.mwt.Desktop;
import ej.mwt.Panel;
import ej.style.Stylesheet;
import ej.style.background.PlainBackground;
import ej.style.outline.SimpleOutline;
import ej.widget.composed.Button;
import sew.maps.demo.microui.MapDisplayable;
import sew.maps.demo.mwt.MapComposite;

public class TestLauncher {

	public static final String GOOGLEMAPS = "GoogleMaps";
	public static final String OPENSTREETMAPS = "OpenStreetMaps";
	public static final String OPENSTREETMAPSLITE = "OpenStreetMapsLite";
	public static final String TOMTOM = "TomTom";
	public static final String WHAT3WORDS = "What3Words";

	public static String Components = GOOGLEMAPS;

	public static void main(String[] args) {
		MicroUI.start();

		try {
			// start environment
			BundleRegistry registry = ServiceLoaderFactory.getServiceLoader().getService(BundleRegistry.class);
			BundleRegistryHelper.startupRegistry();

			// start test
			microui(registry);
			// mwt(registry);

			// registry.stop(null);
		} catch (Throwable e) {
			// RegistryInitializationException
			// ClassCastException
			e.printStackTrace();
		}
	}

	public static void microui(BundleRegistry registry) {
		Display display = Display.getDefaultDisplay();
		int displayWidth = display.getWidth();
		int displayHeight = display.getHeight();

		Map map = new Map(displayWidth, displayHeight);
		MapDisplayable mapDisplayable = new MapDisplayable(display, map);
		mapDisplayable.show();

		map.search("Le pas des haies, Grandchamps des Fontaines");

		// ExplicitFlush g = display.getNewExplicitFlush();
		//
		// MapsProvider mapsProvider = registry.getService(MapsProvider.class);
		// // Location[] locations = mapsProvider.search("Paris Nantes")
		// // Location[] locations =
		// // mapsProvider.search("fief de la jarrie, auzay");
		// // Place[] locations = mapsProvider.search("Le pas des haies, Grandchamps des Fontaines");
		// // Place[] locations = mapsProvider.search("Passage Andre cretaux");
		// Place[] locations = mapsProvider.search("41 rue de la patouillerie, Nantes");
		// // Place[] locations = mapsProvider.search("Rue de racape, Nantes");
		// // Place[] locations =
		// // mapsProvider.search("avenue des impressionnistes, Nantes");
		// // Place[] locations =
		// // mapsProvider.search("chemin de launay 44390 Petit mars");
		// // Image map =
		// // mapsProvider.getMap("Le pas des haies Grandchamps des Fontaines",
		// // displayWidth, displayHeight,
		// // 15);
		// // Image map = mapsProvider.getMap(new Coordinates(47.23070620,
		// // -1.55591120), displayWidth, displayHeight, 15);
		// if (locations == null || locations.length == 0) {
		// g.setColor(Colors.WHITE);
		// g.drawString("Not Found", displayWidth / 2, displayHeight / 2, GraphicsContext.HCENTER
		// | GraphicsContext.VCENTER);
		// } else {
		// Image map = mapsProvider.getMap(locations[0], displayWidth, displayHeight, 15, MapType.ROAD);
		//
		// g.drawImage(map, 0, 0, 0);
		// }
		// g.flush();
	}

	public static void mwt(BundleRegistry registry) {
		Stylesheet stylesheet = ServiceLoaderFactory.getServiceLoader().getService(Stylesheet.class);
		SimpleStyle transparentStyle = new SimpleStyle();
		PlainBackground transparentBackground = new PlainBackground();
		transparentStyle.setBackground(transparentBackground);
		stylesheet.setStyle(transparentStyle);

		SimpleStyle buttonStyle = new SimpleStyle();
		PlainBackground buttonBackground = new PlainBackground();
		buttonBackground.setColor(Colors.GRAY);
		buttonStyle.setBackground(buttonBackground);
		SimpleOutline buttonOutline = new SimpleOutline();
		buttonOutline.setThickness(5);
		buttonStyle.setPadding(buttonOutline);
		// buttonStyle.setForegroundColor(Colors.BLACK);
		stylesheet.setStyle(Button.class, buttonStyle);
		//
		// SimpleStyle labelStyle = new SimpleStyle();
		// labelStyle.setForegroundColor(Colors.BLACK);
		// stylesheet.setStyle(Label.class, labelStyle);

		Display display = Display.getDefaultDisplay();
		int displayWidth = display.getWidth();
		int displayHeight = display.getHeight();

		Map map = new Map(displayWidth, displayHeight);

		Desktop desktop = new Desktop(display);
		Panel panel = new Panel();
		MapComposite mapComposite = new MapComposite(map);

		panel.setWidget(mapComposite);
		panel.show(desktop, true);
		desktop.show();

		map.search("Le pas des haies, Grandchamps des Fontaines");
	}
}
