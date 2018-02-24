package net.smappz.arcadia.util;

public enum Formation {
    Vertical,
    Horizontal,
    Square2,
    Square4;

    public static final float DECAL_X = 110f;
    public static final float DECAL_Y = 90f;

    public Route place(Route src, int index) {
        Route r = new Route(src);
        switch (this) {
            case Vertical:
                RouteDriver.backWard(r, index* DECAL_X);
                break;
            case Horizontal:
                RouteDriver.translate(r, 0, index* DECAL_Y);
                break;
            case Square2:
                square(2, r, index);
                break;
            case Square4:
                square(4, r, index);
                break;
        }
        return r;
    }

    private static void square(int width, Route r, int index) {
        int x = index / width;
        int y = index % width;
        RouteDriver.translate(r, x * DECAL_X, y * DECAL_Y);
    }
}
