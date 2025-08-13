package system.ui.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import marker.CircleMark;
import marker.CircleTag;
import marker.enums.CardinalDirection;
import marker.enums.DiscreteSize;
import marker.enums.Polarity;
import system.ui.table.PermutationTable;

public abstract class PermutationPanel extends JPanel{
	private static final long serialVersionUID = -5996962255502758721L;
	private final JButton btn;
	private final JLabel lbl;
	private final PermutationTable table;
	
	private final String state[][] = {
			{"Run", "Stop"},
			{"Running...", "Stoped."}
	};
	private int next = 0;

	public PermutationPanel() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder("Permutation"));
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		btn = new JButton("Run");
		lbl = new JLabel("Click 'Run' to begin.");
		
		table = new PermutationTable() {
			private static final long serialVersionUID = -2802244431615399528L;
			@Override
			public void onNextTableValues(String[][] table_values) {
				onNextCircleTag(toCircleTag(table_values));
			}
			@Override
			public void onStop() {
				//togleButton();
				System.out.println("stopped");
			}
		};
		
		btn.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				togleButton();
			}
		});
		
		panel.setBorder(BorderFactory.createRaisedBevelBorder());
		panel.add(btn);
		panel.add(lbl);
		
		add(panel, BorderLayout.NORTH);
		add(new JScrollPane(table), BorderLayout.CENTER);
	
	}
	public PermutationTable getPermutationTable() {
		return table;
	}
	public abstract void onRun();
	public abstract void onStop();
	public abstract void onNextCircleTag(CircleTag circle_tag);
	
	private void togleButton() {
		lbl.setText(state[1][next]);
		next = Math.abs(next-1);
		btn.setText(state[0][next]);
		
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
	
	//helpers
	private CircleMark toCircleMark(String arg) {
		int orbit = Integer.parseInt(arg.substring(0,1));
		CardinalDirection cardinal_direction = CardinalDirection.valueOf(arg.substring(1,3));
		DiscreteSize discrete_size = DiscreteSize.valueOf(arg.substring(3,4));
		
		Polarity polarity;
		char sym = arg.charAt(4);
		if(sym == '+') {
			polarity = Polarity.HOLLOW;
		}
		else if(sym == '-') {
			polarity = Polarity.SOLID;
		}
		else {
			polarity = null;
		}
		return new CircleMark(orbit, cardinal_direction, discrete_size, polarity);
	}
	private CircleTag toCircleTag(String args[][]) {
		CircleTag circle_tag = new CircleTag();
		int r,c;
		for(r=0; r<args.length; r++) {
			for(c=0; c<args[r].length; c++) {
				if(!args[r][c].equals("")) circle_tag.addCircleMark(toCircleMark((c+1) + CardinalDirection.values()[r].name() + args[r][c]));
			}
		}
		return circle_tag;
	}
	
}
