package pepse.world.trees;

import pepse.world.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;


public class Flora {
    private static final double TREE_PROBABILITY = 0.1;
    private static final int TREE_SPACING = Block.SIZE * 2;
    private final Function<Float, Float> groundHeightAt;
    private final Consumer<Float> addEnergy;
    private final float cycleLength;
    private final int seed;


    /**
     * Constructs a flora generator.
     */
    public Flora(Function<Float, Float> groundHeight, Consumer<Float> addEnergy, float cycleLength,
                 int seed) {
        this.groundHeightAt = groundHeight;
        this.addEnergy = addEnergy;
        this.cycleLength = cycleLength;
        this.seed = seed;
    }

    public List<Tree> createInRange(int minX, int maxX) {
        List<Tree> trees = new ArrayList<>();
        int startX = adjustToBlock(minX);
        int endX = adjustToBlock(maxX);
        for (int x = startX; x < endX; x++) {
            createTree(x, trees);
        }
        return trees;
    }

    private int adjustToBlock(int x) {
        return (int) Math.floor((double) x / Block.SIZE) * Block.SIZE;
    }

    private void createTree(int x, List<Tree> trees) {
        Random random = new Random(Objects.hash(seed, x));
        if (random.nextDouble() >= TREE_PROBABILITY) {
            return;
        }
        float groundY = groundHeightAt.apply((float) x);
        trees.add(Tree.create(x, groundY, random, cycleLength, addEnergy));
    }

}
