package pepse.world.trees;

import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.utils.ColorSupplier;
import pepse.world.Block;

import java.awt.*;
import java.util.Random;

/**
 * Creates leaf objects and adds wind animations to them.
 * Leaves rotate and slightly change width to simulate wind movement.
 *
 * @author Diana Basil and Meital Lubarski
 */
public class Leaf {
    /**
     * The tag used for leaf objects.
     */
    public static final String LEAF_TAG = "leaf";

    private static final Color LEAF_COLOR = new Color(50, 200, 30);
    private static final float MAX_ANGLE = 12f;
    private static final float WIND_TIME = 1.5f;
    private static final float MAX_DELAY = 1f;
    private static final float MIN_WIDTH = Block.SIZE * 0.9f;
    private static final float MAX_WIDTH = Block.SIZE * 1.1f;

    /* Prevents creating Leaf instances. */
    private Leaf() {
    }

    /**
     * Creates a new leaf at the given position.
     *
     * @param x      The x coordinate of the leaf.
     * @param y      The y coordinate of the leaf.
     * @param random The random object used for the leaf animation delay.
     * @return The created leaf object.
     */
    public static GameObject create(float x, float y, Random random) {
        GameObject leaf = new GameObject(new Vector2(x, y), Vector2.ONES.mult(Block.SIZE),
                new RectangleRenderable(ColorSupplier.approximateColor(LEAF_COLOR)));
        leaf.setTag(LEAF_TAG);
        windAnimation(leaf, random);
        return leaf;
    }

    /* Starts the leaf wind animation after a small random delay. */
    private static void windAnimation(GameObject leaf, Random random) {
        float delay = random.nextFloat() * MAX_DELAY;
        new ScheduledTask(leaf, delay, false, () -> addWindTransitions(leaf));
    }

    /* Adds all wind transitions to the leaf. */
    private static void addWindTransitions(GameObject leaf) {
        addAngleTransition(leaf);
        addWidthTransition(leaf);
    }

    /* Adds a transition that changes the width of the leaf. */
    private static void addWidthTransition(GameObject leaf) {
        new Transition<>(leaf, leaf::setDimensions, new Vector2(MIN_WIDTH, Block.SIZE),
                new Vector2(MAX_WIDTH, Block.SIZE), Transition.LINEAR_INTERPOLATOR_VECTOR, WIND_TIME,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);
    }

    /* Adds a transition that rotates the leaf back and forth. */
    private static void addAngleTransition(GameObject leaf) {
        new Transition<>(leaf, leaf.renderer()::setRenderableAngle, -MAX_ANGLE, MAX_ANGLE,
                Transition.LINEAR_INTERPOLATOR_FLOAT, WIND_TIME,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);
    }
}
