package resources;

//import java.util.Random;

public class MemorySegment {
    // private Random rand = new Random();
    private int readers = 0;
    private int writers = 0;

    public MemorySegment() {
    }

    public void read() {

        readers++;
        System.out.print("READER ADDED ");
        System.out.println("[" + readers + " | " + writers + "]");
        checkLock();

        synchronized (this) {

            try {
                Thread.sleep(5000); // lees 5 seconden lang
            } catch (InterruptedException ie) {
            }
        }

        System.out.print("READER DELETED ");
        System.out.println("[" + readers + " | " + writers + "]");
        readers--;
    }

    private void checkLock() {
        if (writers > 1) {
            System.out.println("ERROR: More than one writer");
        }

        if ((writers > 0) && (readers > 0)) {
            System.out.println("ERROR: Simultaneous reading and writing");
        }
    }

    public void write() {
        writers++;
        System.out.print("WRITER ADDED ");
        System.out.println("[" + readers + " | " + writers + "]");
        checkLock();

        synchronized (this) {
            try {
                Thread.sleep(5000);// schrijft 5 seconden lang
            } catch (InterruptedException ie) {
            }
        }
        System.out.print("WRITER DELETED ");
        System.out.println("[" + readers + " | " + writers + "]");
        writers--;

    }
}
