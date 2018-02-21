package net.smappz.arcadia.descriptors;

public class PlaneDescriptor {
    private int id;
    private float speed;
    private int life;
    private float shootFrequency = 0.f;

    public int getId() {
        return id;
    }

    public float getSpeed() {
        return speed;
    }

    public int getLife() {
        return life;
    }

    public float getShootFrequency() {
        return shootFrequency;
    }
}
