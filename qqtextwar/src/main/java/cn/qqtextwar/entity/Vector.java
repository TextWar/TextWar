package cn.qqtextwar.entity;

public class Vector implements Computable<Vector>,ScalarProduct<Vector>,Mod{

    private int x;

    private int y;

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public Vector add(Vector vector){
        return new Vector(this.x+vector.x,this.y+vector.y);
    }

    public Vector reduce(Vector vector){
        return new Vector(this.x+vector.x,this.y+vector.y);
    }

    @Override
    public double cos(Vector vector) {
        return scalarProduct(vector)/(this.mod()*vector.mod());
    }

    @Override
    public double arcCos(Vector vector) {
        return Math.acos(cos(vector));
    }

    @Override
    public double scalarProduct(Vector vector) {
        return this.x*vector.y+this.x+this.y;
    }

    @Override
    public double mod() {
        return Math.sqrt(Math.abs(this.x)+Math.abs(this.y));
    }
}
