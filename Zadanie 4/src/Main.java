import Algorithms.*;

import java.util.Arrays;

public class Main {

    //SETTINGS FOR SIMULATION =============================================================
    public static final int Processes_Count = 10;
    public static final int Pages_Count = 10000;
    public static final int Minimum_number_of_references = 30;
    public static final int Maximum_number_of_references = 300;
    public static final int radius_of_locality = 10;
    public static final int frames_count = 100;
    //======================================================================================

    public static void main(String[] args) {

        Generator generator = new Generator(Processes_Count, Pages_Count, Minimum_number_of_references, Maximum_number_of_references, radius_of_locality);
        int[][] processes = generator.generateProcesses();

        FramesAllocationAlgorithm[] algorithms = new FramesAllocationAlgorithm[]{
                new Equal(Tools.copyArray(processes), frames_count),
                new Proportional(Tools.copyArray(processes), frames_count),
                new SteeringFaultFrequency(Tools.copyArray(processes), frames_count),
                new ZoneModel(Tools.copyArray(processes), frames_count)
        };

        System.out.println("Results for all:=====================================================");
        for (FramesAllocationAlgorithm algo : algorithms) {
            System.out.println(algo.getClass().getSimpleName() + " - " + algo.doAlgorithm());
        }

        System.out.println("Result for every process:============================================");
        for (int i = 0; i < processes.length; ++i) {
            System.out.println("\n Result for process number " + i + ":");
            for (FramesAllocationAlgorithm algo : algorithms) {
                System.out.println(algo.getClass().getSimpleName() + " - " + algo.getResultForProcess(i));
            }
        }
    }
}
