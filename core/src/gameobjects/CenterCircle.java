package gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import gameworld.GameWorld;
import helpers.AssetLoader;

/**
 * Created by ManuGil on 20/03/15.
 */
public class CenterCircle extends GameObject {

    private GameObject insideBorder, outBorder;

    public CenterCircle(GameWorld world, float x, float y, float width, float height,
                        TextureRegion texture,
                        Color color) {
        super(world, x, y, width, height, texture, color);
        insideBorder = new GameObject(world, x, y, width, height, AssetLoader.dot,
                Color.BLACK);
        insideBorder.getSprite().setAlpha(0.4f);

        getFlashSprite().setRegion(AssetLoader.flashCircle);
        getSprite().setAlpha(0.9f);

    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        //outBorder.render(batch, shapeRenderer);
        insideBorder.render(batch, shapeRenderer);
        world.getBar().render(batch, shapeRenderer);
        super.render(batch, shapeRenderer);
    }
}
