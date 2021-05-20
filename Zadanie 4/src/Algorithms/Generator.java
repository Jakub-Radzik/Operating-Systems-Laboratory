package Algorithms;

public class Generator {
    private final int processes_count;
    private final int pages_count;
    private final int minimum_number_of_references;
    private final int maximum_number_of_references;
    private final int radius_of_locality;

    public Generator(int processes_count, int pages_count, int minimum_number_of_references, int maximum_number_of_references, int radius_of_locality) {
        this.processes_count = processes_count;
        this.pages_count = pages_count;
        this.minimum_number_of_references = minimum_number_of_references;
        this.maximum_number_of_references = maximum_number_of_references;
        this.radius_of_locality = radius_of_locality;
    }

    public int[][] generateProcesses() {
        // first dimension are processes
        int[][] processes_array = new int[processes_count][];
        // second dimension are references to pages for process
        for (int j = 0; j < processes_array.length; j++) {
            //number of references is random from setting min and max number of references
            int howManyReferences = Tools.randomize(minimum_number_of_references, maximum_number_of_references);
            //creating references
            int[] referencesArray = new int[howManyReferences];

            //we have to randomize references for first elem in array (it is because we have i-1 in loop)
            referencesArray[0] = Tools.randomize(pages_count/processes_count*j, pages_count/processes_count*(j+1));
//            referencesArray[0] = Tools.randomize(0, pages_count);

            //RULE OF LOCALITY ======== references of process are in specified range
            // position +- radius but we cant have ArrayIndexOfOutBoundException
            for (int i = 1; i < howManyReferences; i++) {
                int min = Math.max(referencesArray[i - 1] - radius_of_locality, 0);
                int max = Math.min(referencesArray[i - 1] + radius_of_locality, pages_count);
                referencesArray[i] = Tools.randomize(min, max);
            }
            //assign references to process
            processes_array[j] = referencesArray;
        }
        return processes_array;
    }
}
