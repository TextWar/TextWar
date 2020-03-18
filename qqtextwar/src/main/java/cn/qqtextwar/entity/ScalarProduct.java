package cn.qqtextwar.entity;

public interface ScalarProduct<T> {

    double cos(T vector);

    double arcCos(T vector);

    double scalarProduct(T vector);

}
