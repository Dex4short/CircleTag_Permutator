package system.ui.panels;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import marker.CircleTag;

public class PreviewPanel extends JPanel{
	private static final long serialVersionUID = 2207551773110759971L;
	private CircleTag circle_tag;

	public PreviewPanel() {
		circle_tag = new CircleTag();
	}
	
	private Graphics2D g2d;
	@Override
	public void paint(Graphics g) {
		g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		circle_tag.draw(g2d,
				(getWidth()/2) - (circle_tag.getTagSize()/2),
				(getHeight()/2) - (circle_tag.getTagSize()/2)
		);
	}
	public void setCircleTag(CircleTag circle_tag) {
		this.circle_tag = circle_tag;
		getRootPane().repaint();
	}
	public CircleTag getCircleTag() {
		return circle_tag;
	}
}
