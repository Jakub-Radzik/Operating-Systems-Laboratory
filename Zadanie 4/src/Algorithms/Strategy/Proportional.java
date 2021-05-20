package Algorithms.Strategy;

public class Proportional extends FramesAllocationAlgorithm {

    public Proportional(int[][] processes, int frames_count) {
        super(processes, frames_count);
    }

    @Override
    public int doAlgorithm() {
        int processes_length = 0;

        for(int i = 0; i < processes.length; i++){
            processes_length += processes[i].getPage_referencess_array().length;
        }

        for(int i = 0; i < processes.length; i++){
            int frames_per_process = frames_count * processes[i].getPage_referencess_array().length / processes_length;
            processes[i].setFrames_count(frames_per_process);
        }

        for(int i = 0; i < processes.length; i++){
            processes[i].doAll();
            PF_counter += processes[i].getPF_counter();
        }

        return PF_counter;
    }
}
