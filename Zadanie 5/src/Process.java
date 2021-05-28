import static java.lang.String.format;

public class Process {
    public static final Process EMPTY = new Process(0.0, 0);
    private double processorLoad;
    private int time;

    public Process(double processorLoad, int time) {
        this.processorLoad = processorLoad;
        this.time = time;
    }

    public Process(Process process) {
        this(process.processorLoad, process.time);
    }

    public int getTime() {
        return time;
    }

    public void reduceTime() {
        --time;
    }

    public boolean isFinished() {
        return time <= 0;
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }

    public double getProcessorLoad() {
        return processorLoad;
    }

    public void setProcessorLoad(double processorLoad) {
        this.processorLoad = processorLoad;
    }

    @Override
    public String toString() {
        return format("%.2f%% (%d)", processorLoad, time);
    }
}
