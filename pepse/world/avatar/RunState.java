package pepse.world.avatar;

import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import java.awt.event.KeyEvent;

/** represents the run state.
 * in this state the avatar is on ground!!
 * @author Diana Basil and Meital Lubarski
 */
public class RunState implements State{
    private static final float MIN_JUMP_ENERGY = 20f;
    private static final float MIN_RUN_ENERGY = 2f;
    private static final float RUN_ENERGY = 2f;
    private final AnimationRenderable animation;

    /** constructor for the run state
     * @param animation The run animation renderable */
    public RunState(AnimationRenderable animation) {
        this.animation = animation;
    }
    /** checks keyboard to see if we should leave the run state
     * @param avatar The avatar instance
     * @param inputListener The input channel reading keyboard
     */
    @Override
    public void handle(Avatar avatar, UserInputListener inputListener) {
        if (Math.abs(avatar.getVelocity().y()) > 1.0f) {
            avatar.changeState(Avatar.JUMP_STATE);
            return;
        }

        if (inputListener.isKeyPressed(KeyEvent.VK_SPACE)) {
            if (avatar.getEnergy() >= MIN_JUMP_ENERGY) {
                avatar.setEnergy(avatar.getEnergy() - MIN_JUMP_ENERGY);
                avatar.transform().setVelocityY(Avatar.VELOCITY_Y);
                avatar.changeState(Avatar.JUMP_STATE);
                return;
            }
        }

        float xVel = 0;
        boolean left = inputListener.isKeyPressed(KeyEvent.VK_LEFT);
        boolean right = inputListener.isKeyPressed(KeyEvent.VK_RIGHT);
        if (left && right) { // if both u rest!
            avatar.transform().setVelocityX(0);
            avatar.changeState(Avatar.IDLE_STATE);
            return;
        }
        // our run image looks like running right
        // so we use the setIsFlippedHorizontally to make it look left
        if (left) {
            xVel -= Avatar.VELOCITY_X;
            avatar.renderer().setIsFlippedHorizontally(true);
        }
        if (right) {
            xVel += Avatar.VELOCITY_X;
            avatar.renderer().setIsFlippedHorizontally(false);
        }
        if (xVel == 0 || avatar.getEnergy() < MIN_RUN_ENERGY) {
            avatar.transform().setVelocityX(0);
            avatar.changeState(Avatar.IDLE_STATE);
        } else {
            avatar.transform().setVelocityX(xVel);
        }
    }

    /** executes the rules of the run state
     * @param avatar The avatar instance
     * @param deltaTime The time since the last frame update */
    @Override
    public void stateRules(Avatar avatar, float deltaTime) {
        avatar.renderer().setRenderable(animation);
        avatar.setEnergy(avatar.getEnergy() - RUN_ENERGY); // -2 energy bec running
    }
}