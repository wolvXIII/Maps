/*
 * Sébastien Eon 2013 / CC0-1.0
 */
package sew.maps.demo.microui;

import java.util.Observable;
import java.util.Observer;

import ej.microui.display.Colors;
import ej.microui.display.Display;
import ej.microui.display.Displayable;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Image;
import ej.microui.util.EventHandler;
import sew.maps.demo.Map;
import sew.maps.demo.MapController;

/**
 *
 */
public class MapDisplayable extends Displayable implements Observer {

	private final Map map;
	private final MapController mapController;

	public MapDisplayable(Display display, Map map) {
		super(display);
		this.map = map;
		map.addObserver(this);
		this.mapController = new MapController(map);
	}

	@Override
	public void paint(GraphicsContext g) {
		Image image = this.map.getImage();
		if (image == null) {
			Display display = getDisplay();
			int displayWidth = display.getWidth();
			int displayHeight = display.getHeight();
			g.setColor(Colors.BLACK);
			g.fillRect(0, 0, displayWidth, displayHeight);
			g.setColor(Colors.WHITE);
			g.drawString(this.map.getMessage(), displayWidth / 2, displayHeight / 2,
					GraphicsContext.HCENTER | GraphicsContext.VCENTER);
		} else {
			g.drawImage(image, 0, 0, GraphicsContext.LEFT | GraphicsContext.TOP);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		repaint();
	}

	@Override
	public EventHandler getController() {
		return this.mapController;
	}

}
