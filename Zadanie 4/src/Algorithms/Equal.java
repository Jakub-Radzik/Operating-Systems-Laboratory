package Algorithms;

public class Equal extends FramesAllocationAlgorithm {

    public Equal(int[][] processes, int frames_count) {
        super(processes, frames_count);
    }

    public int doAlgorithm() {
        int frames_per_process = frames_count / processes.length;

        for(int i = 0; i < processes.length; i++) {
            processes[i].setFrames_count(frames_per_process);
        }

        for(int i = 0; i < processes.length; i++) {
            processes[i].doAll();
            PF_counter += processes[i].getPF_counter();
        }
        return PF_counter;
    }
}
