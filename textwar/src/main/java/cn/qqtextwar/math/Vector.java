package cn.qqtextwar.math;

import cn.qqtextwar.utils.Utils;

import java.util.Objects;

/**
 * 二维向量类，用于进行坐标计算
 *
 * 世界的所有格子的向量是由原点开始的向量。
 *
 * @author MagicLu @ 卢昶存
 */
public class Vector implements Computable<Vector>,ScalarProduct<Vector>,Mod,Direction{

    private double x;

    private double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    public Vector down(){
        return add(new Vector(0,-1));
    }

    public Vector up(){
        return add(new Vector(0,1));
    }

    public Vector left(){
        return add(new Vector(-1,0));
    }

    public Vector right(){
        return add(new Vector(1,0));
    }

    /** 向量相加*/
    public Vector add(Vector vector){
        return new Vector(this.x+vector.x,this.y+vector.y);
    }

    /** 向量相减 */
    public Vector reduce(Vector vector){
        return new Vector(this.x-vector.x,this.y-vector.y);
    }

    /** 获得和另一个向量的余弦值 */
    @Override
    public double cos(Vector vector) {
        return Utils.format(scalarProduct(vector)/(this.mod()*vector.mod()));
    }

    /** 获得反余弦值，degreeMeasure可以选择使用角度制 */
    @Override
    public double arcCos(Vector vector,boolean degreeMeasure) {
        double rad =  Math.acos(cos(vector));

        return Utils.format(degreeMeasure?Math.toDegrees(rad):rad);
    }

    /** 获得数量积 */
    @Override
    public double scalarProduct(Vector vector) {
        return this.x*vector.x+this.y*vector.y;
    }

    /** 获得向量长度 */
    @Override
    public double mod() {
        return Math.sqrt(Math.pow(this.x,2)+Math.pow(this.y,2));
    }

    /** 对于向量平分坐标公式的封装 */
    public Vector ratioVector(Vector vector2,double lambda){
        return new Vector((this.x + lambda * vector2.x)/(1+lambda),(this.y + lambda * vector2.y)/(1+lambda));
    }

    /** 相对于一个正方向单位向量的tan */
    @Override
    public double getDirection() {
        return y/x;
    }

    @Override
    public String toString() {
        return "Vector{" +
                "x=" + getX() +
                ", y=" + getY() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return x == vector.x &&
                y == vector.y;
    }

    public boolean parallel(Vector vector){
        return vector.x * this.y == this.x * vector.y;
    }

    public boolean vertical(Vector vector){
        return scalarProduct(vector) == 0;
    }

    public Vector toPositive(){
        return new Vector(Math.abs(x),Math.abs(y));
    }

    public double tan(Vector vector){
        return Math.tan(arcCos(vector,false));
    }
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
