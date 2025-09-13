package oop;

public abstract class Graph {
	public final int rows, cols;
	public final Node nodes[][], end_node;
	
	private volatile boolean run=false;
	
	public Graph(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		
		nodes = new Node[rows][cols];
		end_node = new Node("") {
			@Override
			public void next() {
				stop();
			}
		};
	}
	
	//main functions
	public void setTokens(String tokens[][]) {
		int r, c, rr, cc;
		
		for(r=0; r<nodes.length; r++) {
			for(c=0; c<nodes[r].length; c++) {
				nodes[r][c] = createCycle(r, c, tokens);
			}
		}
		
		for(r=0, rr=1; r<rows; r++, rr++) {
			if(rr==rows) rr=0;
			
			for(c=0, cc=0; c<cols; c++, cc++) {
				if(cc==cols) cc=0;
				
				nodes[r][c].setNextNode(nodes[rr][cc]);
			}
		}
		
		for(c=0; c<cols-1; c++) {
			nodes[rows-1][c].setNextNode(nodes[0][c+1]);
		}
		
		nodes[rows-1][cols-1].setNextNode(end_node);
	}
	public String[][] getTokens(){
		int 
		rows = nodes.length,
		cols = nodes[0].length,
		r, c;
		String tokens[][] = new String[rows][cols];
		for(r=0; r<rows; r++) {
			for(c=0; c<cols; c++) {
				tokens[r][c] = nodes[r][c].getToken();
			}
		}
		return tokens;
	}
	public void run() {		
		run = true;
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(run) {
					nextPermutation();
				}
			}
		}).start();
	}
	public void stop() {
		run = false;
	}
	
	//permutation functions
	public void nextPermutation() {
		nodes[0][0].next();
		onNextPermutation(getTokens());
	}
	public abstract void onNextPermutation(String next_tokens[][]);
	
	//internal functions
	protected Node createCycle(int r, int c, String tokens[][]) {
		return new Node((tokens[r][c]==null) ? "" : tokens[r][c]);
	}
	
}
