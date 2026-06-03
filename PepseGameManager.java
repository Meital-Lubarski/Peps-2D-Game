import danogl.GameManager;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;

public class PepseGameManager extends GameManager {

    public PepseGameManager() {
        super("Pepse Game", new Vector2(1200, 700));
    }

    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
    }

    public static void main(String[] args) {
        // new PepseGameManager().run();
        new PepseGameManager().run();
    }
}
