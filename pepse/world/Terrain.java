package pepse.world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.utils.ColorSupplier;
import pepse.utils.NoiseGenerator;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * responsible for generating ground in a range, calculate terrain height
 *
 * @author Diana Basil and Meital Lubarski
 *
 */
public class Terrain {

    private static final Color GROUND_COLOR = new Color(212, 123, 74);
    private static final int TERRAIN_DEPTH = 20;
    private static final float BASE_HEIGHT_FRAC = 2f / 3f;
    private static final int NOISE_MULT = 7;

    private final float groundHeightAtX0;
    private final NoiseGenerator noiseGenerator;
    private final Vector2 windowDimensions;

    /**
     * constructs a terrain
     *
     * @param windowDimensions The dimensions of the game window
     * @param seed             The seed for noise generation
     */
    public Terrain(Vector2 windowDimensions, int seed) {
        this.windowDimensions = windowDimensions;
        this.groundHeightAtX0 = windowDimensions.y() * BASE_HEIGHT_FRAC;
        this.noiseGenerator = new NoiseGenerator(seed, (int) groundHeightAtX0);
    }

    /**
     * calc ground height at x coordinate
     *
     * @param x The horizontal coordinate.
     * @return The vertical pixel position where ground surface begins.
     */
    public float groundHeightAt(float x) {
        float noise = (float) noiseGenerator.noise(x, Block.SIZE * NOISE_MULT);
        return groundHeightAtX0 + noise;
    }

    /**
     * generates a grid list of Block objects in a range
     *
     * @param minX The minimum horizontal coordinate limit.
     * @param maxX The maximum horizontal coordinate limit.
     * @return A list containing all generated terrain ground Block objects within the window chunk.
     */
    public List<Block> createInRange(int minX, int maxX) {
        List<Block> blocks = new ArrayList<>();

        // put start\end X to nearest multiple of block size
        int startX = (int) Math.floor((double) minX / Block.SIZE) * Block.SIZE;
        int endX = (int) Math.floor((double) maxX / Block.SIZE) * Block.SIZE;

        // loop in range (steps of block size) in each col,calc grid y height
        for (int x = startX; x <= endX; x += Block.SIZE) {
            float y = (float) Math.floor(groundHeightAt(x) / Block.SIZE) * Block.SIZE;

            // create ground by putting 20 blocks, in each block change a little the color
            for (int i = 0; i < TERRAIN_DEPTH; i++) {
                float blockY = y + (i * Block.SIZE);
                Vector2 blockPosition = new Vector2(x, blockY);
                Renderable renderable = new RectangleRenderable(
                        ColorSupplier.approximateColor(GROUND_COLOR)
                );
                Block block = new Block(blockPosition, renderable);
                block.setTag(Block.GROUND_TAG);
                blocks.add(block);
            }
        }
        return blocks;
    }
}