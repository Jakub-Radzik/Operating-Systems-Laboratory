package Algorithms;

import java.util.Arrays;
import java.util.Random;

public class Tools {
    public static int[][] copyArray(int[][] array) {
        return Arrays.stream(array).map(int[]::clone).toArray(int[][]::new);
    }

    public static int randomize(int min, int max) {
        Random random = new Random();
        if (max == 0) return 0; //just for sure
        return random.nextInt(max - min) + min;
    }

}
