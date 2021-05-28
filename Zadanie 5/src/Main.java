import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.LongStream.range;

public class Main {

    /*
    *       N - LICZBA PROCKÓW
    * */
    private static final int NUMBER_OF_PROCESSORS = 75;

    /*
    *       p - wartość progowa MAX obciążenia procesora x
    * */
    private static final double MAX_PROCESSOR_LOAD = 0.8;   //0.5

    /*
    *       r - wartość progowa MIN obciążenia procesora x
     */
    private static final double MIN_PROCESSOR_LOAD = 0.3; //0.3

    /*
    *       z - maksymalna ilość zapytań o migrację
    * */
    private static final int MAX_MIGRATION_REQUESTS = 15;

    /*
     *       LICZBA PROCESÓW
     * */
    private static final int NUMBER_OF_PROCESSES = 1000;

    /*
     *       minimalne obciążenie generowane przez proces
     * */
    private static final double PROCESS_MIN_LOAD = 0.3; //0.3;

    /*
     *       maksymalne obciążenie generowane przez proces
     * */
    private static final double PROCESS_MAX_LOAD = 0.5; //0.5

    /*
     *       minimalny czas procesu
     * */
    private static final int PROCESS_MIN_TIME = 50;

    /*
     *       maksymalny czas procesu
     * */
    private static final int PROCESS_MAX_TIME = 100;


    public static void main(String[] args) {
        System.out.println("---------------Przydział procesorów w systemach rozproszonych---------------\n");

        //generowanie procesorów
        List<Processor> processors = range(0, NUMBER_OF_PROCESSORS)
                .mapToObj(i -> new Processor(MIN_PROCESSOR_LOAD, MAX_PROCESSOR_LOAD))
                .collect(toList());

        //generowanie procesów
        Process[] processes = new ProcessGenerator(
                PROCESS_MIN_LOAD,
                PROCESS_MAX_LOAD,
                PROCESS_MIN_TIME,
                PROCESS_MAX_TIME
        ).generate(NUMBER_OF_PROCESSES);

        //tworzenie tablicy z menadżerami
        Algorithm[] managers = new Algorithm[] {
                new Algorithm1(Utils.copyProcessors(processors), MAX_MIGRATION_REQUESTS),
                new Algorithm2(Utils.copyProcessors(processors)),
                new Algorithm3(Utils.copyProcessors(processors)),
        };

        for (Algorithm manager : managers)
            System.out.println(manager.process(Utils.processesQueue(processes)));
    }
}
