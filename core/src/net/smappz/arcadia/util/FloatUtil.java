package net.smappz.arcadia.util;

public class FloatUtil {
    private FloatUtil() {}

    public static boolean equals(float x, float y) {
        return Math.abs(x - y) < 0.02f;
    }


}
