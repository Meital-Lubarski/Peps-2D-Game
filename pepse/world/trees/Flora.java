package pepse.world.trees;

import pepse.world.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Generates trees in a given x range.
 * The class uses deterministic randomness so the same tree positions are
 * generated again when the same world area is recreated.
 *
 * @author Diana Basil and Meital Lubarski
 */
public class Flora {
    private static final double TREE_PROBABILITY = 0.8;
    private static final int TREE_SPACING = Block.SIZE * 5;
    private final Function<Float, Float> groundHeightAt;
    private final Consumer<Float> addEnergy;
    private final float cycleLength;
    private final int seed;


    /**
     * Constructs a new flora generator.
     *
     * @param groundHeight A function that returns the ground height at a given x coordinate.
     * @param addEnergy    A callback that adds energy to the avatar.
     * @param cycleLength  The length of one day-night cycle.
     * @param seed         The seed used for deterministic tree generation.
     */
    public Flora(Function<Float, Float> groundHeight, Consumer<Float> addEnergy, float cycleLength,
                 int seed) {
        this.groundHeightAt = groundHeight;
        this.addEnergy = addEnergy;
        this.cycleLength = cycleLength;
        this.seed = seed;
    }

    /**
     * Creates all trees in the given x range.
     *
     * @param minX The left bound of the range.
     * @param maxX The right bound of the range.
     * @return A list of trees created in the range.
     */
    public List<Tree> createInRange(int minX, int maxX) {
        List<Tree> trees = new ArrayList<>();
        int startX = adjustToSpacing(minX);
        for (int x = startX; x <= maxX; x += TREE_SPACING) {
            if (x >= minX && x < maxX) {
                createTree(x, trees);
            }
        }
        return trees;
    }

    /* Rounds an x coordinate down to the nearest tree spacing location. */
    private int adjustToSpacing(int x) {
        return (int) Math.floor((double) x / TREE_SPACING) * TREE_SPACING;
    }

    /* Tries to create a tree at the given x coordinate and adds it to the list. */
    private void createTree(int x, List<Tree> trees) {
        Random random = new Random(Objects.hash(seed, x));
        if (random.nextDouble() >= TREE_PROBABILITY) {
            return;
        }
        float groundY = groundHeightAt.apply((float) x);
        trees.add(Tree.create(x, groundY, random, cycleLength, addEnergy));
    }

}
