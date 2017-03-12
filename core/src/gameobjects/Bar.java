package gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

import gameworld.GameWorld;
import helpers.AssetLoader;

/**
 * Created by ManuGil on 20/03/15.
 */
public class Bar extends GameObject {

    public float angle;
    public float angleVel;
    public int type;

    public Bar(GameWorld world, float x, float y, float width, float height,
               TextureRegion texture,
               Color color) {
        super(world, x, y, width, height, texture, color);
        angle = 0;
        angleVel = 300;
        getFlashSprite().setRegion(AssetLoader.flashBar);
        setType(3);
        getSprite().setAlpha(0.9f);
    }


    @Override
    public void update(float delta) {
        super.update(delta);
        angle += angleVel*delta;
        if (angle >= 360 || angle <= -360) {
            angle = 0;
        }

        Gdx.app.log("Angle", angle + "");
        getSprite().setRotation(angle);
        getSprite().setOrigin(getSprite().getWidth() / 2, 15);
        getFlashSprite().setRotation(angle);
        getFlashSprite().setOrigin(getSprite().getWidth() / 2, 15);

    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        super.render(batch, shapeRenderer);
    }

    public void setType(int i) {
        switch (i) {
            case 1:
                type = 1;
                getSprite().setRegion(AssetLoader.bar1);
                break;
            case 2:
                type = 2;
                getSprite().setRegion(AssetLoader.bar2);
                break;
            case 3:
                type = 3;
                getSprite().setRegion(AssetLoader.bar3);
                break;
            case 4:
                type = 4;
                getSprite().setRegion(AssetLoader.bar4);
                break;
        }
    }

    public void changeToRandom() {
        int typ;
        do {
            typ = MathUtils.random(1, 4);
        } while (type == typ);
        type = typ;
        setType(type);
    }

    public void tap() {
        angleVel *= -1.0f;
        flash(0.12f, 0f);
        world.getCenterCircle().flash(0.12f, 0f);
        AssetLoader.success.play();
        changeToRandom();
    }

    public float getAngle() {
        return angle;
    }

}
