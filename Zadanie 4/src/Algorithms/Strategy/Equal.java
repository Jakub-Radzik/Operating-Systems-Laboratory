package Algorithms.Strategy;

public class Equal extends FramesAllocationAlgorithm {

    public Equal(int[][] processes, int frames_count) {
        super(processes, frames_count);
    }

    public int doAlgorithm() {
        int frames_per_process = frames_count / processes.length;

        //Assign equal nums. of frames for every process
        for(int i = 0; i < processes.length; i++) {
            processes[i].setFrames_count(frames_per_process);
        }

        //Do LRU for all processes and get page failures
        for(int i = 0; i < processes.length; i++) {
            processes[i].doAll();
            PF_counter += processes[i].getPF_counter();
        }

        return PF_counter;
    }
}
