package net.smappz.arcadia.descriptors;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class PlaneDescriptor implements Cloneable {
    private static final int MAX_MULTIPLY = 5;

    private float speed;
    private int speedMultiplier = 1;
    private int life;
    private int lifeMultiplier = 1;
    private float shootFrequency = 0.f;
    private int shootMultiplier = 1;
    private int shoot = -1;
    private boolean bonus = false;

    public float getSpeed() {
        return speed + (speedMultiplier - 1) * 100;
    }

    public void increaseSpeed() {
        speedMultiplier = increaseMultiplier(speedMultiplier);
    }

    public int getLife() {
        return life;
    }

    public float getShootFrequency() {
        return shootFrequency + (shootMultiplier - 1) * 0.02f;
    }

    public void increaseFrequency() {
        shootMultiplier = increaseMultiplier(shootMultiplier);
    }

    public int getShootId() {
        return shoot;
    }

    public void increaseShoot() {
        shoot = increaseMultiplier(shoot);
    }

    public boolean hasBonus() {
        return bonus;
    }

    public int getSpeedMultiplier() {
        return speedMultiplier;
    }

    public int getShootMultiplier() {
        return shootMultiplier;
    }

    @Override
    public PlaneDescriptor clone() {
        final PlaneDescriptor clone;
        try {
            clone = (PlaneDescriptor) super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("superclass messed up", ex);
        }
        return clone;
    }

    private int increaseMultiplier(int multiplier) {
        if (multiplier < MAX_MULTIPLY) {
            multiplier++;
        }
        return multiplier;
    }
}
