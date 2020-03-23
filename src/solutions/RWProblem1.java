package solutions;

import domain.Process;

public class RWProblem1 extends AbstractRWProblem {
	// ______________________________ MAIN __________________________
	public static void main(String[] args) {
		Process.setSynchronisationWrapper(new RWProblem1());
		Process.startSimulation();
	}

	private int readers_W = 0;
	private int readers_A = 0;
	private int writers_W = 0;
	private int writers_A = 0;

	// ________________________ CONSTRUCTOR _________________________
	public RWProblem1() {
		super();
	}

	// _____________________________ READERS ________________________
	protected synchronized void beforeRead() { // SYNCRHONIZED
		readers_W++;
		if (writers_A > 0 || readers_A > 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		notifyAll();
		readers_W--;
		readers_A++;
	}

	protected synchronized void afterRead() {// SYNCRHONIZED
		readers_A--;
		if (readers_A == 0) {
			notifyAll();// NO READERS LEFT
		}
	}

	// _____________________________ WRITERS ________________________
	protected synchronized void beforeWrite() {// SYNCRHONIZED
		writers_W++;
		while (readers_A != 0 || writers_A != 0 || readers_W != 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		notifyAll();
		writers_W--;
		writers_A++;
	}

	protected synchronized void afterWrite() {// SYNCRHONIZED
		writers_A--;
		notifyAll();// DONE WRITING
	}

	@Override
	public void read(java.lang.Process p) {

	}

	@Override
	public void write(java.lang.Process p) {

	}
}