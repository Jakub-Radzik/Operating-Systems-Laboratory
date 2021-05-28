import java.util.List;
import java.util.Queue;
import java.util.Random;

import static java.util.Collections.unmodifiableList;

public abstract class Algorithm {
    protected final List<Processor> processors;
    protected final Random random;

    public Algorithm(List<Processor> processors) {
        this.processors = unmodifiableList(processors);
        this.random = new Random();
    }

    public abstract ProcessorStats process(Queue<Process> processes);

    //SREDNIE ODCHYLENIE
    protected double averageDeviation() {
        return processors.stream().mapToDouble(Processor::averageDeviation)
                .sum() / processors.size();
    }

    //SREDNIE OBCIĄZENIE
    protected double averageLoad() {
        return processors.stream().mapToDouble(Processor::averageLoad)
                .sum() / processors.size();
    }

    //URUCHOM WSZYSTKO
    protected void processAll() {
        processors.forEach(Processor::process);
    }

    //LOSOWANIE PROCESU
    protected Processor randomProcessor() {
        return processors.get(random.nextInt(processors.size()));
    }
}
