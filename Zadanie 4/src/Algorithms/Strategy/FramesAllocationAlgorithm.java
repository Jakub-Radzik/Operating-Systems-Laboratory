package Algorithms.Strategy;

import Algorithms.LRU;

public abstract class FramesAllocationAlgorithm {
    protected LRU[] processes;
    protected int frames_count;
    protected int PF_counter;

    public FramesAllocationAlgorithm(int[][] processes, int frames_count) {
        this.frames_count = frames_count;
        this.processes = new LRU[processes.length];
        for (int i = 0; i < processes.length; ++i) {
            this.processes[i] = new LRU(processes[i]);
        }
    }

    public abstract int doAlgorithm();

    public int getResultForProcess(int process_number) {
        return processes[process_number].getPF_counter();
    }
}
