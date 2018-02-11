package net.smappz.arcadia.util;

public class FloatUtil {
    private static final float EPSILON = 0.01f;
    private FloatUtil() {}

    public static boolean equals(float x, float y) {
        return Math.abs(x - y) < EPSILON;
    }

    public static boolean isZero(float value) {
        return Math.abs(value) < EPSILON;
    }
}
