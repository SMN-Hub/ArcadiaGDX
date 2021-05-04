package net.smappz.arcadia.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.PixmapTextureData;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

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
    private final Skin skin;

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
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        Label.LabelStyle menuLabelStyle = new Label.LabelStyle(font, new Color(1, 1, 1, 1));
        skin.add("menuLabel", menuLabelStyle);
        ImageButton.ImageButtonStyle imStyle = new ImageButton.ImageButtonStyle();
        imStyle.imageUp = new TextureRegionDrawable( new TextureRegion(new Texture("next.png")));
        skin.add("menuRunButton", imStyle);
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

    public Skin getSkin() {
        return skin;
    }

    public void dispose() {
        shotTextures.dispose();
        fighterTextures.dispose();
        enemyTextures.dispose();
        backGround.dispose();
        font.dispose();
        fadePixel.dispose();
        fadeTexture.dispose();
        skin.dispose();
    }
}
