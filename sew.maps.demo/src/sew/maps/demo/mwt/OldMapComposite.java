/*
 * Sébastien Eon 2013 / CC0-1.0
 */
package sew.maps.demo.mwt;

import java.util.Observable;
import java.util.Observer;

import ej.microui.display.Colors;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Image;
import ej.mwt.Composite;
import ej.mwt.MWT;
import sew.maps.demo.Map;

/**
 *
 */
public class OldMapComposite extends Composite implements Observer {

	private final Map map;
	private final ZoomCursor zoomCursor;

	public OldMapComposite(Map map) {
		this.map = map;
		this.zoomCursor = new ZoomCursor(0, 18);
		add(this.zoomCursor);
		map.addObserver(this);
	}

	@Override
	public void render(GraphicsContext g) {
		Image image = this.map.getImage();
		if (image == null) {
			int width = getWidth();
			int height = getHeight();
			g.setColor(Colors.BLACK);
			g.fillRect(0, 0, width, height);
			g.setColor(Colors.WHITE);
			g.drawString(this.map.getMessage(), width / 2, height / 2,
					GraphicsContext.HCENTER | GraphicsContext.VCENTER);
		} else {
			g.drawImage(image, 0, 0, GraphicsContext.LEFT | GraphicsContext.TOP);
		}
	}

	@Override
	public void validate(int widthHint, int heightHint) {
		this.zoomCursor.validate(MWT.NONE, MWT.NONE);
		System.out.println("MapComposite.validate() " + widthHint + " " + heightHint);

		if (widthHint == MWT.NONE) {
			widthHint = this.zoomCursor.getPreferredWidth();
		}
		if (heightHint == MWT.NONE) {
			heightHint = this.zoomCursor.getPreferredHeight();
		}

		setPreferredSize(widthHint, heightHint);
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		int cursorWidth = this.zoomCursor.getPreferredWidth();
		int cursorHeight = this.zoomCursor.getPreferredHeight();
		this.zoomCursor.setBounds(width - cursorWidth - 5, 5, cursorWidth, cursorHeight);
		super.setBounds(x, y, width, height);
	}

	@Override
	public void update(Observable o, Object arg) {
		repaint();
	}

}
