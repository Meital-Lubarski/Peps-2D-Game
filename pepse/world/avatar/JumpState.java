package pepse.world.avatar;

import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;

import java.awt.event.KeyEvent;

/**
 * represents the Jump state. The avatar is NOT on ground!! (jumping or fall)
 * @author Diana Basil and Meital Lubarski
 */
public class JumpState implements State{
    private static final float DOUBLE_JUMP_ENERGY = 50f;
    private final AnimationRenderable animation;

    /** constructor for the jump state
     * @param animation The jump animation renderable */
    public JumpState(AnimationRenderable animation) {
        this.animation = animation;
    }

    /** checks keyboard to see if we should leave the jump state
     * @param avatar The avatar instance
     * @param inputListener The input channel reading keyboard
     */
    @Override
    public void handle(Avatar avatar, UserInputListener inputListener) {
        if (Math.abs(avatar.getVelocity().y()) < 1.0f) {
            avatar.changeState(Avatar.IDLE_STATE);
            return;
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_SPACE) && avatar.getVelocity().y() > 0) {
            if (avatar.getEnergy() >= DOUBLE_JUMP_ENERGY) {
                avatar.setEnergy(avatar.getEnergy() - DOUBLE_JUMP_ENERGY);
                avatar.transform().setVelocityY(Avatar.VELOCITY_Y);
            }
        }
        float xVel = 0;
        boolean left = inputListener.isKeyPressed(KeyEvent.VK_LEFT);
        boolean right = inputListener.isKeyPressed(KeyEvent.VK_RIGHT);

        if (left && right) {
            avatar.transform().setVelocityX(0);
            return;
        }
        if (left) {
            xVel -= Avatar.VELOCITY_X;
            avatar.renderer().setIsFlippedHorizontally(true);
        }
        if (right) {
            xVel += Avatar.VELOCITY_X;
            avatar.renderer().setIsFlippedHorizontally(false);
        }
        avatar.transform().setVelocityX(xVel);
    }

    /** executes the rules of the jump state
     * @param avatar The avatar instance
     * @param deltaTime The time since the last frame update */
    @Override
    public void stateRules(Avatar avatar, float deltaTime) {
        avatar.renderer().setRenderable(animation);
    }
}