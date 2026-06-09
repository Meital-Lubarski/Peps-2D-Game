package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.Color;

/**
 * Creates and animates the sun in the day-night cycle.
 * The sun moves in a circular path around the horizon.
 *
 * @author Diana Basil and Meital Lubarski
 */
public class Sun {
    /**
     * The tag used for the sun object.
     */
    private static final String SUN_TAG = "sun";
    private static final float SUN_SIZE = 100f;
    private static final float INIT_SUN_HEIGHT = 1f / 3f;
    private static final float HORIZON_HEIGHT = 2f / 3f;
    private static final float FULL_CIRCLE = 360f;

    /**
     * Creates a new sun object and starts its movement.
     *
     * @param windowDimensions The dimensions of the game window.
     * @param cycleLength      The duration of one day-night cycle.
     * @return The created sun object.
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength) {
        Vector2 sunDimensions = Vector2.ONES.mult(SUN_SIZE);
        Vector2 initialSunCenter = new Vector2(windowDimensions.x() / 2,
                windowDimensions.y() * INIT_SUN_HEIGHT);
        GameObject sun = new GameObject(Vector2.ZERO, sunDimensions, new OvalRenderable(Color.YELLOW));
        sun.setCenter(initialSunCenter);
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag(SUN_TAG);
        sunMovement(sun, initialSunCenter, windowDimensions, cycleLength);
        return sun;
    }

    /* Creates the circular movement animation of the sun. */
    private static void sunMovement(GameObject sun, Vector2 initSunCenter, Vector2 windowDimensions,
                                    float cycleLength) {
        Vector2 cycleCenter = new Vector2(windowDimensions.x() / 2, windowDimensions.y() * HORIZON_HEIGHT);
        new Transition<>(sun,
                angle -> sun.setCenter(initSunCenter.subtract(cycleCenter).rotated(angle).add(cycleCenter))
                , 0f, FULL_CIRCLE, Transition.LINEAR_INTERPOLATOR_FLOAT, cycleLength,
                Transition.TransitionType.TRANSITION_LOOP, null);
    }
}
