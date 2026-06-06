package pepse.world.trees;

import danogl.GameObject;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.utils.ColorSupplier;
import pepse.world.Block;

import java.awt.*;

public class Trunk {
    public static final String TRUNK_TAG = "trunk";
    private static final Color TRUNK_COLOR = new Color(100, 50, 20);

    /** Prevents instances. */
    private Trunk(){}

    public static GameObject create(float x, float y){
        Renderable renderable = new RectangleRenderable(ColorSupplier.approximateColor(TRUNK_COLOR));
        Block trunk = new Block(new Vector2(x, y), (Renderable) renderable);
        trunk.setTag(TRUNK_TAG);
        return trunk;
    }
}
