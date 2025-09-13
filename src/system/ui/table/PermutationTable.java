package system.ui.table;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import marker.enums.CardinalDirection;
import oop.PermutationModel;
import oop.PermutationModel2;
import oop.PermutationModel3;

public abstract class PermutationTable extends JTable{
	private static final long serialVersionUID = -2675329254589537342L;
	private String column_header[];
	private DefaultTableModel model;
	
	private final PermutationModel permutation;
	private boolean editable;
	
	public PermutationTable() {
		column_header = new String[]{"...","orbit1","orbit2","orbit3","orbit4","orbit5"};
		model = new DefaultTableModel(column_header, 8) {
			private static final long serialVersionUID = -5583074461403990107L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return column!=0;
			}
		};
		setModel(model);
		
		CardinalDirection directions[] = CardinalDirection.values();
		model.setRowCount(directions.length);
		for(int r=0; r<model.getRowCount(); r++) {
			model.setValueAt(directions[r], r, 0);
		}
		
		permutation = new PermutationModel2() {
			@Override
			public void onNextPermutation(String next_tokens[][]) {
				setTableValues(next_tokens);
			}
		};
		
		setEditable(true);
	}
	@Override
	public boolean isCellEditable(int row, int column) {
		return editable;
	}
	@Override
	public boolean isCellSelected(int row, int column) {
		if(editable)
			return super.isCellSelected(row, column);
		else
			return false;
	}
	public String[][] getTableValues() {
		int 
		rows = getRowCount(),
		cols = getColumnCount() - 1,
		r, c;
		
		String table_values[][] = new String[rows][cols];
		Object obj;
		
		for(r=0; r<rows; r++) {
			for(c=0; c<cols; c++) {
				obj = model.getValueAt(r, c+1);
				table_values[r][c] = (obj==null) ? "" : obj.toString();
			}
		}
		
		return table_values;
	}
	public void setTableValues(String table_values[][]) {
		int 
		rows = getRowCount(),
		cols = getColumnCount() - 1,
		r, c;
		
		String str;
		for(r=0; r<rows; r++) {
			for(c=0; c<cols; c++) {
				str = table_values[r][c];
				model.setValueAt(str, r, c+1);
			}
		}
		
		nextTableValues(table_values);
	}
	public void runPermutation() {
		setEditable(false);
		permutation.setTokens(getTableValues());
		permutation.run();
	}
	public void stopPermutation() {
		permutation.stop();
		setEditable(true);
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	
	public void nextTableValues(String table_values[][]) {
		onNextTableValues(table_values);
	}
	public abstract void onNextTableValues(String table_values[][]);
	public abstract void onStop();
}
