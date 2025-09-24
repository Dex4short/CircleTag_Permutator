package oop;


public abstract class PermutationModel3 extends PermutationModel2{
	private static boolean must_stop;

	public PermutationModel3() {
		
	}
	
	@Override
	protected Node createCycle(int r, int c, String tokens[][]) {
		return new CustomNode3(r, c, (tokens[r][c]==null) ? "" : tokens[r][c]);
	}
	
	@Override
	public void stop() {
		if(must_stop)
			super.stop();
	}
	
	public class CustomNode3 extends CustomNode2{
		private boolean occupied;
		
		public CustomNode3(int row, int col, String initial_token) {
			super(row, col, initial_token);
		}
		
		@Override
		public void next() {
			if(occupied && getTokenIndex()==0) {
				must_stop = false;
				getNextNode().next();
			}
			else {
				must_stop = true;
				super.next();
				occupy();
			}
		}
		
		private final CustomNode3 occupied_nodes[] = new CustomNode3[40];
		private int n,occupied_count=0;
		private double position1[], position2[];
		public void occupy() {
			for(n=0; n<occupied_count; n++) {
				occupied_nodes[n].setOccupied(false);
			}
			occupied_count = 0;
			
			if(getTokenIndex() == 0) {
				setOccupied(false);
			}
			else {
				setOccupied(true);
				position1 = getPosition(row, col, getTokenIndex());
				for(Node arr_node[]: nodes) {
					for(Node node: arr_node) {
						CustomNode3 custom_node = ((CustomNode3) node);
						
						position2 = getPosition(custom_node.row, custom_node.col, 1);
						custom_node.setOccupied(check_intersection(position1, position2));
						
						if(custom_node.occupied) {
							occupied_nodes[occupied_count] = custom_node;
							occupied_count++;
						}
					}
				}
			}
		}
		
		public void setOccupied(boolean occupied) {
			this.occupied = occupied;
		}
		
		public boolean isOccupied() {
			return occupied;
		}
		
		private static final double normals[][] = {
				{-1, 1},//NW
				{ 0, 1},//N
				{ 1, 1},//NE
				{-1, 0},//W
				{ 1, 0},//E
				{-1,-1},//SW
				{ 0,-1},//S
				{ 1,-1} //SE
		};
		private static final double factor = 1d/Math.sqrt(2);
		private static double dx, dy;
		private static double[] getPosition(int direction, double orbit, double size) {
			dx = normals[direction][0];
			dy = normals[direction][1];
			
			if (Math.abs(dx)==1 && Math.abs(dy)==1) {
	            dx *= factor;
	            dy *= factor;
	        }
			return new double[]{
					dx * ((orbit+1) * 10),
					dy * ((orbit+1) * 10),
					((size + 1) / 2) * 5
			};
		}
		
		public static boolean check_intersection(double c1[], double c2[]) {
		    dx = c2[0] - c1[0];
		    dy = c2[1] - c1[1];
		    return (Math.sqrt((dx*dx) + (dy*dy))) <= (c1[2] + c2[2]);
		}
		
	}
}
