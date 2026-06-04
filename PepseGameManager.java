import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;
import pepse.world.Block;
import pepse.world.Sky;
import pepse.world.Terrain;
import java.util.List;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;

import pepse.world.daynight.Night;

public class PepseGameManager extends GameManager {
    // Constant initialization configurations
    private static final int INIT_SEED = 1000; // for us to play (changes ground)
    private static final int START_X = 0;
    private static final float DAY_CYCLE = 30f;


    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        Vector2 windowDimensions = windowController.getWindowDimensions();


        createSky(windowDimensions); // create sky
        createTerrain(windowDimensions); // create gound
        createSunAndHalo(windowDimensions);
        createNight(windowDimensions);

    }

    private void createSunAndHalo(Vector2 windowDimensions){
        GameObject sun = Sun.create(windowDimensions, DAY_CYCLE);
        GameObject sunHalo = SunHalo.create(sun);
        gameObjects().addGameObject(sunHalo, Layer.BACKGROUND + 1);
        gameObjects().addGameObject(sun, Layer.BACKGROUND + 2);
    }

    /** creates the sky object and adds it to the back layer  */
    private void createSky(Vector2 windowDimensions) {
        GameObject sky = Sky.create(windowDimensions);
        int skyLayer = Layer.BACKGROUND;
        gameObjects().addGameObject(sky, skyLayer);
    }

    /**creates the ground blocks and adds them to the static layer */
    private void createTerrain(Vector2 windowDimensions) {
        Terrain terrain = new Terrain(windowDimensions, INIT_SEED);
        int maxX = (int) windowDimensions.x();
        List<Block> blocksList = terrain.createInRange(START_X, maxX);
        for (Block block: blocksList) {
            gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
        }
    }

    /** Creates the night darkness and adds it to the foreground layer */
    private void createNight(Vector2 windowDimensions) {
        GameObject nightOverlay = Night.create(windowDimensions, DAY_CYCLE);
        // we chose FOREGROUND bec it will also affect the ground and avatar
        int nightLayer = Layer.FOREGROUND;
        gameObjects().addGameObject(nightOverlay, nightLayer);
    }


    public static void main(String[] args) {
        new PepseGameManager().run();
    }
}