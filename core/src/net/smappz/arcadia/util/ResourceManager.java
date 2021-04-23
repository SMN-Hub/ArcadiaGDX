package net.smappz.arcadia.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.PixmapTextureData;

import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
    private final TextureAtlas shotTextures;
    private final TextureAtlas fighterTextures;
    private final TextureAtlas fighter2Textures;
    private final TextureAtlas enemyTextures;
    private final TextureAtlas invaderTextures;
    private final TextureAtlas bonusTextures;
    private final Texture backGround;
    private final Map<String,AtlasRegion> regions = new HashMap<>();
    private final BitmapFont font;
    private final Pixmap fadePixel;
    private final Texture fadeTexture;

    public ResourceManager() {
        shotTextures = new TextureAtlas("shots.atlas");
        fighterTextures = new TextureAtlas("PlanesSmall.atlas");
        fighter2Textures = new TextureAtlas("terron.atlas");
        enemyTextures = new TextureAtlas("enemy.atlas");
        invaderTextures = new TextureAtlas("NewInvaders.atlas");
        bonusTextures = new TextureAtlas("bonus.atlas");
        backGround = new Texture(Gdx.files.internal("space_0.png"));
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("MATURASC.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 32;
        font = generator.generateFont(parameter);
        font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        generator.dispose();
        fadePixel = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        fadePixel.setColor(1, 1, 1, 1);
        fadePixel.fill();
        PixmapTextureData textureData = new PixmapTextureData(fadePixel, Pixmap.Format.RGBA8888, false, false, true);
        fadeTexture = new Texture(textureData);
        fadeTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
    }

    public AtlasRegion getTextureRegion(String id) {
        AtlasRegion region = regions.get(id);
        if (region == null) {
            TextureAtlas atlas;
            if (id.startsWith("shot")) {
                atlas = shotTextures;
            } else if (id.startsWith("plane")) {
                atlas = fighterTextures;
            } else if (id.startsWith("terron")) {
                atlas = fighter2Textures;
            } else if (id.startsWith("UP_") || id.startsWith("WP_")) {
                atlas = bonusTextures;
            } else if (id.startsWith("invader")) {
                atlas = invaderTextures;
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

    public Texture getFadeTexture() {
        return fadeTexture;
    }

    public void dispose() {
        shotTextures.dispose();
        fighterTextures.dispose();
        enemyTextures.dispose();
        backGround.dispose();
        font.dispose();
        fadePixel.dispose();
        fadeTexture.dispose();
    }
}
