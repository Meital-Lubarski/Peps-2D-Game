package pepse.world.trees;

import danogl.GameObject;
import pepse.world.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class Tree {
    private static final int MIN_TRUNK_HEIGHT = 4;
    private static final int MAX_TRUNK_HEIGHT = 8;
    private static final int LEAF_RADIUS = 2;
    private static final double LEAF_PROBABILITY = 0.75;
    private static final double FRUIT_PROBABILITY = 0.12;
    private final List<GameObject> trunks = new ArrayList<>();
    private final List<GameObject> leaves = new ArrayList<>();
    private final List<GameObject> fruits = new ArrayList<>();

    private Tree(){}

    public static Tree create(int x, float groundY, Random random, float cycleLength, Consumer<Float> addEnergy){
        Tree tree = new Tree();
        int trunkHeight = MIN_TRUNK_HEIGHT + random.nextInt(MAX_TRUNK_HEIGHT - MIN_TRUNK_HEIGHT + 1);
        tree.createTrunk(x, groundY, trunkHeight);
        tree.createTreeTop(x, groundY, trunkHeight, random, cycleLength, addEnergy);
        return tree;
    }

    public List<GameObject> getTrunks(){
        return trunks;
    }

    public List<GameObject> getLeaves(){
        return leaves;
    }

    public List<GameObject> getFruits(){
        return fruits;
    }

    private void createTrunk(int x, float groundY, int trunkHeight){
        for(int i = 1; i <= trunkHeight; i++){
            trunks.add(Trunk.create(x, groundY - i * Block.SIZE));
        }
    }

    /** Creates leaves and fruits around the trunk top. */
    private void createTreeTop(int x, float groundY, int trunkHeight, Random random, float cycleLength, Consumer<Float> addEnergy){
        float topY = groundY - trunkHeight * Block.SIZE;
        for(int row = -LEAF_RADIUS; row <= LEAF_RADIUS; row++){
            for(int col = -LEAF_RADIUS; col <= LEAF_RADIUS; col++){
                createLeafCell(x, topY, row, col, random, cycleLength, addEnergy);
            }
        }
    }

    /** Tries to create one leaf and maybe one fruit. */
    private void createLeafCell(int x, float topY, int row, int col, Random random, float cycleLength, Consumer<Float> addEnergy){
        if(random.nextDouble() >= LEAF_PROBABILITY){
            return;
        }
        float objectX = x+ col * Block.SIZE;
        float objectY = topY + row * Block.SIZE;
        leaves.add(Leaf.create(objectX, objectY, random));
        if(random.nextDouble() < FRUIT_PROBABILITY){
            fruits.add(Fruit.create(objectX, objectY, cycleLength, addEnergy));
        }
    }
}
