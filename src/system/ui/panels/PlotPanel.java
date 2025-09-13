package system.ui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.border.BevelBorder;

public class PlotPanel extends JPanel{
	private static final long serialVersionUID = -2564040508357132866L;
	private int i, counter[], pltX, plotLength, plotHeight, highest;
	private double plotScale;
	private JScrollBar scrollBar;
	private Graphics2D g2d;

	public PlotPanel(int plotLength) {
		this.plotLength = plotLength;
		plotScale = 1;
		highest = 0;
		
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		
		initializeCounters();
		initializeScrollBar();
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				if(getRootPane() != null) getRootPane().repaint();
			}
		});
	}
	
	@Override
	public void paint(Graphics g) {
		plotHeight = getHeight() - scrollBar.getHeight() - 5;
		
		super.paint(g);
		
		g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, getWidth(), plotHeight);
		
		for(i=0; i<counter.length; i++) {
			if(counter[i] == 0) {
				g2d.setColor(Color.lightGray);
				g2d.drawLine(pltX + i, 0, pltX + i, plotHeight);
				continue;
			}

			g2d.setColor(Color.green);
			if(highest < counter[i]) {
				highest = counter[i];
				if(counter[i] > (getHeight()-50)) {
					plotScale = (getHeight()-50d) / highest; 
				}
			}
			else if(highest < (getHeight()-50)) {
				plotScale = 1;
			}
			g2d.drawLine(pltX + i, plotHeight, pltX + i, plotHeight - (int)Math.round(counter[i] * plotScale));
		}
	}
	
	public void plotMarkerId(int markerId) {
		counter[plotLength + markerId]++;
	}
	
	public void setCounters(int counter[]) {
		for(int i=0; i< counter.length; i++) {
			this.counter[i] = counter[i];
		}
	}
	
	private void initializeCounters() {
		counter = new int[(plotLength * 2) + 1];
	}
	
	private void initializeScrollBar() {
		scrollBar = new JScrollBar(JScrollBar.HORIZONTAL);
		scrollBar.setMinimum(0);
		scrollBar.setMaximum(counter.length);
		scrollBar.addAdjustmentListener( e -> {
			pltX = -e.getValue();
			if(getRootPane() != null) getRootPane().repaint();
		});
		add(scrollBar, BorderLayout.SOUTH);
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				scrollBar.setVisibleAmount(getWidth());
				scrollBar.setValue((counter.length/2) - (getWidth()/2));
			}
		});
	}
	
}
