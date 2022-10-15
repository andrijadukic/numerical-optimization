package util;

import java.util.Comparator;

public final class Sampling {

    private Sampling() {
    }

    public static <T extends Comparable<T>> int argMin(T[] array) {
        return arg(array, Comparator.naturalOrder());
    }

    public static <T extends Comparable<T>> int argMax(T[] array) {
        return arg(array, Comparator.reverseOrder());
    }

    public static <T> int arg(T[] array, Comparator<T> comparator) {
        int index = 0;
        T value = array[0];
        for (int i = 1, n = array.length; i < n; i++) {
            T temp = array[i];
            if (comparator.compare(temp, value) < 0) {
                value = temp;
                index = i;
            }
        }
        return index;
    }

    public static void cumulativeSum(double[] array, double bias) {
        double sum = 0.;
        for (int i = 0, n = array.length; i < n; i++) {
            array[i] = (sum += array[i] + bias);
        }
    }

    public static void scale(double[] array, double factor) {
        for (int i = 0, n = array.length; i < n; i++) {
            array[i] /= factor;
        }
    }
}
