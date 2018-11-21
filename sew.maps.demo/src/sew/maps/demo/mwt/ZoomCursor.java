/*
 * Sébastien Eon 2013 / CC0-1.0
 */
package sew.maps.demo.mwt;

import ej.microui.display.Colors;
import ej.microui.display.GraphicsContext;
import ej.microui.display.shape.AntiAliasedShapes;
import ej.mwt.MWT;
import ej.mwt.Widget;

/**
 *
 */
public class ZoomCursor extends Widget {

	private static final int WIDTH = 12;
	private static final int MIN_STEPS_SHIFT = 5;
	private final int min;
	private final int max;

	public ZoomCursor(int min, int max) {
		this.min = min;
		this.max = max;
	}

	@Override
	public void render(GraphicsContext g) {
		int width = getWidth();
		int height = getHeight();

		AntiAliasedShapes aliasedShapes = AntiAliasedShapes.Singleton;
		aliasedShapes.setThickness(1);
		g.setColor(Colors.GRAY);
		g.drawVerticalLine(width / 2, 0, height);
		g.setColor(Colors.SILVER);
		g.drawVerticalLine(width / 2 + 1, 1, height);

		int stepsCount = Math.min(this.max - this.min + 1, (height - 2) / MIN_STEPS_SHIFT + 1);
		System.out
				.println("ZoomCursor.render() " + stepsCount + " " + getHeight() + " " + getHeight() / MIN_STEPS_SHIFT);
		for (int i = -1; ++i <= stepsCount;) {
			int currentY = (height - 2) * i / stepsCount;
			System.out.println("ZoomCursor. currentX " + currentY);
			g.setColor(Colors.GRAY);
			g.drawHorizontalLine(0, currentY, width);
			g.setColor(Colors.SILVER);
			g.drawHorizontalLine(1, currentY + 1, width);
		}
	}

	@Override
	public void validate(int widthHint, int heightHint) {
		if (widthHint == MWT.NONE) {
			widthHint = WIDTH;
		}
		if (heightHint == MWT.NONE) {
			heightHint = (this.max - this.min + 1) * MIN_STEPS_SHIFT + 2;
		}
		setPreferredSize(widthHint, heightHint);
	}

}
