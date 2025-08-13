package oop;

public abstract class PermutationModel2 extends PermutationModel{
	private static final int caps2[][][] = {
			{//orbit 1:
				{  5,  3,  0,  0,  0,  0,  0},	//orbit 1 
				{  9,  7,  5,  3,  0,  0,  0},	//orbit 2
				{ 13, 11,  9,  7,  5,  3,  0},	//orbit 3
				{ 15, 15, 13, 11,  9,  7},		//orbit 4
				{ 15, 15, 15, 15}				//orbit 5
			},
			{//orbit 2:
				{  9,  7,  5,  3,  0,  0,  0},	//orbit 1
				{ 13, 11,  9,  7,  5,  3,  0},	//orbit 2
				{ 15, 15, 13, 11,  9,  7,  5},	//orbit 3
				{ 15, 15, 15, 15, 13, 11,  9},	//orbit 4
				{ 15, 15, 15, 15}				//orbit 5
			},
			{//orbit 3:
				{ 13, 11,  9,  7,  5,  3,  0},	//orbit 1
				{ 15, 13, 11,  9,  7,  5,  3},	//orbit 2
				{ 15, 15, 15, 15, 15, 13, 11},	//orbit 3
				{ 15, 15, 15, 15, 15},			//orbit 4
				{ 15, 15, 15, 15}				//orbit 5
			},
			{//orbit 4:
				{ 13, 13, 13, 11,  9,  7,  5},	//orbit 1
				{ 13, 13, 13, 13, 13, 11,  9},	//orbit 2
				{ 13, 13, 13, 13, 13, 13, 13},	//orbit 3
				{ 13, 13, 13, 13, 13, 13},		//orbit 4
				{ 13, 13, 13, 13}				//orbit 5
			},
			{//orbit 5:
				{  9,  9,  9,  9,  9,  9,  9},	//orbit 1
				{  9,  9,  9,  9,  9,  9,  9},	//orbit 2
				{  9,  9,  9,  9,  9,  9,  9},	//orbit 3
				{  9,  9,  9,  9,  9,  9},		//orbit 4
				{  9,  9,  9,  9}				//orbit 5
			},
	};
	private static final int 
	impact[] = {
			-1,	//severity
			 0,	//orbit
			 0	//size
	};
	
	public PermutationModel2() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void nextPermutation() {
		super.nextPermutation();
		check_orbits();
		check_impact();
	}
	@Override
	protected Node createCycle(int r, int c, String tokens[][]) {
		return new CustomNode2(r, c, (tokens[r][c]==null) ? "" : tokens[r][c]);
	}
	
	//helpers
	private int severity;
	private void check_impact() {
		impact[0] = -1;
		
		for(int r=0; r<rows; r++) {
			for(int c=0; c<orbits_occupied; c++) {
				severity = (cols*10) * (((nodes[r][c].getTokenIndex()+1)/2)*10);
				
				if(severity > (int)impact[0]) {
					impact[0] = severity;
					impact[1] = c;
					impact[2] = (nodes[r][c].getTokenIndex()+1)/2;
				}
			}
		}
	}
	private int n, orbits_used, orbits_occupied;
	private void check_orbits() {
		orbits_used = 0;
		orbits_occupied = 0;
		
		for(n=0; n<circle_count.length; n++) {
			if(circle_count[n] > 0) {
				orbits_used++;
				
				if(n > orbits_occupied) 
					orbits_occupied = n;
			}
		}
	}
	
	//inner class
	public class CustomNode2 extends CustomNode{
		private boolean alter;
		private int cap2;

		public CustomNode2(int row, int col, String initial_token) {
			super(row, col, initial_token);
		}
		@Override
		public void next() {		
			super.next();
			
			check_alteration();
			if(alter) {
				cap2 = caps2[col][impact[1]][impact[2]];
				if(cap2 < getCap())
					setCap(cap2);
			}
		}
		private void check_alteration() {
			alter = (impact[2]!=0) && (orbits_used>1);
		}		
	}
	
}
