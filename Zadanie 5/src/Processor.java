import java.util.*;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Processor {
    private final double minLoad;
    private final double maxLoad;
    private List<Process> processes;

    private double currentLoad;
    private int time;
    private Map<Integer, Double> loadInTime;

    public Processor(double minLoad, double maxLoad) {
        this.minLoad = minLoad;
        this.maxLoad = maxLoad;
        this.processes = new ArrayList<>();
        this.loadInTime = new HashMap<>();
    }

    public Processor(Processor processor) {
        this(processor.minLoad, processor.maxLoad);
    }

    public void addProcess(Process process) {
        processes.add(process);
        currentLoad += process.getProcessorLoad();
    }

    public boolean canHandleNextProcess() {
        return currentLoad < maxLoad;
    }

    public void process() {
        loadInTime.put(++time, currentLoad);
        processes.forEach(Process::reduceTime);
        cleanEmptyProcesses();
    }

    public double averageLoad() {
        double totalLoad = loadInTime.values().stream().reduce(Double::sum) // całkowitye obciążenie w każdej jednostce czasu
                .orElseThrow(IllegalStateException::new);

        return totalLoad / loadInTime.size(); // dzielenie przez ilość pomiarów
    }

    public double averageDeviation() {
        final double mean = averageLoad(); // średnie obciążenie
        double deviation = loadInTime.values().stream() //obciążenie w czasie
                .mapToDouble(load -> pow(load - mean, 2)) //dla każdej wartości obciążenia bieżemy jego odległość od średniego obciążenia i podnosimy do kwadratu
                .sum(); //sumujemy to
        double averageDeviation = deviation / loadInTime.size(); // i dzielimy przez ilość pomiarów
        return sqrt(averageDeviation); // zwracamy pierwiastek z tego
    }

    private void cleanEmptyProcesses() {
        for(Iterator<Process> iterator = processes.iterator(); iterator.hasNext();) {
            Process process = iterator.next();
            if (process.isFinished()) {
                currentLoad -= process.getProcessorLoad();
                iterator.remove();
            }
        }
    }

    public boolean isBelowMinimumLoad() {
        return currentLoad <= minLoad;
    }

    public Process removeProcess() {
        if(processes.size() > 0) {
            processes.sort(Comparator.comparingDouble(Process::getProcessorLoad)); //sortowanie po obciążeniu
                this.currentLoad=this.currentLoad-processes.get(0).getProcessorLoad();
                return processes.remove(0); //usuwanie pierwszego
            }
        else
            return Process.EMPTY;
    }
}
