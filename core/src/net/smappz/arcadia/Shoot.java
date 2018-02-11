package net.smappz.arcadia;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;

import net.smappz.arcadia.util.LinearDriver;
import net.smappz.arcadia.util.Point;

public class Shoot extends Actor {
    private final Sprite sprite;
    private final LinearDriver driver;

    Shoot(TextureAtlas.AtlasRegion region, Point position, float orientation) {
        sprite =  new Sprite(region);
        driver = new LinearDriver(this, position, orientation);
        driver.start();
    }
}
