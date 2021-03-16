package Zadanie1.Algorythms;

import Zadanie1.Proces;

import java.util.ArrayList;
import java.util.Collections;

public class SJF {
    public static double SJF(ArrayList<Proces> procesy) {
        int currentTime = 0;
        double allWaitingTime = 0;
        ArrayList<Proces> procesy2 = new ArrayList<>();
        ArrayList<Proces> queue = new ArrayList<>();
        for (int i = 0; i < procesy.size(); i++) {
            procesy2.add(new Proces((procesy.get(i)).getProcesNumber(),
                    (procesy.get(i)).getEnterMoment(), (procesy.get(i)).getDuration(), (procesy.get(i)).getDuration(), 0));
        }
        Collections.sort(procesy2, Proces.Comparators.ComparatorEnterMoment);

        do {
            for (int i = 0; i < procesy2.size(); i++)

            {
                if (currentTime == (procesy2.get(i)).getEnterMoment()) {

                    queue.add(new Proces(0, 0, 0, (procesy2.get(i)).getTimeLeft(), 0));
                }


            }
            if (queue.size() == 0) {
                Collections.sort(queue, Proces.Comparators.ComparatorTimeLeft);
            } else if ((queue.get(0)).getTimeLeft() == 0) {
                allWaitingTime += (queue.get(0)).getWaitingTime();
                queue.remove(0);
                Collections.sort(queue, Proces.Comparators.ComparatorTimeLeft);

            }
            currentTime++;

            if (queue.size() != 0) {
                (queue.get(0)).setTimeLeft((queue.get(0)).getTimeLeft() - 1);
                for (int j = 1; j < queue.size(); j++) {
                    (queue.get(j)).setWaitingTime((queue.get(j)).getWaitingTime() + 1);
                }

            }
        }
        while (currentTime != 100000);
        return allWaitingTime / procesy2.size();

    }
}
