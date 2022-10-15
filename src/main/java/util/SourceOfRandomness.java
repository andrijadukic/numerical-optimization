package util;

import java.util.Random;

public final class SourceOfRandomness {

    private static final Random random = new Random(System.nanoTime());

    public static Random getSource() {
        return random;
    }
}
