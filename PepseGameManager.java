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
    private Avatar avatar;

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
        createAvatarPlayer(windowDimensions, inputListener, imageReader);
        createEnergyDisplay();
        initCamera(windowController);
    }

    /** creates the avatar and adds it to the game */
    private void createAvatarPlayer(Vector2 windowDimensions,
                                    UserInputListener inputListener,
                                    ImageReader imageReader) {
        // Note: To perfectly align with Section 6.2.2 requirements, you'll eventually want to
        // fetch the exact ground height using terrain.groundHeightAt(x) here!
        Vector2 position = new Vector2(windowDimensions.x() / 2, windowDimensions.y() * 0.5f);
        this.avatar = new Avatar(position, inputListener, imageReader);
        avatar.setTag("avatar");
        gameObjects().addGameObject(avatar, Layer.DEFAULT);
    }

    /** create energy number display */
    private void createEnergyDisplay() {
        Vector2 energyCoords = new Vector2(20, 20);
        pepse.world.avatar.EnergyNum energyDisplay = new pepse.world.avatar.EnergyNum(energyCoords);
        energyDisplay.setCoordinateSpace(danogl.components.CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(energyDisplay, Layer.UI);
        avatar.register(energyDisplay);
    }

    /** create camera to move with player.*/
    private void initCamera(WindowController windowController) {
        Vector2 windowDimensions = windowController.getWindowDimensions();
        setCamera(new Camera(
                this.avatar,
                Vector2.ZERO,
                windowDimensions,
                windowDimensions
        ));
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