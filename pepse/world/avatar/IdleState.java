package pepse.world.avatar;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;

import java.awt.event.KeyEvent;

/** represents Idle State using the State Design Pattern.
 * when the avatar is idle it is always on ground!!
 * @author di **/

public class IdleState implements State{
    private static final float MIN_JUMP_ENERGY = 20f;
    private static final float MIN_RUN_ENERGY = 2f;
    private static final float REST_ENERGY = 1f;

    private final AnimationRenderable animation;
    /** constructor for the idle state
     * @param animation The idle animation renderable */
    public IdleState(AnimationRenderable animation) {
        this.animation = animation;
    }

    /** checks keyboard to see if we should leave the Idle state
     * @param avatar The avatar instance
     * @param inputListener The input channel reading keyboard
     */
    @Override
    public void handle(Avatar avatar, UserInputListener inputListener) {
        if (inputListener.isKeyPressed(KeyEvent.VK_SPACE)) {
            if (avatar.getEnergy() >= MIN_JUMP_ENERGY) {
                avatar.setEnergy(avatar.getEnergy() - MIN_JUMP_ENERGY); //
                avatar.transform().setVelocityY(Avatar.VELOCITY_Y); // Apply upward speed
                avatar.changeState(Avatar.JUMP_STATE);
                return;
            }
        }
        float xVel = 0;
        boolean left = inputListener.isKeyPressed(KeyEvent.VK_LEFT);
        boolean right = inputListener.isKeyPressed(KeyEvent.VK_RIGHT);
        if (left && right) { // dont move
            avatar.transform().setVelocityX(0);
            return;
        }
        if (left){
            xVel -= Avatar.VELOCITY_X;
        }

        if (right)
        {
            xVel += Avatar.VELOCITY_X;
        }
        if (xVel != 0 && avatar.getEnergy() >= MIN_RUN_ENERGY) { // need at least 2 to run
            avatar.transform().setVelocityX(xVel);
            avatar.changeState(Avatar.RUN_STATE);
        } else {
            avatar.transform().setVelocityX(0); // dont move
        }
    }
    /** executes the rules of the Idle state
     * @param avatar The avatar instance
     * @param deltaTime The time since the last frame update */
    @Override
    public void stateRules(Avatar avatar, float deltaTime) {
            avatar.renderer().setRenderable(animation);
            avatar.setEnergy(avatar.getEnergy() + REST_ENERGY); // 1 energy bec resting
    }
}
