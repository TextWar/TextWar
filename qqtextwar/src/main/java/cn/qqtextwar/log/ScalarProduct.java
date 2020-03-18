package cn.qqtextwar.log;

public interface ScalarProduct<T> {

    double cos(T vector);

    double arcCos(T vector,boolean degreeMeasure);

    double scalarProduct(T vector);

}
