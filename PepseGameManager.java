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

public class PepseGameManager extends GameManager {
    // Constant initialization configurations
    private static final int INIT_SEED = 1000; // for us to play (changes ground)
    private static final int START_X = 0;
    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        Vector2 windowDimensions = windowController.getWindowDimensions();


        createSky(windowDimensions); // create sky
        createTerrain(windowDimensions); // create gound


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


    public static void main(String[] args) {
        new PepseGameManager().run();
    }
}
