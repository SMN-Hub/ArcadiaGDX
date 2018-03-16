package net.smappz.arcadia.actors;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import net.smappz.arcadia.ArcadiaGame;
import net.smappz.arcadia.GameListener;
import net.smappz.arcadia.util.ManualDriver;

import java.util.HashMap;
import java.util.Map;

import static net.smappz.arcadia.ArcadiaGame.RESOURCES;


public class AirFighter1 extends AirFighter {
    private Pitch pitch = Pitch.Flat;
    private Map<Pitch,TextureAtlas.AtlasRegion> regions = new HashMap<>();

    public AirFighter1() {
        this(360, 200);
    }

    public AirFighter1(float initialX, float initialY) {
        super(-90f, 2f, 1f, ArcadiaGame.INSTANCE.getPlane(0), initialX, initialY);
        regions.put(Pitch.Left2, RESOURCES.getTextureRegion("plane01"));
        regions.put(Pitch.Left, RESOURCES.getTextureRegion("plane02"));
        regions.put(Pitch.Flat, RESOURCES.getTextureRegion("plane03"));
        regions.put(Pitch.Right, RESOURCES.getTextureRegion("plane04"));
        regions.put(Pitch.Right2, RESOURCES.getTextureRegion("plane05"));

        setImage(regions.get(pitch));
        start();
    }

    @Override
    protected void updateFrame(Pitch newPitch) {
        if (newPitch != pitch) {
            pitch = newPitch;
            setImage(regions.get(pitch));
        }
    }
}
