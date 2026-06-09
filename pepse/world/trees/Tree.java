package pepse.world.trees;

import danogl.GameObject;
import pepse.world.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

/**
 * Represents a full tree made of trunks, leaves, and fruits.
 * This class acts as a facade for creating and accessing all tree parts.
 *
 * @author Diana Basil and Meital Lubarski
 */
public class Tree {
    private static final int MIN_TRUNK_HEIGHT = 4;
    private static final int MAX_TRUNK_HEIGHT = 8;
    private static final int LEAF_RADIUS = 2;
    private static final double LEAF_PROBABILITY = 0.75;
    private static final double FRUIT_PROBABILITY = 0.12;
    private final List<GameObject> trunks = new ArrayList<>();
    private final List<GameObject> leaves = new ArrayList<>();
    private final List<GameObject> fruits = new ArrayList<>();

    /* Prevents direct construction of Tree objects. */
    private Tree() {
    }

    /**
     * Creates a new tree at the given position.
     *
     * @param x           The x coordinate of the tree trunk.
     * @param groundY     The y coordinate of the ground at the tree position.
     * @param random      The random object used for the tree structure.
     * @param cycleLength The time after which fruits reappear.
     * @param addEnergy   A callback that adds energy to the avatar.
     * @return The created tree.
     */
    public static Tree create(int x, float groundY, Random random, float cycleLength,
                              Consumer<Float> addEnergy) {
        Tree tree = new Tree();
        int trunkHeight = MIN_TRUNK_HEIGHT + random.nextInt(MAX_TRUNK_HEIGHT - MIN_TRUNK_HEIGHT + 1);
        tree.createTrunk(x, groundY, trunkHeight);
        tree.createTreeTop(x, groundY, trunkHeight, random, cycleLength, addEnergy);
        return tree;
    }

    /**
     * Returns the trunk objects of the tree.
     *
     * @return The tree trunk objects.
     */
    public List<GameObject> getTrunks() {
        return trunks;
    }

    /**
     * Returns the leaf objects of the tree.
     *
     * @return The tree leaf objects.
     */
    public List<GameObject> getLeaves() {
        return leaves;
    }

    /**
     * Returns the fruit objects of the tree.
     *
     * @return The tree fruit objects.
     */
    public List<GameObject> getFruits() {
        return fruits;
    }

    /* Creates the trunk blocks of the tree. */
    private void createTrunk(int x, float groundY, int trunkHeight) {
        for (int i = 1; i <= trunkHeight; i++) {
            trunks.add(Trunk.create(x, groundY - i * Block.SIZE));
        }
    }

    /* Creates the leaves and fruits around the top of the trunk. */
    private void createTreeTop(int x, float groundY, int trunkHeight, Random random, float cycleLength,
                               Consumer<Float> addEnergy) {
        float topY = groundY - trunkHeight * Block.SIZE;
        for (int row = -LEAF_RADIUS; row <= LEAF_RADIUS; row++) {
            for (int col = -LEAF_RADIUS; col <= LEAF_RADIUS; col++) {
                createLeafCell(x, topY, row, col, random, cycleLength, addEnergy);
            }
        }
    }

    /* Tries to create a leaf cell, and possibly a fruit, at the given offset. */
    private void createLeafCell(int x, float topY, int row, int col, Random random, float cycleLength,
                                Consumer<Float> addEnergy) {
        if (random.nextDouble() >= LEAF_PROBABILITY) {
            return;
        }
        float objectX = x + col * Block.SIZE;
        float objectY = topY + row * Block.SIZE;
        leaves.add(Leaf.create(objectX, objectY, random));
        if (random.nextDouble() < FRUIT_PROBABILITY) {
            fruits.add(Fruit.create(objectX, objectY, cycleLength, addEnergy));
        }
    }
}
