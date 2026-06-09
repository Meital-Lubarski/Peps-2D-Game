package pepse.world.avatar;

import danogl.gui.UserInputListener;

/**
 * state interface encapsulating behavior according to state of avatar
 * State Design Pattern is used bec the state needs to change its behavior dynamically
 * at runtime depending on its internal state
 *
 * @author Diana Basil and Meital Lubarski
 */
public interface State {
    /**
     * responsible for state transitions (that happening from keyboard)
     * checks if a button was pressed and if the Avatar needs to switch state
     */
    void handle(Avatar avatar, UserInputListener inputListener);

    /**
     * executes the rules of the current state, responsible for:
     * 1) changes in energy numbers
     * 2) Changes in animation
     **/
    void stateRules(Avatar avatar, float deltaTime);
}

