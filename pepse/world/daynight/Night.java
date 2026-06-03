package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import java.awt.Color;

/** responsible for the brightness in day night cycle
* @author Diana Basil and Meital Lubarski * */
public class Night {
    private static final Float MIDNIGHT_OPACITY = 0.5f;
    private static final float START_OPACITY = 0f;
    private static final float HALF_DAY = 2.0f;

    /** puts a black block on screen with changing brightness during the day night cycle
     *@param windowDimensions window dimensions of the game
     *@param cycleLength time in seconds to complete a 24 hours cycle
     * @return A GameObject (block) representing the brightness */
    public static GameObject create(Vector2 windowDimensions, float cycleLength) {

        GameObject night = new GameObject( Vector2.ZERO, windowDimensions,
                new RectangleRenderable(Color.BLACK)
        );

        night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        night.setTag( "night");

        float transitionTime = cycleLength / HALF_DAY; // split day into half a day

        new Transition<Float>(
                night,
                night.renderer()::setOpaqueness,
                START_OPACITY,
                MIDNIGHT_OPACITY,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                transitionTime,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null
        );
        return night;
    }
}
