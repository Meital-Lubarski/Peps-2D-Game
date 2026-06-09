package pepse.world.trees;

import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import danogl.collisions.Collision;
import pepse.world.Block;

import java.awt.*;
import java.util.function.Consumer;

/**
 * Represents a fruit that can be collected by the avatar.
 * A collected fruit gives energy, disappears, and reappears after one cycle.
 *
 * @author Diana Basil and Meital Lubarski
 */
public class Fruit extends GameObject {
    /**
     * The tag used for fruit objects.
     */
    public static final String FRUIT_TAG = "fruit";
    private static final String AVATAR_TAG = "avatar";
    private static final Color FRUIT_COLOR = Color.RED;
    private static final float FRUIT_SIZE = Block.SIZE * 0.7f;
    private static final float ENERGY_GAIN = 10f;

    private final float cycleLength;
    private final Consumer<Float> addEnergy;
    private boolean available = true;

    /**
     * Constructs a new fruit.
     *
     * @param topLeftCorner The top-left position of the fruit.
     * @param cycleLength   The time after which the fruit reappears.
     * @param addEnergy     A callback that adds energy to the avatar.
     */
    public Fruit(Vector2 topLeftCorner, float cycleLength, Consumer<Float> addEnergy) {
        super(topLeftCorner, Vector2.ONES.mult(FRUIT_SIZE), new OvalRenderable(FRUIT_COLOR));
        this.cycleLength = cycleLength;
        this.addEnergy = addEnergy;
        setTag(FRUIT_TAG);
    }

    /**
     * Creates a new fruit at the given position.
     *
     * @param x           The x coordinate of the fruit.
     * @param y           The y coordinate of the fruit.
     * @param cycleLength The time after which the fruit reappears.
     * @param addEnergy   A callback that adds energy to the avatar.
     * @return The created fruit.
     */
    public static Fruit create(float x, float y, float cycleLength, Consumer<Float> addEnergy) {
        return new Fruit(new Vector2(x, y), cycleLength, addEnergy);
    }

    /**
     * Handles a collision with another game object.
     *
     * @param other     The object colliding with the fruit.
     * @param collision The collision data.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (!available || !other.getTag().equals(AVATAR_TAG)) {
            return;
        }
        eat();
    }

    /* Gives energy to the avatar and hides the fruit until it respawns. */
    private void eat() {
        available = false;
        renderer().setOpaqueness(0f);
        addEnergy.accept(ENERGY_GAIN);
        new ScheduledTask(this, cycleLength, false, this::respawn);
    }

    /* Makes the fruit available and visible again. */
    private void respawn() {
        available = true;
        renderer().setOpaqueness(1f);
    }
}
