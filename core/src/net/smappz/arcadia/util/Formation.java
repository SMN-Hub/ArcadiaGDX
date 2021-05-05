package net.smappz.arcadia.util;

public enum Formation {
    Vertical,
    Horizontal,
    Square2,
    Square4,
    Square5;

    public static final float DECAL_X = 90f;
    public static final float DECAL_Y = 110f;

    public Route place(Route src, int index) {
        Route r = src.clone();
        switch (this) {
            case Vertical:
                RouteDriver.backWard(r, index* DECAL_Y);
                break;
            case Horizontal:
                RouteDriver.translate(r, index* DECAL_X, 0);
                break;
            case Square2:
                square(2, r, index);
                break;
            case Square4:
                square(4, r, index);
                break;
            case Square5:
                square(5, r, index);
                break;
        }
        return r;
    }

    private static void square(int width, Route r, int index) {
        int y = index / width;
        int x = index % width;
        RouteDriver.translate(r, x * DECAL_X, y * DECAL_Y);
    }
}
