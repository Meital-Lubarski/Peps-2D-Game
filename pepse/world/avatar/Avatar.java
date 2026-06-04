package pepse.world.avatar;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.util.Vector2;
import java.util.ArrayList;
import java.util.List;

/** acts as a publisher in the Observer Design Pattern for energy updates
 * The Avatar maintains a list of EnergyObservers and notifies them
 */
public class Avatar extends GameObject {
    // constant from platformer
    protected static final float VELOCITY_X = 400;
    protected static final float VELOCITY_Y = -650;
    private static final float GRAVITY = 600;

    // this size match Block.SIZE, avatar will align with game grid
    private final UserInputListener inputListener;
    private static final Vector2 AVATAR_SIZE = new Vector2(50, 50);
    private float energy = 100.0f;

    private final List<EnergyObserver> observers = new ArrayList<>();
    private State currentState;

    /** Constructor for Avatar entity. */
    public Avatar(Vector2 topLeftCorner, UserInputListener inputListener, ImageReader imageReader) {
        super(topLeftCorner, AVATAR_SIZE, imageReader.readImage("assets\\idle_0.png", true));
        this.inputListener = inputListener;
        // TODO IMPLIMENT current state in start is IDLE STATE

        // code from platformer:
        physics().preventIntersectionsFromDirection(Vector2.ZERO); // takes avatar down
        transform().setAccelerationY(GRAVITY); // prevents avatar from sinking in ground
    }
    /** registers a new observer to receive updates */
    public void register(EnergyObserver observer) {
        observers.add(observer);
        observer.updateEnergy(energy);
    }
    /** Iterates the list of observers and updates them */
    private void notifyObservers() {
        for (EnergyObserver observer : observers) {
            observer.updateEnergy(energy);
        }
    }
    /** setter for energy, notify's all subscribers*/
    public void setEnergy(float energy) {
        float oldEnergy = this.energy;
        this.energy = Math.max(0f, Math.min(100f, energy));
        if (oldEnergy != this.energy) {
            notifyObservers();
        }
    }
    /** getter for energy*/
    public float getEnergy() {
        return this.energy;
    }

    public void changeState(State newState) {
        this.currentState = newState;
    }

    @Override
    public void update(float deltaTime) {
        // TODO IMPLIMENT THIS!
    }
}
