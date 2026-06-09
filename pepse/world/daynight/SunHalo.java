package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * Creates a glow effect around the sun.
 * The halo follows the sun throughout the day-night cycle.
 *
 * @author Diana Basil and Meital Lubarski
 */
public class SunHalo {
    /**
     * The tag used for the halo object.
     */
    private static final String SUN_HALO_TAG = "sun halo";
    private static final Color HALO_COLOR = new Color(255, 255, 0, 20);
    private static final float HALO_SIZE = 150f;

    /**
     * Creates a halo object that follows the given sun.
     *
     * @param sun The sun object that the halo follows.
     * @return The created halo object.
     */
    public static GameObject create(GameObject sun) {
        GameObject sunHalo = new GameObject(Vector2.ZERO, Vector2.ONES.mult(HALO_SIZE),
                new OvalRenderable(HALO_COLOR));
        sunHalo.setCenter(sun.getCenter());
        sunHalo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sunHalo.setTag(SUN_HALO_TAG);
        sunHalo.addComponent(deltaTime -> sunHalo.setCenter(sun.getCenter()));
        return sunHalo;
    }
}
