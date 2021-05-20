package Zadanie1.Algorythms;

import Zadanie1.Proces;

import java.util.ArrayList;
import java.util.Collections;

public class RR {
    public static double RR(int k, ArrayList<Proces> procesy)
    {
        //kopiowanie i tworzenie kolejki
        int quant = 0;
        int currentTime = 0;
        double allWaitingTime = 0;
        ArrayList<Proces> procesy4 = new ArrayList<>();
        ArrayList<Proces> queue = new ArrayList<>();
        for (int i = 0; i < procesy.size(); i++) {
            procesy4.add(new Proces((procesy.get(i)).getProcesNumber(),
                    (procesy.get(i)).getEnterMoment(), (procesy.get(i)).getDuration(), (procesy.get(i)).getDuration(), 0));
        }
        Collections.sort(procesy4, Proces.Comparators.ComparatorEnterMoment);


        do {
            //kolejkowanie procesu w chwili zgłoszenia i kopiowanie procesu
            for (int i = 0; i < procesy4.size(); i++) {
                if (currentTime == (procesy4.get(i)).getEnterMoment()) {
                    queue.add(new Proces(0, (procesy4.get(i)).getEnterMoment(), 0, (procesy4.get(i)).getTimeLeft(), 0));
                }
            }

            //w kolejce procesów (niepustej)
            if(quant <= 0 && queue.size() !=0) {
                if ((queue.get(0)).getTimeLeft() == 0) { // proces który się skończył
                    allWaitingTime += (queue.get(0)).getWaitingTime(); //czas oczekiwania wydłużamy o  ten ile czekał zakończony proces
                    queue.remove(0); //usuwamy skończony proces
                    Collections.sort(queue, Proces.Comparators.ComparatorProcesNumberEnterMoment); //sortujemy od nowa po czasie wejścia

                    //niepusta kolejka
                    if (queue.size() != 0) {
                        //TODO: chyba nie potrzebna linijka poniżej
                        (queue.get(0)).setProcesNumber((queue.get(0)).getProcesNumber() + 1); //zamiana numeru procesu na 1 większy

                        //jeśli czas do wykonania procesu jest większy od kwantu czasu
                        //kwant jest taki jak zadaliśmy
                        //jeśli nie to skracamy kwant do takiego na ile potrzebujemy do zakończenia procesu
                        if ((queue.get(0)).getTimeLeft() >= k) {
                            quant = k;
                        } else {
                            quant = (queue.get(0)).getTimeLeft();
                        }
                    }
                }
                else{ //proces który jeszcze musi być przetwarzany

                    if(queue.size()==1) { //przypadek gdy kolejka ma jeden proces
                        (queue.get(0)).setProcesNumber((queue.get(0)).getProcesNumber() + 1);
                    }
                    else{// kolejka z wieloma elementami
                        queue.add(queue.get(0));
                        queue.remove(0);
                        Collections.sort(queue, Proces.Comparators.ComparatorProcesNumberEnterMoment);
                        (queue.get(0)).setProcesNumber((queue.get(0)).getProcesNumber() + 1);
                        //proces nie został zakończony więc przerzucamy go na koniec kolejki z czasem obsługi
                        //mniejszym o kwant czasu
                    }

                    //proces o pozostałym czasie obsługi mniejszym od kwantu potrzebuje mniej czasu na obsługę
                    // kwant musi być dostosowany do czasu
                    if((queue.get(0)).getTimeLeft()>=k) {
                        quant = k;
                    }
                    else {
                        quant = (queue.get(0)).getTimeLeft();
                    }
                }

            }


            if (queue.size() != 0) {//niepusta kolejka
                (queue.get(0)).setTimeLeft((queue.get(0)).getTimeLeft() - 1); //procesowi skracamy czas obsługi
                for (int j = 1; j < queue.size(); j++) {
                    (queue.get(j)).setWaitingTime((queue.get(j)).getWaitingTime() + 1); //reszta procesów czeka kiedy obsługujemy proces
                }
            }

            //zwiększamy czas obsługi i skracamy kwant
            currentTime++;
            quant--;

        }
        while (currentTime != 100000);
        return allWaitingTime / procesy4.size();

    }
}
