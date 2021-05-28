import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Algorithm2 extends Algorithm {
    //Jesli obciążenie x przekracza wartość progową p, proces zostaje wysłany
    // na losowo wybrany procesor y o obciążeniu mniejszym od p\n" +
    //(jeśli wylosowany y ma obc.>p, losowanie powtarza się do skutku).
    // Jeśli nie przekracza - proces wykonuje się na x.";

    public Algorithm2(List<Processor> processors) {
        super(processors);
    }

    @Override
    public ProcessorStats process(Queue<Process> processes) {
        int migrationRequest = 0;
        int migrations = 0;

        while (!processes.isEmpty()) {
            Process process = processes.poll();         //pierwszy proces
            Processor processor = randomProcessor();    //losowy procesor

            ProcessResponse response = findProcessorAndProcess(process, processor);
            migrationRequest += response.migrationRequests; //przypisujemy ilosc zapytan o migracje
            migrations += response.migrations;              //i faktyczną ilosc migracji

            processAll();
        }

        return new ProcessorStats("Algorytm 2", averageLoad(), averageDeviation(), migrationRequest, migrations);
    }

    protected ProcessResponse findProcessorAndProcess(Process process, Processor processor) {
        //dla danego procesora i procesu

        int migrationRequest = 0;
        int migrations = 0;
        Processor selectedProcessor = processor;

        if (process.isEmpty()) {    //jeśli pusty proces to nie musimy sie martwic -> operujemy
            processAll();
        }
        else if (processor.canHandleNextProcess()) {
            processor.addProcess(process);  //przypadek kiedy proces może zostać obsłużony
        }
        else {
            List<Processor> processorsToPeek = new ArrayList<>(processors);     //procesory wszystkie
            boolean processHandled = false; //proces nie jest obsłuzony
            while (!processorsToPeek.isEmpty() && !processHandled) { //dopoki procesory są dostępne i proces nie został obsłuzony
                int index = random.nextInt(processorsToPeek.size()); //losowy procesor
                Processor newProcessor = processorsToPeek.get(index);
                ++migrationRequest;

                if (newProcessor.canHandleNextProcess()) {  //jeżeli może obsłużyc to super
                    ++migrations;
                    selectedProcessor = newProcessor;
                    newProcessor.addProcess(process);
                    processHandled = true;
                }
                else
                    processorsToPeek.remove(index);     // nie moze to usuwamy go z listy i losujemy dalej
            }
        }

        return new ProcessResponse(migrationRequest, migrations, selectedProcessor);
    }

    protected static class ProcessResponse {
        protected final int migrationRequests;
        protected final int migrations;
        protected final Processor processor;

        public ProcessResponse(int migrationRequests, int migrations, Processor processor) {
            this.migrationRequests = migrationRequests;
            this.migrations = migrations;
            this.processor = processor;
        }
    }

}
