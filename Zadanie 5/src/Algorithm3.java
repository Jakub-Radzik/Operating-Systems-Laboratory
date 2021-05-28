import java.util.List;
import java.util.Queue;

public class Algorithm3 extends Algorithm2 {
    //.Jak w pkt 2, z tym że procesory o obciążeniu mniejszym od minimalnego progu r pytają losowo wybrane\n
    // procesory i jesli obc. zapytanego jest większe od p, pytający przejmuje część jego zadań (założyć jaką).\n
    // oraz  że procesory o obciążeniu mniejszym od minimalnego progu r pytają losowo wybrane procesory i jesli obc.
    // zapytanego jest większe od p, pytający przejmuje część jego zadań

    //założony próg zapytań o wymiane procesu
    private final int MAX_COUNT_OF_PROCESS_EXCHANGES = 15;

    public Algorithm3(List<Processor> processors) {
        super(processors);
    }


    @Override
    public ProcessorStats process(Queue<Process> processes) {
        int migrationRequest = 0;
        int migrations = 0;
        int requestAboutProcessExchange;

        while (!processes.isEmpty()) {
            Process process = processes.poll(); //weź proces z zwierzchu
            Processor processor = randomProcessor(); // losuj procka
            ProcessResponse response = findProcessorAndProcess(process, processor);
            migrationRequest += response.migrationRequests;
            migrations += response.migrations;

            requestAboutProcessExchange = 0;
            for(Processor selectedProcessor:processors){   // sprawdzamy każdy procesor
                if(selectedProcessor.isBelowMinimumLoad()) { //jeśli obciążenie procka jest mniejsze od minimum
                    while (selectedProcessor.canHandleNextProcess() && requestAboutProcessExchange++ < MAX_COUNT_OF_PROCESS_EXCHANGES) {
                        //dopóki procesor wybrany może obsłużyc następny proces i mamy nie przekroczona ilosc zapytan o wymiane procesow
                        Processor selectedToGiveBack = randomProcessor();//losujemy procesor
                        ++migrationRequest;

                        while (!selectedToGiveBack.canHandleNextProcess()) {
                            ++migrations;
                            selectedProcessor.addProcess(selectedToGiveBack.removeProcess());// wymieniamy procesory
                        }
                    }
                }
            }

            processAll();
        }

        return new ProcessorStats("Algorithm 3", averageLoad(), averageDeviation(), migrationRequest, migrations);

    }
}
