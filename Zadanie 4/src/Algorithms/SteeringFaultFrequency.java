package Algorithms;


public class SteeringFaultFrequency extends FramesAllocationAlgorithm {

    public SteeringFaultFrequency(int[][] processes, int frames_count) {
        super(processes, frames_count);
    }

    @Override
    public int doAlgorithm() {

        //for now divide equal
        int frames_per_process = frames_count / processes.length;

        for (int i = 0; i < processes.length; i++) {
            processes[i].setFrames_count(frames_per_process);
        }

        boolean isAllDone = false;
        int counter = 0;
        int[] page_faults_array = new int[processes.length];
        int[] priority_array = new int[processes.length];
        int available_counter = 0;

        while (!isAllDone) {
            counter++;
            isAllDone = true;

            for (int i = 0; i < processes.length; i++) {
                if (!processes[i].runOne()) { //if LRU failed
                    isAllDone = false;
                }
            }

            if (counter >= frames_per_process) {
                //over all processes
                for (int i = 0; i < processes.length; ++i) {
                    //assign to priority processes PF and subtract pages faults
                    priority_array[i] = processes[i].getPF_counter() - page_faults_array[i];
                    //set page faults
                    page_faults_array[i] = processes[i].getPF_counter();
                    //give frame back you dont need it !!!
                    if (priority_array[i] <= counter / 3 && processes[i].getFrames_count() > 1) {
                        processes[i].removeFrame();
                        available_counter++;
                    }
                }
                while (available_counter > 0 && counter > 0) {
                    //while i < processes count and available frames
                    for (int i = 0; i < processes.length && available_counter > 0; ++i) {
                        //if you need more frames I will give you one
                        if (priority_array[i] >= counter) {
                            processes[i].addFrame();
                            available_counter--;
                        }
                    }
                    counter--;
                }
                counter = 0;
            }

        }

        for (int i = 0; i < processes.length; ++i) {
            PF_counter += processes[i].getPF_counter();
        }

        return PF_counter;
    }
}
