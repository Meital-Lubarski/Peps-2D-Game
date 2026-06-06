package pepse.world.trees;

import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import danogl.collisions.Collision;
import pepse.world.Block;

import java.awt.*;
import java.util.function.Consumer;

public class Fruit extends GameObject {
    public static final String FRUIT_TAG = "fruit";
    private static final String AVATAR_TAG = "avatar";
    private static final Color FRUIT_COLOR = Color.RED;
    private static final float FRUIT_SIZE = Block.SIZE * 0.7f;
    private static final float ENERGY_GAIN = 10f;

    private final float cycleLength;
    private final Consumer<Float> addEnergy;
    private boolean available = true;


    public Fruit(Vector2 topLeftCorner, float cycleLength, Consumer<Float> addEnergy) {
        super(topLeftCorner, Vector2.ONES.mult(FRUIT_SIZE), new OvalRenderable(FRUIT_COLOR));
        this.cycleLength = cycleLength;
        this.addEnergy = addEnergy;
        setTag(FRUIT_TAG);
    }

    public static Fruit create(float x, float y, float cycleLength, Consumer<Float> addEnergy){
        return new Fruit(new Vector2(x, y), cycleLength, addEnergy);
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision){
        super.onCollisionEnter(other, collision);
        if(!available || !other.getTag().equals(AVATAR_TAG)){
            return;
        }
        eat();
    }

    /** Gives energy and hides the fruit. */
    private void eat(){
        available = false;
        renderer().setOpaqueness(0f);
        addEnergy.accept(ENERGY_GAIN);
        new ScheduledTask(this, cycleLength, false, this::respawn);
    }

    /** Makes the fruit visible again. */
    private void respawn(){
        available = true;
        renderer().setOpaqueness(1f);
    }
}
