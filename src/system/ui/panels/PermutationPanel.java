package system.ui.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import io.FileHandling;
import marker.CircleMark;
import marker.CircleTag;
import marker.enums.CardinalDirection;
import marker.enums.DiscreteSize;
import marker.enums.Polarity;
import system.ui.table.PermutationTable;

public abstract class PermutationPanel extends JPanel{
	private static final long serialVersionUID = -5996962255502758721L;
	private int next;
	private JButton btn;
	private JLabel lbl;
	private PermutationTable table;
	private PlotPanel plot;

	public PermutationPanel(String parentFolderUrl) {
		
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder("Permutation"));
		
		JPanel 
		headerPanel = headerPanel(),
		bodyPanel = bodyPanel(parentFolderUrl, 3999);
		
		add(headerPanel, BorderLayout.NORTH);
		add(bodyPanel, BorderLayout.CENTER);
	
	}
	
	public abstract void onRun();
	public abstract void onStop();
	public abstract void onNextCircleTag(CircleTag circle_tag);
	
	public PermutationTable getPermutationTable() {
		return table;
	}
	
	private String state[][];
	private JPanel headerPanel() {
		JPanel headerPanel =  new JPanel(new FlowLayout(FlowLayout.LEFT));

		state = new String[][]{
			{"Run", "Stop"},
			{"Running...", "Stoped."}
		};
		next = 0;
		
		btn = new JButton("Run");
		btn.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				nextAction();
			}
		});
		
		lbl = new JLabel("Click 'Run' to begin.");
		
		headerPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		headerPanel.add(btn);
		headerPanel.add(lbl);
		
		return headerPanel;
	}
	
	private final JPanel bodyPanel(String parentFolderUrl, int maxId) {		
		JPanel bodyPanel = new JPanel(new BorderLayout());
		
		table = initializeTable();
		plot = initializePlot(parentFolderUrl, maxId);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(400, (table.getRowHeight() * table.getRowCount()) + 24));
		
		bodyPanel.add(scrollPane, BorderLayout.NORTH);
		bodyPanel.add(plot, BorderLayout.CENTER);
		
		return bodyPanel;
	}
	
	private final PermutationTable initializeTable() {
		return new PermutationTable() {
			private static final long serialVersionUID = -2802244431615399528L;
			private CircleTag circleTag;
			
			@Override
			public void onNextTableValues(String[][] table_values) {
				circleTag = toCircleTag(table_values);

				if(circle_tag.isMarkerValid()) {
					plot.plotMarkerId(circleTag.getMarkerId());
					onNextCircleTag(circleTag);
				}
			}
			
			@Override
			public void onStop() {
				if(next == 0) togleButton();
				
				System.out.println("stopped");
			}
			
		};
	}
	
	private final PlotPanel initializePlot(String parentFolderUrl, int maxId) {
		PlotPanel plot = new PlotPanel(maxId);
		plot.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
		new Thread(() -> countGeneratedCircleTags(plot, parentFolderUrl, maxId)).start();
		
		return plot;
	}
	
	private int[] countGeneratedCircleTags(PlotPanel plot, String parentFolderUrl, int maxId) {
		int counter[] = new int[(maxId * 2) + 1];
		
		for(int id=-maxId; id<=maxId; id++) {
			if(id < 0)
				counter[id + maxId] = FileHandling.countFilesInFolder(parentFolderUrl + "/negatives/id_" + id);
			else if(id > 0) 
				counter[id + maxId] = FileHandling.countFilesInFolder(parentFolderUrl + "/positives/id_" + id);
			else 
				counter[id + maxId] = FileHandling.countFilesInFolder(parentFolderUrl + "/zeros/id_" + id);
			
			if(getRootPane() != null) getRootPane().repaint();

			plot.setCounters(counter);
		}
		
		return counter;
	}
	
	private void togleButton() {
		lbl.setText(state[1][next]);
		next = Math.abs(next-1);
		btn.setText(state[0][next]);
	}
	
	private void nextAction() {
		togleButton();
		
		switch (next) {
		case 0: 
			table.stopPermutation();
			onStop();
			break;
		case 1: 
			table.runPermutation();
			onRun();
			break;
		}
	}
	
	private int orbit;
	private char sym;
	private CardinalDirection cardinal_direction;
	private DiscreteSize discrete_size;
	private Polarity polarity;
	private CircleMark toCircleMark(String arg) {
		orbit = Integer.parseInt(arg.substring(0,1));
		cardinal_direction = CardinalDirection.valueOf(arg.substring(1,3));
		discrete_size = DiscreteSize.valueOf(arg.substring(3,4));
		
		sym = arg.charAt(4);
		
		if(sym == '+') 
			polarity = Polarity.HOLLOW;
		else if(sym == '-') 
			polarity = Polarity.SOLID;
		else 
			polarity = null;
		
		return new CircleMark(orbit, cardinal_direction, discrete_size, polarity);
	}
	
	private CircleTag circle_tag;
	private int r,c;
	private CircleTag toCircleTag(String args[][]) {
		circle_tag = new CircleTag();
		
		for(r=0; r<args.length; r++) {
			for(c=0; c<args[r].length; c++) {
				if(!args[r][c].equals("")) circle_tag.addCircleMark(toCircleMark((c+1) + CardinalDirection.values()[r].name() + args[r][c]));
			}
		}
		
		return circle_tag;
	}
	
}
