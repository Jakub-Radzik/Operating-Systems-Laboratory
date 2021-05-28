import static java.lang.String.format;

public class ProcessorStats {
    private final String description;
    private final double averageLoad;
    private final double averageDeviation;
    private final int loadRequests;
    private final int loadMigrations;

    public ProcessorStats(String description, double averageLoad, double averageDeviation, int loadRequests, int loadMigrations) {
        this.description = description;
        this.averageLoad = averageLoad;
        this.averageDeviation = averageDeviation;
        this.loadRequests = loadRequests;
        this.loadMigrations = loadMigrations;
    }

    @Override
    public String toString() {
        return format("%s:\n1) Srednie obciazenie: %.2f\n2) Srednie odchylenie: %.2f\n"
                      + "3) Ilosc zapytan o obciazenie: %d\n4) Ilosc migracji: %d\n",
                description, averageLoad, averageDeviation, loadRequests, loadMigrations);
    }
}
