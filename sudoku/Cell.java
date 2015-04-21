package sudoku;

import sudoku.*;

class Cell {
	private int row, col, val;
	private boolean isSet;

	public Cell ( int row, int col, int val ) {
		this.row = row;
		this.col = col;
		this.val = val;
		this.isSet = true;
	}

	public Cell ( int row, int col ) {
		this.row = row;
		this.col = col;
		isSet = false;
	}

	public boolean isSet() {
		return isSet;
	}

	public int col() {
		return col;
	}

	public int row() {
		return row;
	}

	public int val() {
		return val;
	}

	public void print(){
		if(isSet){
			System.out.print(""+ row + " " + col + " " + val + "\n");
		} else {
			System.out.print(""+ row + " " + col + " .\n");
		}
	}
}
