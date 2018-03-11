package net.smappz.arcadia.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
    private final TextureAtlas shotTextures;
    private final TextureAtlas fighterTextures;
    private final TextureAtlas enemyTextures;
    private final TextureAtlas bonusTextures;
    private final Texture backGround;
    private final Map<String,AtlasRegion> regions = new HashMap<>();
    private final BitmapFont font;

    public ResourceManager() {
        shotTextures = new TextureAtlas(Gdx.files.internal("shots.atlas"));
        fighterTextures = new TextureAtlas(Gdx.files.internal("PlanesSmall.atlas"));
        enemyTextures = new TextureAtlas(Gdx.files.internal("enemy.atlas"));
        bonusTextures = new TextureAtlas(Gdx.files.internal("bonus.atlas"));
        backGround = new Texture(Gdx.files.internal("space_0.png"));
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("MATURASC.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 16;
        font = generator.generateFont(parameter);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        generator.dispose();
    }

    public AtlasRegion getTextureRegion(String id) {
        AtlasRegion region = regions.get(id);
        if (region == null) {
            TextureAtlas atlas;
            if (id.startsWith("shot")) {
                atlas = shotTextures;
            } else if (id.startsWith("plane")) {
                atlas = fighterTextures;
            } else if (id.startsWith("UP_") || id.startsWith("WP_")) {
                atlas = bonusTextures;
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
