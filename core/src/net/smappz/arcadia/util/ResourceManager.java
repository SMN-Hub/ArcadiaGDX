package net.smappz.arcadia.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
    private final TextureAtlas shotTextures;
    private final TextureAtlas fighterTextures;
    private final TextureAtlas enemyTextures;
    private final Texture backGround;
    private final Map<String,TextureAtlas.AtlasRegion> regions = new HashMap<>();
    private final BitmapFont font;

    public ResourceManager() {
        shotTextures = new TextureAtlas(Gdx.files.internal("shots.atlas"));
        fighterTextures = new TextureAtlas(Gdx.files.internal("PlanesSmall.atlas"));
        enemyTextures = new TextureAtlas(Gdx.files.internal("enemy.atlas"));
        backGround = new Texture(Gdx.files.internal("space_0.png"));
        font = new BitmapFont();
    }

    public TextureAtlas.AtlasRegion getTextureRegion(String id) {
        TextureAtlas.AtlasRegion region = regions.get(id);
        if (region == null) {
            TextureAtlas atlas;
            if (id.startsWith("shot")) {
                atlas = shotTextures;
            } else if (id.startsWith("plane")) {
                atlas = fighterTextures;
            } else {
                atlas = enemyTextures;
            }
            region = atlas.findRegion(id);
            regions.put(id, region);
        }
        return region;
    }

    public Texture getBackGround() {
        return backGround;
    }

    public BitmapFont getFont() {
        return font;
    }

    public void dispose() {
        shotTextures.dispose();
        fighterTextures.dispose();
        enemyTextures.dispose();
        backGround.dispose();
        font.dispose();
    }
}
