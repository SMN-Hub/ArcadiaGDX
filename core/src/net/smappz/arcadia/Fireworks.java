package net.smappz.arcadia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;

import net.smappz.arcadia.util.Point;

class Fireworks extends Group {
    private TextureAtlas.AtlasRegion regionShoot1;

    Fireworks() {
        TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("enemy.atlas"));
        regionShoot1 = textureAtlas.findRegion("0001");
    }

    void shoot(Point origin, Point speed) {
        Sprite sprite =  new Sprite(regionShoot1);
        sprite.setPosition(origin.getX(), origin.getY());
    }
}
