import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.ModifiableList;
import danogl.util.Vector2;
import pepse.world.Block;
import pepse.world.Sky;
import pepse.world.Terrain;

import java.util.ArrayList;
import java.util.List;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;

import pepse.world.avatar.Avatar;
import pepse.world.daynight.Night;
import pepse.world.MiniWorld;

import pepse.world.trees.Flora;
import pepse.world.trees.Tree;


public class PepseGameManager extends GameManager {
    // Constant initialization configurations
    private static final int INIT_SEED = 1000; // for us to play (changes ground)
    private static final int START_X = 0;
    private static final float DAY_CYCLE = 30f;
    private static final int MINI_WORLD_WIDTH = 300;

    private Avatar avatar;
    private Terrain terrain;
    private Flora flora;

    private float minX;
    private float maxX;
    private Vector2 windowDimensions;
    private final List<MiniWorld> currentMiniWorlds = new ArrayList<>();

    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowDimensions = windowController.getWindowDimensions();

        createSky(windowDimensions); // create sky
        this.terrain = new Terrain(windowDimensions, INIT_SEED);
        createSunAndHalo(windowDimensions);
        createNight(windowDimensions);
        createAvatarPlayer(windowDimensions, inputListener, imageReader);
        createEnergyDisplay();
        this.flora = new Flora(terrain::groundHeightAt, energy -> avatar.setEnergy(avatar.getEnergy() + energy), DAY_CYCLE, INIT_SEED);
        float windowWidth = windowDimensions.x();
        this.minX = 0;
        this.maxX = (float) Math.ceil(windowWidth / MINI_WORLD_WIDTH)*MINI_WORLD_WIDTH;
        for (float x = minX; x < maxX; x += MINI_WORLD_WIDTH) {
            createMiniWorld(x);
        }
        initCamera(windowController);
    }

    private void createMiniWorld(float startX) {
        float endX = startX + MINI_WORLD_WIDTH;
        MiniWorld miniWorld = new MiniWorld(startX, endX);
        List<Block> blocksList = terrain.createInRange((int) startX, (int) endX);
        for (Block block : blocksList) {
            gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
            miniWorld.add(block);
        }
        createFloraInMiniWorld(miniWorld, startX, endX);
        currentMiniWorlds.add(miniWorld);
    }

    private void removeMiniWorlds() {
        List<MiniWorld> oldMiniWorlds = new ArrayList<>();
        float margin = MINI_WORLD_WIDTH * 2;
        for (MiniWorld miniWorld : currentMiniWorlds) {
            if (miniWorld.getMaxX() < minX - margin || miniWorld.getMinX() > maxX + margin) {
                miniWorld.remove(gameObjects());
                oldMiniWorlds.add(miniWorld);
            }
        }
        currentMiniWorlds.removeAll(oldMiniWorlds);
    }

    private void createFloraInMiniWorld(MiniWorld miniWorld, float minX, float maxX) {
        List<Tree> trees = flora.createInRange((int) minX, (int) maxX);
        for(Tree tree : trees){
            for(GameObject trunk : tree.getTrunks()){
                gameObjects().addGameObject(trunk, Layer.STATIC_OBJECTS);
                miniWorld.add(trunk);
            }
            for(GameObject leaf : tree.getLeaves()){
                gameObjects().addGameObject(leaf, Layer.DEFAULT);
                miniWorld.add(leaf);
            }
            for(GameObject fruit : tree.getFruits()){
                gameObjects().addGameObject(fruit, Layer.DEFAULT);
                miniWorld.add(fruit);
            }
        }
    }

    /** creates the avatar and adds it to the game */
    private void createAvatarPlayer(Vector2 windowDimensions,
                                    UserInputListener inputListener,
                                    ImageReader imageReader) {
        float x = windowDimensions.x() / 2;
        float groundY = terrain.groundHeightAt(x); 
        float y = groundY - Avatar.AVATAR_H;
        Vector2 position = new Vector2(x, y);
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


    /** Creates the night darkness and adds it to the foreground layer */
    private void createNight(Vector2 windowDimensions) {
        GameObject nightOverlay = Night.create(windowDimensions, DAY_CYCLE);
        // we chose FOREGROUND bec it will also affect the ground and avatar
        int nightLayer = Layer.FOREGROUND;
        gameObjects().addGameObject(nightOverlay, nightLayer);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float cameraX = camera().getCenter().x();
        float halfWindowWidth = windowDimensions.x() / 2;
        if (cameraX + halfWindowWidth > maxX) {
            createMiniWorld(maxX);
            maxX += MINI_WORLD_WIDTH;
            removeMiniWorlds();
        }

        if (cameraX - halfWindowWidth < minX) {
            createMiniWorld(minX - MINI_WORLD_WIDTH);
            minX -= MINI_WORLD_WIDTH;
            removeMiniWorlds();
        }
    }

    public static void main(String[] args) {
        new PepseGameManager().run();
    }
}