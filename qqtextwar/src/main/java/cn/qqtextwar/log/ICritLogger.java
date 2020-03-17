package cn.qqtextwar.log;

public interface ICritLogger {
    void crit(String message);

    void crit(String message, String prefix);

    void crit(String message, String prefix, String suffix);

    void multiCrit(Class<?> clz, String message, String prefix, String suffix);
}
