package Zadanie1.Algorythms;

import Zadanie1.Proces;

import java.util.ArrayList;
import java.util.Collections;

public class SJF {
    public static double SJF(ArrayList<Proces> procesy) {

        int currentTime = 0;
        double allWaitingTime = 0;
        int max = 0;
        int val = 0;
//        int pNumber = 0;
        int pNumberMax = 0;

        //utworzenie kopii tablicy procesów i stworzenie kolejki procesów
        ArrayList<Proces> procesy2 = new ArrayList<>();
        ArrayList<Proces> queue = new ArrayList<>();
        for (int i = 0; i < procesy.size(); i++) {
            procesy2.add(new Proces((procesy.get(i)).getProcesNumber(),
                    (procesy.get(i)).getEnterMoment(), (procesy.get(i)).getDuration(), (procesy.get(i)).getDuration(), 0));
        }
        //sortowanie według czasu wejścia
        Collections.sort(procesy2, Proces.Comparators.ComparatorEnterMoment);

        do {
            for (int i = 0; i < procesy2.size(); i++) {
                //kolejkuję proces który właśnie się zgłasza
                if (currentTime == (procesy2.get(i)).getEnterMoment()) {
                    queue.add(new Proces((procesy2.get(i)).getProcesNumber(), 0, 0, (procesy2.get(i)).getTimeLeft(), 0));
                }
            }

            //sortuję kolejkę po czasie który został - pusta kolejka

            // jeśli proces został skończony wyrzucamy go z kolejki i sortujemy kolejkę ponownie
            // aby procesy były dalej posortowane po długości
            if (queue.size() == 0) {
                Collections.sort(queue, Proces.Comparators.ComparatorTimeLeft);
            }else if ((queue.get(0)).getTimeLeft() == 0 && queue.size() != 0) {
                allWaitingTime += (queue.get(0)).getWaitingTime();
                queue.remove(0);
                Collections.sort(queue, Proces.Comparators.ComparatorTimeLeft);
            }
            Collections.sort(queue, Proces.Comparators.ComparatorTimeLeft);
            //zwiększamy czas obsługi
            currentTime++;

            //obsługujemy proces, skracamy mu czas obsługi i reszcie wydłużamy czas oczekiwania
            if (queue.size() != 0) {
                (queue.get(0)).setTimeLeft((queue.get(0)).getTimeLeft() - 1);
                for (int j = 1; j < queue.size(); j++) {
                    (queue.get(j)).setWaitingTime((queue.get(j)).getWaitingTime() + 1);
                }
            }

            if(queue.size()>0){
                val = queue.stream().map(Proces::getWaitingTime).max(Integer::compare).get();

                if(val>max){
                    for (int i = 0; i < queue.size(); i++) {
                        if(queue.get(i).getWaitingTime()==val){
                            pNumberMax = queue.get(i).getProcesNumber();
                        }
                    }
                    max = val;
                }
            }

        }
        while (currentTime != 100000);
        Proces proc = procesy2.get(0);
        for (int i = 0; i < procesy2.size(); i++) {
            if(procesy2.get(i).getProcesNumber()==pNumberMax)
                proc = procesy2.get(i);
        }
        proc.setWaitingTime(max);
        System.out.println("\nSJF STATS:");
        System.out.println("Proces with max waiting time: " + proc+"");

        return allWaitingTime / procesy2.size();

    }
}
