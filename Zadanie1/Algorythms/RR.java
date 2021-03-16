package Zadanie1.Algorythms;

import Zadanie1.Proces;

import java.util.ArrayList;
import java.util.Collections;

public class RR {
    public static double RR(int k, ArrayList<Proces> procesy)
    {
        int quant = 0;
        int currentTime = 0;
        double allWaitingTime = 0;
        ArrayList<Proces> procesy4 = new ArrayList<Proces>();
        ArrayList<Proces> queue = new ArrayList<Proces>();
        for (int i = 0; i < procesy.size(); i++) {
            procesy4.add(new Proces((procesy.get(i)).getProcesNumber(),
                    (procesy.get(i)).getEnterMoment(), (procesy.get(i)).getDuration(), (procesy.get(i)).getDuration(), 0));
        }
        Collections.sort(procesy4, Proces.Comparators.ComparatorEnterMoment);

        do {

            for (int i = 0; i < procesy4.size(); i++)

            {
                if (currentTime == (procesy4.get(i)).getEnterMoment())
                {
                    queue.add(new Proces(0, (procesy4.get(i)).getEnterMoment(), 0, (procesy4.get(i)).getTimeLeft(), 0));
                }
            }

            if(quant <= 0 && queue.size() !=0)
            {
                if ((queue.get(0)).getTimeLeft() == 0) {
                    allWaitingTime += (queue.get(0)).getWaitingTime();
                    queue.remove(0);
                    Collections.sort(queue, Proces.Comparators.ComparatorProcesNumberEnterMoment);
                    if (queue.size() != 0)
                    {
                        (queue.get(0)).setProcesNumber((queue.get(0)).getProcesNumber() + 1);
                        if ((queue.get(0)).getTimeLeft() >= k) {
                            quant = k;
                        } else {
                            quant = (queue.get(0)).getTimeLeft();
                        }
                    }

                }

                else
                {
                    if(queue.size()==1)
                    {
                        (queue.get(0)).setProcesNumber((queue.get(0)).getProcesNumber() + 1);

                    }
                    else
                    {
                        queue.add(queue.get(0));
                        queue.remove(0);
                        Collections.sort(queue, Proces.Comparators.ComparatorProcesNumberEnterMoment);
                        (queue.get(0)).setProcesNumber((queue.get(0)).getProcesNumber() + 1);


                    }
                    if((queue.get(0)).getTimeLeft()>=k)
                    {
                        quant = k;
                    }
                    else {
                        //  System.out.println("mniejszy kwant");
                        quant = (queue.get(0)).getTimeLeft();
                    }
                }

            }



            if (queue.size() != 0) {
                // System.out.println(kolejka);
                (queue.get(0)).setTimeLeft((queue.get(0)).getTimeLeft() - 1);
                for (int j = 1; j < queue.size(); j++) {
                    (queue.get(j)).setWaitingTime((queue.get(j)).getWaitingTime() + 1);
                }

            }

            currentTime++;
            quant--;

        }
        while (currentTime != 100000);
        return allWaitingTime / procesy4.size();

    }
}
