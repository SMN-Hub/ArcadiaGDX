package net.smappz.arcadia.actors;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.SnapshotArray;

import net.smappz.arcadia.ArcadiaGame;
import net.smappz.arcadia.util.Driver;

import static net.smappz.arcadia.ArcadiaGame.RESOURCES;

public class Fireworks extends Group {

    public Shoot shoot(int shootId, Vector2 origin, float orientation) {
        TextureAtlas.AtlasRegion region = RESOURCES.getTextureRegion(String.format("shot0%d", shootId));
        Shoot shoot = new Shoot(region, origin, orientation, ArcadiaGame.INSTANCE.getShot(shootId));
        addActor(shoot);
        return shoot;
    }

    public Shoot shoot(int shootId, Vector2 origin, Vector2 target) {
        return shoot(shootId, origin, Driver.computeAngle(origin, target));
    }

    public BonusContainer bonus(Bonus bonusCode, Vector2 origin) {
        TextureAtlas.AtlasRegion region = RESOURCES.getTextureRegion(bonusCode.name());
        BonusContainer bonusContainer = new BonusContainer(region, origin, bonusCode);
        addActor(bonusContainer);
        return bonusContainer;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        SnapshotArray<Actor> children = getChildren();
        Actor[] items = children.begin();
        for (int i=0; i<children.size; i++) {
            Actor actor = items[i];
            if (actor != null && !actor.isVisible()) {
                children.removeIndex(i);
            }
        }
        children.end();
    }
}
