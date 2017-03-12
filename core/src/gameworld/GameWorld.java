package gameworld;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

import gameobjects.Background;
import gameobjects.Bar;
import gameobjects.CenterCircle;
import gameobjects.Star;
import helpers.AssetLoader;
import noon.ActionResolver;
import noon.NoonGame;

/**
 * Created by ManuGil on 09/03/15.
 */

public class GameWorld {

    public final float w;
    private final int numberOfStars = 0;
    //GENERAL VARIABLES
    public float gameWidth;
    public float gameHeight;
    public float worldWidth;
    public float worldHeight;

    public ActionResolver actionResolver;
    public NoonGame game;
    public GameWorld world = this;

    //GAME CAMERA
    private GameCam camera;

    //VARIABLES
    private GameState gameState;
    private int score;

    //GAMEOBJECTS
    private Bar bar;
    private Background background;
    private CenterCircle centerCircle;
    private Array<Star> stars = new Array<Star>();

    public GameWorld(NoonGame game, ActionResolver actionResolver, float gameWidth,
                     float gameHeight, float worldWidth, float worldHeight) {

        this.gameWidth = gameWidth;
        this.w = gameHeight / 100;
        this.gameHeight = gameHeight;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.game = game;
        this.actionResolver = actionResolver;
        camera = new GameCam(this, 0, 0, gameWidth, gameHeight);
        gameState = GameState.RUNNING;


        background = new Background(this, 0, 0, gameWidth, gameHeight, AssetLoader.background,
                /*world.parseColor("11031A",1f)*/Color.WHITE);
        centerCircle = new CenterCircle(this,
                world.gameWidth / 2 - (AssetLoader.colorCircle.getRegionWidth() / 2),
                world.gameHeight / 2 - (AssetLoader.colorCircle.getRegionHeight() / 2),
                AssetLoader.colorCircle.getRegionWidth(), AssetLoader.colorCircle.getRegionHeight(),
                AssetLoader.colorCircle, Color.WHITE);

        bar = new Bar(this, world.gameWidth / 2 - (AssetLoader.bar1.getRegionWidth() / 2),
                world.gameHeight / 2 - 30, AssetLoader.bar1.getRegionWidth(),
                AssetLoader.bar1.getRegionHeight(),
                AssetLoader.bar1, Color.WHITE);

        for (int i = 0; i < numberOfStars; i++) {
            stars.add(new Star(world));
        }
    }


    public void update(float delta) {
        centerCircle.update(delta);
        bar.update(delta);
        for (int i = 0; i < numberOfStars; i++) {
            stars.get(i).update(delta);
        }
    }

    public void render(SpriteBatch batcher, ShapeRenderer shapeRenderer, ShaderProgram fontShader) {
        background.render(batcher,shapeRenderer);
        for (int i = 0; i < numberOfStars; i++) {
            stars.get(i).render(batcher,shapeRenderer);
        }
        camera.render(batcher, shapeRenderer);
        centerCircle.render(batcher, shapeRenderer);
    }

    public void finishGame() {
        saveScoreLogic();
    }

    private void saveScoreLogic() {
        AssetLoader.addGamesPlayed();
        int gamesPlayed = AssetLoader.getGamesPlayed();

        // GAMES PLAYED ACHIEVEMENTS!
        actionResolver.submitScore(score);
        actionResolver.submitGamesPlayed(gamesPlayed);
        if (score > AssetLoader.getHighScore()) {
            AssetLoader.setHighScore(score);
        }
        //checkAchievements();
    }

    public void startGame() {
        score = 0;
    }

    public GameCam getCamera() {
        return camera;
    }

    public int getScore() {
        return score;
    }

    public Bar getBar() {
        return bar;
    }

    public static Color parseColor(String hex, float alpha) {
        String hex1 = hex;
        if (hex1.indexOf("#") != -1) {
            hex1 = hex1.substring(1);
            // Gdx.app.log("Hex", hex1);
        }
        Color color = Color.valueOf(hex1);
        color.a = alpha;
        return color;
    }

    public boolean isRunning() {
        return gameState == GameState.RUNNING;
    }

    public CenterCircle getCenterCircle() {
        return centerCircle;
    }
}
