package domain;

import solutions.MemoryWrapper;

import buttonviewers.ButtonViewer;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Random;

import static domain.IOType.READ;
import static domain.IOType.WRITE;

public class Process extends Observable implements Runnable {

    private int _id;
    private IOType processType;
    private ProcessState _state = ProcessState.IDLE;
    private static MemoryWrapper _memory;

    public static void setSynchronisationWrapper(MemoryWrapper memory) {
        _memory = memory;
    }

    public static void startSimulation() {
        Process p1 = new Process(1, READ);
        Process p2 = new Process(2, WRITE);
        Process p3 = new Process(3, READ);
        Process p4 = new Process(4, WRITE);
        Process p5 = new Process(5, READ);
        Process p6 = new Process(6, WRITE);

        java.awt.Frame f = new java.awt.Frame("Reader-Writer problem");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                _memory.printStats();
                System.exit(0);
            }
        });
        f.setLayout(new java.awt.FlowLayout());
        f.setSize(700, 800);
        f.add(new ButtonViewer("Proces 1", p1));
        f.add(new ButtonViewer("Proces 2", p2));
        f.add(new ButtonViewer("Proces 3", p3));
        f.add(new ButtonViewer("Proces 4", p4));
        f.add(new ButtonViewer("Proces 5", p5));
        f.add(new ButtonViewer("Proces 6", p6));

        f.pack();
        f.setVisible(true);

        p1.start();
        p2.start();
        p3.start();
        p4.start();
        p5.start();
        p6.start();
    }

    public Process(int id, IOType rwPreference) {
        _id = id;
        processType = rwPreference;
    }

    public void start() {
        new Thread(this).start();
        setState(ProcessState.IDLE);
    }

    public ProcessState getState() {
        return _state;
    }

    public void setState(ProcessState state) {
        _state = state;
        setChanged();
        notifyObservers();
    }

    public String toString() {
        return "Proces " + _id;
    }

    private void read() {
        _memory.read(this);
    }

    private void write() {
        _memory.write(this);
    }

    public void run() {
        Random rand = new Random();
        while (true) {
            synchronized (this) {
                try {
                    Thread.sleep(rand.nextInt(1000));
                } catch (InterruptedException ie) {
                }
            }
            if (processType.equals(READ)) {
                read();
            } else {
                write();
            }
        }
    }

}
