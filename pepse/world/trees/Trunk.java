package pepse.world.trees;

import danogl.GameObject;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.utils.ColorSupplier;
import pepse.world.Block;

import java.awt.*;

/**
 * Creates trunk blocks for trees.
 *
 * @author Diana Basil and Meital Lubarski
 */
public class Trunk {
    /**
     * The tag used for trunk objects.
     */
    public static final String TRUNK_TAG = "trunk";
    private static final Color TRUNK_COLOR = new Color(100, 50, 20);

    /* Prevents creating Trunk instances. */
    private Trunk() {
    }

    /**
     * Creates a new trunk block at the given position.
     *
     * @param x The x coordinate of the trunk block.
     * @param y The y coordinate of the trunk block.
     * @return The created trunk object.
     */
    public static GameObject create(float x, float y) {
        Renderable renderable = new RectangleRenderable(ColorSupplier.approximateColor(TRUNK_COLOR));
        Block trunk = new Block(new Vector2(x, y), (Renderable) renderable);
        trunk.setTag(TRUNK_TAG);
        return trunk;
    }
}
