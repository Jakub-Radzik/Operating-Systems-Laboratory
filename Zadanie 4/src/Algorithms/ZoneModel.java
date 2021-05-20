package Algorithms;

import java.util.HashSet;

public class ZoneModel extends FramesAllocationAlgorithm {

    public ZoneModel(int[][] processes, int frames_counter) {
        super(processes, frames_counter);
    }

    @Override
    public int doAlgorithm() {
        int frames_per_process = frames_count / processes.length;

        HashSet<Integer>[] array_of_sets_of_latest_references = new HashSet[processes.length];
        for (int i = 0; i < processes.length; ++i) {
            processes[i].setFrames_count(frames_per_process);
            array_of_sets_of_latest_references[i] = new HashSet<>();
        }

        boolean isAllDone = false;
        int counter = 0;
        int available_counter = 0;

        while (!isAllDone) {
            counter++;
            isAllDone = true;
            for (int i = 0; i < processes.length; ++i) {
                if (!processes[i].runOne()) {
                    array_of_sets_of_latest_references[i].add(processes[i].getLastUsed());
                    isAllDone = false;
                }
            }

            if (counter >= 2 * frames_per_process) {
                for (int i = 0; i < processes.length; ++i) {
                    if (processes[i].isDone()) {
                        available_counter += processes[i].getFrames_count();
                        processes[i].setFrames_count(0);
                    }
                    for (int j = array_of_sets_of_latest_references[i].size() - processes[i].getFrames_count(); j > 0 && processes[i].getFrames_count() > 1; --j) {
                        available_counter++;
                        processes[i].removeFrame();
                    }
                }
                while (available_counter > 0 && counter > 0) {
                    for (int i = 0; i < processes.length && available_counter > 0; ++i) {
                        int needed_counter = array_of_sets_of_latest_references[i].size() - processes[i].getFrames_count();
                        if (needed_counter > 0) {
                            if (available_counter < needed_counter) {
                                available_counter += processes[i].getFrames_count();
                                processes[i].setFrames_count(0);
                            } else {
                                processes[i].addFrame();
                                available_counter--;
                            }
                        }
                    }
                    counter--;
                }
                counter = 0;

                for (int i = 0; i < array_of_sets_of_latest_references.length; i++) {
                    if (processes[i].getFrames_count() > 0 || processes[i].isDone()) {
                        array_of_sets_of_latest_references[i].clear();
                    }
                }

            }

        }

        for (int i = 0; i < processes.length; ++i) {
            PF_counter += processes[i].getPF_counter();
        }

        return PF_counter;
    }
}
