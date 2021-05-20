package Zadanie1.Algorythms;

import Zadanie1.Proces;

import java.util.ArrayList;
import java.util.Collections;

public class FCFS {
    public static ArrayList<Integer> maxWaitingTime = new ArrayList<>();

    public static double FCFS(ArrayList<Proces> procesy)
    {
        //Tworzę całkowitą kopię tablicy procesów z czasem oczekiwania 0 dla każdego procesu
        ArrayList<Proces> procesyCopy = new ArrayList<>(procesy.size());

        for (int i = 0; i < procesy.size(); i++) {
            procesyCopy.add(
                    new Proces(
                            (procesy.get(i)).getProcesNumber(),
                            (procesy.get(i)).getEnterMoment(),
                            (procesy.get(i)).getDuration(),
                            (procesy.get(i)).getDuration(),
                            0));
        }
        //FCFS obsługuje procesy według czasu wejścia stąd sortujemy po czasie pojawienia się procesu
        Collections.sort(procesyCopy, Proces.Comparators.ComparatorEnterMoment);

        double allWaitingTime = 0;
        int allFinishTime = (procesyCopy.get(0)).getDuration();

        for (int i = 1; i < procesyCopy.size(); i++) {
            //RZADKO WYKONYWANE.... JEST TO SPOWODOWANE TYM, ŻE MUSIMY CZEKAĆ NA PROCES JEŚLI DOTYCHCZAS SIĘ ŻADEN NIE POJAWIŁ
            //KONIECZNE W SKRAJNYCH PRZYPADKACH
            //PRZYKŁAD SYTUACJI:
            //czasOczekiwania=0, czas wejścia procesu = 5 i jego czas trwania to 10, łącznie wyjdzie 15
            if (allFinishTime <= procesyCopy.get(i).getEnterMoment())
                allFinishTime = procesyCopy.get(i).getEnterMoment() + (procesyCopy.get(i)).getDuration();
            else {
                allWaitingTime += (allFinishTime - procesyCopy.get(i).getEnterMoment());
                allFinishTime += (procesyCopy.get(i)).getDuration();
            }
            //USTAWIAM KAŻDEMU PROCESOWI CZAS OCZEKIWANIA WEDLUG WZORU:
            //czasOczekiwaniaProcesu = czasOczekiwaniaPoprzedniegoProcesu + czasTrwaniaPoprzedniegoProcesu - (czasWejściaObecnegoProcesu - czasWejściaPoprzedniegoProcesu)
            procesyCopy.get(i).setWaitingTime( procesyCopy.get(i-1).getWaitingTime() + procesyCopy.get(i-1).getDuration() - (procesyCopy.get(i).getEnterMoment() - procesyCopy.get(i-1).getEnterMoment()) );


        }
        maxWaitingTime.add(procesyCopy.stream().map(proces -> proces.getWaitingTime()).max(Integer::compare).get());

        return allWaitingTime / procesy.size();
    }
}
