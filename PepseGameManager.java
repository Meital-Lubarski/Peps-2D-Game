import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;
import pepse.world.Block;
import pepse.world.Sky;
import pepse.world.Terrain;
import java.util.List;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;

import pepse.world.avatar.Avatar;
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
        createAvatar(windowDimensions, inputListener, imageReader, windowController);
    }
    private void createAvatar(Vector2 windowDimensions,
                              UserInputListener inputListener,
                              ImageReader imageReader,
                              WindowController windowController) {
        // Position the avatar horizontally in the middle, and vertically sitting right above the floor baseline
        Vector2 initialPos = new Vector2(windowDimensions.x() / 2, windowDimensions.y() * 0.5f);

        Avatar avatar = new Avatar(initialPos, inputListener, imageReader);

        // Layer.DEFAULT ensures the avatar tracks physics collisions with Layer.STATIC_OBJECTS (the terrain)
        gameObjects().addGameObject(avatar, Layer.DEFAULT);

        // // ADDED: Instantiate the Energy Counter Display Object at the top-left corner (e.g., coordinates 20, 20)
        Vector2 energyUiPos = new Vector2(20, 20);
        pepse.world.avatar.EnergyNum energyDisplay = new pepse.world.avatar.EnergyNum(energyUiPos);

        // // ADDED: Essential step to ensure the text screen element follows the camera HUD viewport space!
        energyDisplay.setCoordinateSpace(danogl.components.CoordinateSpace.CAMERA_COORDINATES);

        // // ADDED: Add the counter to a top layer so it stays visible above backgrounds and assets
        gameObjects().addGameObject(energyDisplay, Layer.UI);

        // // ADDED: Register the UI element to receive continuous updates using the Observer pattern
        avatar.register(energyDisplay);

        // Configure the engine's tracking system so the camera smoothly pans as you move
        setCamera(new Camera(
                avatar,                                      // Object to target and follow
                Vector2.ZERO,                                // Offset from center of screen
                windowController.getWindowDimensions(),      // Dimensions of the viewport window
                windowController.getWindowDimensions()       // Total dimensions of the tracking area bound
        ));
    }
    // Comment to test pushing in git
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