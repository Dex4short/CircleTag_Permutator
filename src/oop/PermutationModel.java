package oop;

public abstract class PermutationModel extends Graph{
	private static final int caps1[][] = {
	//count    0;  1;  2;  3;  4;  5;  6;  7;  8;
			{ 15, 15,  5,  1,  1,  0,  0,  0,  0},//orbit1
			{ 15, 15, 13, 13, 13,  9,  9,  1,  1},//orbit2
			{ 15, 15, 15, 15, 15, 15, 15,  3,  3},//orbit3
			{ 13, 13, 13, 13, 13, 13, 13, 11, 11},//orbit4
			{  9,  9,  9,  9,  9,  9,  9,  9,  9} //orbit5
	};
	protected static final int circle_count[] = {
	//orbit 1; 2; 3; 4; 5; 
			0, 0, 0, 0, 0
	};
	
	public PermutationModel() {
		super(8,5);
	}
	
	@Override
	public void run() {
		count_circles();
		super.run();
	}
	
	@Override
	protected Node createCycle(int r, int c, String tokens[][]) {
		return new CustomNode(r, c, (tokens[r][c]==null) ? "" : tokens[r][c]);
	}
	
	//helpers
	private void count_circles() {
		for(int c=0; c<circle_count.length; c++)
			circle_count[c] = 0;
		
		for(int r=0; r<nodes.length; r++) {
			for(int c=0; c<nodes[r].length; c++) {
				if(!nodes[r][c].getToken().equals(""))
					circle_count[c]++;
			}
		}
		
	}

	//inner class
	public class CustomNode extends Node{
		public final int
		row, //direction
		col; //orbit
		
		private boolean empty, token_changed;
		private int cap1;

		public CustomNode(int row, int col, String initial_token) {
			super(initial_token);
			this.row = row;
			this.col = col;
		}
		
		@Override
		public void next() {	
			checkCurrentToken();		
			super.next();	
			checkTokenChanges();
			
			cap1 = caps1[col][circle_count[col]];
			setCap(cap1);
		}
		
		private void checkCurrentToken() {
			empty = getToken().equals("");
		}
		
		private void checkTokenChanges() {
			if(empty)
				token_changed = !getToken().equals("");
			else
				token_changed = getToken().equals("");
			
			if(empty && token_changed)
				circle_count[col]++;
			else if(!empty && token_changed)
				circle_count[col]--;
		}
	}	
}
