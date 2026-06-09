package pepse.world;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;

import java.util.ArrayList;
import java.util.List;

/**
 * responsible for a single world (1 from infinite world) in a coords range
 * encapsulates this world game objects (responsible to add them and delete them)
 *
 * @author Diana Basil and Meital Lubarski
 */
public class MiniWorld {
    private final float minX;
    private final float maxX;
    private final List<GameObject> MiniWorldObjects = new ArrayList<>();

    /**
     * constructor for a new MiniWorld in an X range
     *
     * @param minX starting (leftmost) X coordinate in this world
     * @param maxX ending (rightmost) X coordinate in this world
     */
    public MiniWorld(float minX, float maxX) {
        this.minX = minX;
        this.maxX = maxX;
    }

    /**
     * getter for min X coordinate in this world
     *
     * @return The minimum x
     */
    public float getMinX() {
        return minX;
    }

    /**
     * getter for max X coordinate in this world
     *
     * @return The max x
     */
    public float getMaxX() {
        return maxX;
    }

    /**
     * load objects to be as part of this world
     *
     * @param obj The GameObject to add
     */
    public void add(GameObject obj) {
        MiniWorldObjects.add(obj);
    }

    /**
     * unloads and completely removes all registered game objects in this world*
     *
     * @param gameObjects The collection of game objects
     */
    public void remove(GameObjectCollection gameObjects) {
        for (GameObject obj : MiniWorldObjects) {
            gameObjects.removeGameObject(obj, Layer.STATIC_OBJECTS);
            gameObjects.removeGameObject(obj,
                    Layer.STATIC_OBJECTS + 1); // flora top layer
            gameObjects.removeGameObject(obj, Layer.DEFAULT);
        }
        MiniWorldObjects.clear();
    }
}
