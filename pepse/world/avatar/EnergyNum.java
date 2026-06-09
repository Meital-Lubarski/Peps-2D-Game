package pepse.world.avatar;
import danogl.GameObject;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

/** responsible for the number shown which is the current energy of the avatar
 * @author Diana Basil and Meital Lubarski * */
public class EnergyNum extends GameObject implements EnergyObserver {
    private final TextRenderable textRenderable;
    private static final String INIT_ENERGY = "100";
    private static final String ENERGY = "Energy: ";
    private static final String PRECENT = "%";
    private static final Vector2 SIZE_NUM = new Vector2(100, 100);
    /** constructor - creates an energy number on screen
     * @param topLeftCorner  target pixel coordinates */
    public EnergyNum(Vector2 topLeftCorner) {
        super(topLeftCorner, SIZE_NUM, new TextRenderable(ENERGY+INIT_ENERGY+PRECENT));
        this.textRenderable = (TextRenderable) renderer().getRenderable();
    }

    @Override
    public void updateEnergy(float currentEnergy) {
        int energyInt = (int) currentEnergy;
        textRenderable.setString(ENERGY+energyInt +PRECENT);
    }
}
