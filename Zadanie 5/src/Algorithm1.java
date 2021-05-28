import java.util.List;
import java.util.Queue;

public class Algorithm1 extends Algorithm {
    //x pyta losowo wybr. procesor y o aktualne obciążenie.
    // Jeśli jest mniejsze od progu p, proces jest tam wysyłany." +
    //Jeśli nie, losujemy i pytamy następny, próbując co najwyżej z razy.
    // Jeśli wszystkie wylosowane są obciążone powyżej p, proces wykonuje się na x";

    private final int maxMigrationRequests;

    public Algorithm1(List<Processor> processors, int maxMigrationRequests) {
        super(processors);
        this.maxMigrationRequests = maxMigrationRequests;
    }

    @Override
    public ProcessorStats process(Queue<Process> processes) {
        int migrationRequest = 0;
        int migrations = 0;

        while (!processes.isEmpty()) {  //jeśli są procesy
            Process process = processes.poll(); //bierzemy proces z góry kolejki i go usuwamy z niej
            Processor processor = randomProcessor(); // losujemy procesor
            if (process.isEmpty()){
                processAll();   //przetwarzanie procesu jeśli process z góry kolejki jest pusty
            }
            else {
                boolean processHandled = false; //czy proces został obsłużony
                for (int i = 0; i < maxMigrationRequests && !processHandled; i++) {
                    //dopóki i jest mniejsze od maksymalnej ilosci migracji
                    //i proces nie został obsłużony

                    ++migrationRequest;
                    Processor nextProcessor = randomProcessor(); //losujemy inny procesor
                    if (nextProcessor.canHandleNextProcess()) {  //pytamy procesor czy może obsłużyć następny proces (OBCIĄZENIE JEGO) próg p
                        nextProcessor.addProcess(process);      //przypisujemy mu proces
                        processHandled = true;                  //proces jest obsłużony
                        ++migrations;                           //zwiększamy ilość migracji
                    }
                }
                if (!processHandled)            // jeśli losowaliśmy wystarczającą ilość razy to obciążamy procesor pierwotny
                    processor.addProcess(process);
            }

            processAll();                   //uruchamiamy przetwarzania procesów
        }

        return new ProcessorStats("Algorytm 1", averageLoad(), averageDeviation(), migrationRequest, migrations);
    }
}
