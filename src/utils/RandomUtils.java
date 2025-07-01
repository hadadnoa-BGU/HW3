package util;

import java.util.Random;

public class RandomUtils {

    private static final Random rand = new Random();

    public static int randomInt(int min, int max) {
        return rand.nextInt(max - min + 1) + min;  // Inclusive range
    }
}
