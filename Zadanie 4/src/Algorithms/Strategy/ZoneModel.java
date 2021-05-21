package Algorithms.Strategy;

import java.util.Arrays;
import java.util.HashSet;

public class ZoneModel extends FramesAllocationAlgorithm {

    public ZoneModel(int[][] processes, int frames_counter) {
        super(processes, frames_counter);
    }

    @Override
    public int doAlgorithm() {
        //equal
        int frames_per_process = frames_count / processes.length;

        //list of the latest references
        HashSet<Integer>[] WorkingSet = new HashSet[processes.length];
        for (int i = 0; i < processes.length; ++i) {
            processes[i].setFrames_count(frames_per_process);
            WorkingSet[i] = new HashSet<>();
        }

        boolean isAllDone = false;
        int c = 0; //mentioned in hints
        int available_counter = 0;

        while (!isAllDone) {
            c++;
            isAllDone = true;
            for (int i = 0; i < processes.length; i++) {
                if (!processes[i].runOnce()) { //run LRU, and if is done
                    WorkingSet[i].add(processes[i].getLastUsed()); //last frame I used
                    isAllDone = false;
                }
            }

            // c is over 2* frames for process
            // frequency of WSS count


//            boolean condition = c >=  2 * frames_per_process;
            boolean condition = c >=  2 / frames_per_process;
//            boolean condition = c >=  frames_per_process;

            if (condition) {
                for (int i = 0; i < processes.length; ++i) {
                    if (processes[i].isDone()) {
                        available_counter += processes[i].getFrames_count();//if process is done we relase frames
                        processes[i].setFrames_count(0); //no frames for process
                    }
                    // for every process
                    /*
                     * Dopóki D jest mniejsze niż liczba dostępnych w systemie ramek każdy
                     * z procesów otrzymuje do wykorzystania  tyle  ramek, ile  wynosi  jego WSS.
                     * */
                    for (int j = WorkingSet[i].size() - processes[i].getFrames_count(); j > 0 && processes[i].getFrames_count() > 1; j--) {
                        available_counter++;
                        processes[i].removeFrame();
                    }
                }


                while (available_counter > 0 && c > 0) {
                    for (int i = 0; i < processes.length && available_counter > 0; i++) {

                        int needed_counter = WorkingSet[i].size() - processes[i].getFrames_count();
                        //WORKING SET - WS, .size() = WSS
                        /*
                        * W momencie przekroczenia liczby dostępnych ramek
                        * przez współczynnik D (point 1) jeden z aktywnych procesów
                        * musi zostać wstrzymany, a uwolnione ramki przekazane
                        * do pozostałych procesów (zgodnie z zasadą proporcjonalności). (point 2)
                        * */
                        if (needed_counter > 0) {
                            if (available_counter < needed_counter) {   //(point 1)
                                available_counter += processes[i].getFrames_count();
                                processes[i].setFrames_count(0);    //(point 2)
                            } else {
                                processes[i].addFrame();
                                available_counter--;
                            }
                        }
                    }
                    c--;
                }

                c = 0;

                //clear WS after interval or done of process
                for (int i = 0; i < WorkingSet.length; i++) {
                    if (processes[i].getFrames_count() > 0 || processes[i].isDone()) {
                        WorkingSet[i].clear();
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
