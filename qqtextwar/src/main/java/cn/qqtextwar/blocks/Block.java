package cn.qqtextwar.blocks;

import cn.qqtextwar.math.Vector;

public class Block {

    private Vector vector;

    private long id;

    private boolean cross;

    public Block(Vector vector,long id, boolean cross) {
        this.vector = vector;
        this.id = id;
        this.cross = cross;
    }

    public Vector getVector() {
        return vector;
    }

    public long getId() {
        return id;
    }

    public boolean isCross() {
        return cross;
    }

    public void setCross(boolean cross) {
        this.cross = cross;
    }
}
