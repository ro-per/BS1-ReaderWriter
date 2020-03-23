package solutions;

import resources.MemorySegment;
import domain.Process;
import domain.ProcessState;

public abstract class AbstractRWProblem implements MemoryWrapper {

    protected MemorySegment _memory = null;

    // STATISTICS
    protected int totalReads;
    protected int totalWrites;
    protected int readerCount;

    protected boolean writing;

    public AbstractRWProblem() {
        _memory = new MemorySegment();
        totalReads = 0;
        totalWrites = 0;
        readerCount = 0;
        writing = false;
    }

    protected abstract void beforeRead();

    protected abstract void afterRead();

    protected abstract void beforeWrite();

    protected abstract void afterWrite();

    public void read(Process p) {
        p.setState(ProcessState.WANTREAD);
        beforeRead();
        p.setState(ProcessState.READING);
        _memory.read();
        p.setState(ProcessState.IDLE);
        synchronized (this) {
            totalReads++;
        }
        afterRead();
    }

    public void write(Process p) {
        p.setState(ProcessState.WANTWRITE);
        beforeWrite();
        p.setState(ProcessState.WRITING);
        _memory.write();
        p.setState(ProcessState.IDLE);
        totalWrites++;
        afterWrite();
    }

    public void printStats() {
        //PRINTING STATS...
        String lineSeparator = System.getProperty("line.separator");
        System.out.println(lineSeparator + "---" + lineSeparator + lineSeparator + "Total amount of reads = "
                + totalReads + lineSeparator + "Total amount of writes = " + totalWrites + lineSeparator);
    }
}