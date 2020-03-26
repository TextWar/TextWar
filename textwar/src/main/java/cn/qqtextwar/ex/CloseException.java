package cn.qqtextwar.ex;

/**
 * 服务端关闭可能会出现的异常，如重复关闭
 * 另外运用于关闭完成的信息
 */
public class CloseException extends ServerException {
    public CloseException(String message) {
        super(message);
    }
}
