package cn.textwar.plugins;

public interface Cancellable {

    void setCancelled(boolean cancelled);

    boolean isCancelled();
}
