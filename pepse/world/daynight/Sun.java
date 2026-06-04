package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.Color;

public class Sun {
    private static final String SUN_TAG = "sun";
    private static final float SUN_SIZE = 100f;
    private static final float INIT_SUN_HEIGHT = 1f / 3f;
    private static final float HORIZON_HEIGHT = 2f / 3f;
    private static final float FULL_CIRCLE = 360f;

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

    private static void sunMovement(GameObject sun, Vector2 initSunCenter, Vector2 windowDimensions,
                                    float cycleLength) {
        Vector2 cycleCenter = new Vector2(windowDimensions.x() / 2, windowDimensions.y() * HORIZON_HEIGHT);
        new Transition<>(sun,
                angle -> sun.setCenter(initSunCenter.subtract(cycleCenter).rotated(angle).add(cycleCenter))
                , 0f, FULL_CIRCLE, Transition.LINEAR_INTERPOLATOR_FLOAT, cycleLength,
                Transition.TransitionType.TRANSITION_LOOP, null);
    }
}
