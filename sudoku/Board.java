package sudoku;

import sudoku.*;
import java.util.*;
import java.io.*;

class Board {
	class BoardRunnable implements Runnable {
		Board b, solve;

		public BoardRunnable(Board b){
			this.b = b;
			this.solve = null;
		}

		@Override
		public void run(){
			control.rmThread();
			if(b == null) return;
			this.solve = b.solve();
			control.addThread();
		}
	}

	public List<Cell> cells = new LinkedList<Cell>();

	Control control;

	public Board(String file, Control control) {
		this.control = control;
		try{
			InputStream in;
			in = new FileInputStream(file);
			Reader reader = new InputStreamReader(in);

			int ch, row = 0, col = 0;
			while ((ch = reader.read()) != -1) {
				if (ch == '\r' || ch == '\n') {
					row++;
					col = 0;
					continue;
				}
				if (ch >= '0' && ch <= '9') {
					cells.add(new Cell(row, col++, ch - '0'));
					continue;
				}
				if (ch == ' ' || ch == '_' || ch == '.') {
					cells.add(new Cell(row, col++));
					continue;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Board ( List<Cell> cells, Control control ) {
		this.control = control;
		this.cells = new LinkedList<Cell>(cells);
	}

	public Board add ( int row, int col, int val ) {
		boolean conflict = false;
		List<Cell> rm = new LinkedList<Cell>();
		for(Cell c: cells) {
			if((c.row() == row || c. col() == col) && c.val() == val) {
				conflict = true;
			}
			if(c.row() == row && c.col() == col){
				rm.add(c);
			}
		}
		if(conflict == true) return null;

		// a copy of current list
		List<Cell> cs = new LinkedList<Cell>(this.cells);

		// rm old cell
		cs.removeAll(rm);

		// add new
		Cell c = new Cell(row, col, val);
		cs.add(c);

		return new Board(cs, control);
	}

	public Board solve(){
		Cell unset = null;
		for(Cell c: cells){
			if(!c.isSet()){
				unset = c;
				break;
			}
		}

		if(unset == null){
			return this;
		}

		int i;
		Thread th[] = new Thread[10];
		BoardRunnable br[] = new BoardRunnable[10];
		for(i = 0; i <= 9; i++){
			Board solution = this.add(unset.row(), unset.col(), i);
			if( solution != null ){
				if(control.leftThread() > 0){
					br[i] = new BoardRunnable(solution);
					th[i] = new Thread(br[i]);
					th[i].start();
				} else {
					Board solve = solution.solve();
					if( solve != null ){
						return solve;
					}
				}
			}
		}

		for(i = 0; i <= 9; i++){
			if(th[i] == null) {
				break;
			}


			try{
				th[i].join();
				if(br[i].solve == null){
					break;
				}
				return br[i].solve;
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}

		return null;
	}

	public void printcells(){
		for(Cell c: cells) {
			c.print();
		}
	}

	public void print(){
		for( int row = 0; row <= 9; row++ ){
			for( int col = 0; col <= 9; col++ ){
				for(Cell c: cells) {
					if(c.row() == row && c.col() == col){
						if(c.isSet()){
							System.out.print(""+ c.val());
						} else {
							System.out.print(".");
						}
					}
				}
			}
			System.out.print('\n');
		}

	}
}
