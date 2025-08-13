package system;


import javax.swing.JFrame;

import system.ui.CircleTagPermutator;

public class Main {

	public static void main(String[]args) {
		CircleTagPermutator w = new CircleTagPermutator();
		w.setSize(800,600);
		w.setLocationRelativeTo(null);
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		w.setVisible(true);
		
	}
	
}
