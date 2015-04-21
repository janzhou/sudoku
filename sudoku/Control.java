package sudoku;

class Control {
	private int nthread = 1;
	private int running = 1;

	public Control(int nthread){
		this.nthread = nthread;
		running = 1;
	}

	public synchronized int leftThread(){
		if(running < 1) return 0;
		return nthread - running;
	}

	public synchronized void addThread(){
		running += 1;
	}

	public synchronized void rmThread(){
		running -= 1;
	}
}
