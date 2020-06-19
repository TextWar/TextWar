package cn.qqtextwar.blocks;

import cn.qqtextwar.math.Vector;

import java.util.Objects;

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

    @Override
    public String toString() {
        return "Block{" +
                "vector=" + vector +
                ", id=" + id +
                ", cross=" + cross +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Block block = (Block) o;
        return id == block.id &&
                cross == block.cross &&
                Objects.equals(vector, block.vector);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vector, id, cross);
    }
}
