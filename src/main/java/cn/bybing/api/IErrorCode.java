package cn.bybing.api;

public interface IErrorCode {
    /**
     * -1:失败
     * 200：成功
     * @return 错误编码
     */
    Integer getCode();

    /**
     * 错误描述
     * @return
     */
    String getMessage();

}
