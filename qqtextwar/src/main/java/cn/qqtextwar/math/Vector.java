package cn.qqtextwar.math;

/**
 * 二维向量类，用于进行坐标计算
 *
 * @author MagicLu @ 卢昶存
 */
public class Vector implements Computable<Vector>,ScalarProduct<Vector>,Mod,Direction{

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
        return new Vector(this.x+vector.x,this.y+vector.y);
    }

    /** 获得和另一个向量的余弦值 */
    @Override
    public double cos(Vector vector) {
        return scalarProduct(vector)/(this.mod()*vector.mod());
    }

    /** 获得反余弦值，degreeMeasure可以选择使用角度制 */
    @Override
    public double arcCos(Vector vector,boolean degreeMeasure) {
        double rad =  Math.acos(cos(vector));
        return degreeMeasure?((rad*180)/Math.PI):rad;
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
        return new Vector((int)((this.x + lambda * vector2.x)/(1+lambda)),(int)((this.y + lambda * vector2.y)/(1+lambda)));
    }

    /** 相对于一个正方向单位向量的夹角 */
    @Override
    public double getDirection() {
        return arcCos(new Vector(0,1),false);
    }
}
