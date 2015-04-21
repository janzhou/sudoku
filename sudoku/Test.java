package sudoku;

import sudoku.*;

public class Test {

	public static void main(String args[]){
		if (args.length != 2) {
			System.out.println("java sudoku.Test file thread");
			System.exit(1);
		}

		Board b = new Board(args[0], new Control(Integer.parseInt(args[1])));
		b.print();
		long startTime = System.nanoTime();
		b = b.solve();
		long estimatedTime = System.nanoTime() - startTime;
		b.print();

		System.out.print(estimatedTime);
	}  

}
