/*
 * Sébastien Eon 2013 / CC0-1.0
 */
package sew.maps.demo;

import ej.microui.event.Event;
import ej.microui.event.generator.Command;
import ej.microui.event.generator.Pointer;
import ej.microui.util.EventHandler;

/**
 *
 */
public class MapController implements EventHandler {

	private final Map map;

	public MapController(Map map) {
		this.map = map;
	}

	@Override
	public boolean handleEvent(int event) {
		if (Event.getType(event) == Event.COMMAND) {
			switch (Event.getData(event)) {
			case Command.UP:
				this.map.zoomIn();
				break;
			case Command.DOWN:
				this.map.zoomOut();
				break;
			default:
				this.map.zoomIn();
				break;
			}
		}
		if (Event.getType(event) == Event.POINTER) {
			switch (Pointer.getAction(event)) {
			case Pointer.PRESSED:
				this.map.zoomIn();
				break;
			// case Command.DOWN:
			// this.map.zoomOut();
			// break;
			// default:
			// this.map.zoomIn();
			// break;
			}
		}
		return false;
	}

}
