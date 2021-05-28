import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Utils {
    public static List<Processor> copyProcessors(List<Processor> processors) {
        List<Processor> copy = new ArrayList<>(processors.size());
        for (Processor processor : processors) {
            copy.add(new Processor(processor));
        }
        return copy;
    }

    static Queue<Process> processesQueue(Process[] processes) {
        Queue<Process> copy = new LinkedList<>();
        for (Process process : processes)
            copy.add(new Process(process));
        return copy;
    }
}
