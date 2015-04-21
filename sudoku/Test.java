package sudoku;

import sudoku.*;

public class Test {

	public static void main(String args[]){
		if (args.length != 1) {
			System.out.println("java sudoku.Test file");
			System.exit(1);
		}

		Board b = new Board(args[0]);
		b.print();
		b = b.solve();
		b.print();
	}  

}
