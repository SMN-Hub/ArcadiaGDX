package net.smappz.arcadia.descriptors;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class PlaneDescriptor implements Cloneable {
    private int id;
    private float speed;
    private int life;
    private float shootFrequency = 0.f;
    private int shoot = -1;
    private boolean bonus = false;

    public int getId() {
        return id;
    }

    public float getSpeed() {
        return speed;
    }

    public void increaseSpeed() {
        speed += 100;
    }

    public int getLife() {
        return life;
    }

    public float getShootFrequency() {
        return shootFrequency;
    }

    public void increaseFrequency() {
        if (shootFrequency > 0.1)
            shootFrequency -= 0.02;
    }

    public int getShootId() {
        return shoot;
    }

    public void increaseShoot() {
        if (shoot < 5)
            shoot++;
    }

    public boolean hasBonus() {
        return bonus;
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
}
