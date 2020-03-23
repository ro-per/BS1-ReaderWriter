package solutions;

import domain.Process;

public interface MemoryWrapper {

    void read(Process p);

    void write(Process p);

    public void printStats();
}
