package Zadanie1;

import Zadanie1.Algorythms.FCFS;
import Zadanie1.Algorythms.RR;
import Zadanie1.Algorythms.SJF;

import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        int quant = 5;
        int cycles = 20;
        int procesQuantity = 50;

        ArrayList<String> list = new ArrayList<>();
        ArrayList<Proces> proceses = new ArrayList<Proces>();

        double sumFCFS = 0;
        double sumSJF = 0;
        double sumRR = 0;

        System.out.println("\nCałkowityCzasOczekiwania/IloscProcesow/IloscCykli\n");
        for (int j = 0; j < cycles; j++) {

            for (int i = 0; i < procesQuantity; i++) {
                Random r = new Random();
                int d = r.nextInt(100);
                int m = 1 + r.nextInt(100);
                proceses.add(new Proces(i, d, m, m, 0));
            }

            System.out.println(
                    "FCFS: " + FCFS.FCFS(proceses) +
                            " SJF: "  + SJF.SJF(proceses) +
                            " RR: "   + RR.RR(quant, proceses) );

            sumFCFS += FCFS.FCFS(proceses);
            sumSJF += SJF.SJF(proceses);
            sumRR += RR.RR(quant, proceses);

            proceses.clear();
        }

        list.add("FCFS "+sumFCFS /(cycles));
        list.add("SJF "+sumSJF / (cycles));
        list.add("RR "+ sumRR / (cycles));
        System.out.println("\nAverage of all results:");
        System.out.println(list.toString());


        System.out.println("\nAnother stats:");
        System.out.println("AVG OF MAX WAITING TIME FCFS: " + FCFS.maxWaitingTime.stream().mapToDouble(a -> a).average().getAsDouble());

    }
}