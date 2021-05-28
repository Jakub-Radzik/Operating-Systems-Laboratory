import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ProcessGenerator {
    private final double minLoad;
    private final double maxLoad;
    private final int minTime;
    private final int maxTime;


    public ProcessGenerator(double minLoad, double maxLoad, int minTime, int maxTime) {
        this.minLoad = minLoad;
        this.maxLoad = maxLoad;
        this.minTime = minTime;
        this.maxTime = maxTime;
    }

    public Process[] generate(int n) {
        Process[] processes = new Process[n];
        for (int i = 0; i < n; i++)
                processes[i] = new Process(randomLoad(), randomTime());
        return processes;
    }

    //LOSOWANIE CZASU POJAWIENIA SIĘ
    private int randomTime() {
        return ThreadLocalRandom.current().nextInt(minTime, maxTime + 1);
    }

    //LOSOWANIE OBCIĄŻENIA
    private double randomLoad() {
        return ThreadLocalRandom.current().nextDouble(minLoad, maxLoad);
    }
}
