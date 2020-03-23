package solutions;

import domain.Process;

public class RWProblem3 extends AbstractRWProblem {
    // ______________________________ MAIN __________________________
    public static void main(String[] args) {
        Process.setSynchronisationWrapper(new RWProblem3());
        Process.startSimulation();
    }

    final int counter_max = 5; // max # readers that may pass
    int counter_cur = 0; // #readers that have passed

    private int readers_W = 0;
    private int readers_A = 0;
    private int writers_W = 0;
    boolean writers_A = false;

    boolean writer_prior = false;

    // ________________________ CONSTRUCTOR _________________________
    public RWProblem3() {
        super();
    }

    // _____________________________ READERS ________________________
    protected synchronized void beforeRead() {// SYNCRHONIZED
        readers_W++;
        while (writers_A || writer_prior) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        readers_W--;
        readers_A++;
        // ONLY COUNT WHEN MULTIPLE READERS
        if (readers_W > 0) {
            counter_cur++;
            if (counter_cur >= counter_max) {
                writer_prior = true;
            }
        }
        // ONLY NOTIFY WHEN WRITER DOES NOT HAVE PRIOR
        if (!writer_prior) {
            notifyAll();
        }
    }

    protected synchronized void afterRead() {// SYNCRHONIZED
        readers_A--;
        if (readers_A == 0) {
            notifyAll();// NO READERS LEFT
        }
    }

    // ______________________________ SCHRIJVERS ______________________________
    protected synchronized void beforeWrite() {// SYNCRHONIZED
        writers_W++;
        while (readers_A > 0 || writers_A || (readers_W > 0 && !writer_prior)) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        writers_W--;
        writers_A = true;
        // WRITER HAS GAINED ACCES, READERS CAN START PASSING AGAIN
        counter_cur = 0;
        writer_prior = false;
    }

    protected synchronized void afterWrite() {// SYNCRHONIZED
        writers_A = false;
        notifyAll();// DONE WRITING
    }

    @Override
    public void read(java.lang.Process p) {

    }

    @Override
    public void write(java.lang.Process p) {

    }
}
