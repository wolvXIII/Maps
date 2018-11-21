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
import ej.widget.Button;
import ej.widget.Label;
import sew.maps.demo.Map;

/**
 *
 */
public class MapComposite extends Composite implements Observer {

	private final Map map;
	private final Button minusButton;
	private final Button plusButton;

	public MapComposite(Map map) {
		this.map = map;
		this.minusButton = new Button();
		this.minusButton.setWidget(new Label("-"));
		add(this.minusButton);
		this.plusButton = new Button();
		this.plusButton.setWidget(new Label("+"));
		add(this.plusButton);
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
		System.out.println("MapComposite.validate() " + widthHint + " " + heightHint);
		this.minusButton.validate(MWT.NONE, MWT.NONE);
		this.plusButton.validate(MWT.NONE, MWT.NONE);

		if (widthHint == MWT.NONE) {
			widthHint = this.minusButton.getPreferredWidth() + this.plusButton.getPreferredWidth();
		}
		if (heightHint == MWT.NONE) {
			heightHint = Math.max(this.minusButton.getPreferredHeight(), this.plusButton.getPreferredHeight());
		}

		setPreferredSize(widthHint, heightHint);
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		int minusWidth = this.minusButton.getPreferredWidth();
		int minusHeight = this.minusButton.getPreferredHeight();
		this.minusButton.setBounds(width - minusWidth, height - minusHeight, minusWidth, minusHeight);
		int plusWidth = this.plusButton.getPreferredWidth();
		int plusHeight = this.plusButton.getPreferredHeight();
		this.plusButton.setBounds(width - plusWidth, 0, plusWidth, plusHeight);
		super.setBounds(x, y, width, height);
	}

	@Override
	public void update(Observable o, Object arg) {
		repaint();
	}

}
