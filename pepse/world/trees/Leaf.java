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

public class Leaf {
    public static final String LEAF_TAG = "leaf";

    private static final Color LEAF_COLOR = new Color(50, 200, 30);
    private static final float MAX_ANGLE = 12f;
    private static final float WIND_TIME = 1.5f;
    private static final float MAX_DELAY = 1f;
    private static final float MIN_WIDTH = Block.SIZE * 0.9f;
    private static final float MAX_WIDTH = Block.SIZE * 1.1f;

    /**
     * Prevents instances.
     */
    private Leaf() {
    }

    public static GameObject create(float x, float y, Random random) {
        GameObject leaf = new GameObject(new Vector2(x, y), Vector2.ONES.mult(Block.SIZE),
                new RectangleRenderable(ColorSupplier.approximateColor(LEAF_COLOR)));
        leaf.setTag(LEAF_TAG);
        windAnimation(leaf, random);
        return leaf;
    }

    private static void windAnimation(GameObject leaf, Random random) {
        float delay = random.nextFloat() * MAX_DELAY;
        new ScheduledTask(leaf, delay, false, () -> addWindTransitions(leaf));
    }

    private static void addWindTransitions(GameObject leaf) {
        addAngleTransition(leaf);
        addWidthTransition(leaf);
    }

    private static void addWidthTransition(GameObject leaf) {
        new Transition<>(leaf, leaf::setDimensions, new Vector2(MIN_WIDTH, Block.SIZE),
                new Vector2(MAX_WIDTH, Block.SIZE), Transition.LINEAR_INTERPOLATOR_VECTOR, WIND_TIME,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);
    }

    private static void addAngleTransition(GameObject leaf) {
        new Transition<>(leaf, leaf.renderer()::setRenderableAngle, -MAX_ANGLE, MAX_ANGLE,
                Transition.LINEAR_INTERPOLATOR_FLOAT, WIND_TIME,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);
    }
}
