package pepse.world;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents a single block unit, extends GameObject.
 *
 * @author Diana Basil and Meital Lubarski
 */
public class Block extends GameObject {
    /**
     * pixel dimensions (width and height) of a single block
     */
    public static final int SIZE = 30;
    public static final String GROUND_TAG = "ground";
    /**
     * Constructs a new Block instance at a position.
     *
     * @param topLeftCorner The coordinates for the top-left of the block.
     * @param renderable    The visual asset or color to render for this block.
     */
    public Block(Vector2 topLeftCorner, Renderable renderable) {
        super(topLeftCorner, Vector2.ONES.mult(SIZE), renderable);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }
}
