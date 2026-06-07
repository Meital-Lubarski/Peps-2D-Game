import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import java.util.ArrayList;
import java.util.List;

public class MiniWorld {
    private final float minX;
    private final float maxX;
    private final List<GameObject> MiniWorldObjects = new ArrayList<>();

    public MiniWorld(float minX, float maxX) {
        this.minX = minX;
        this.maxX = maxX;
    }

    public float getMinX() {
        return minX;
    }
    public float getMaxX() {
        return maxX;
    }

    public void registerObject(GameObject obj) {
        MiniWorldObjects.add(obj);
    }

    public void unload(GameObjectCollection gameObjects) {
        for (GameObject obj : MiniWorldObjects) {
            gameObjects.removeGameObject(obj, Layer.STATIC_OBJECTS);
            gameObjects.removeGameObject(obj, Layer.STATIC_OBJECTS + 1);
            gameObjects.removeGameObject(obj, Layer.DEFAULT);
        }
        MiniWorldObjects.clear();
    }
}
