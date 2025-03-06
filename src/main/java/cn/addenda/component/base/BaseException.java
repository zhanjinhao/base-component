package cn.addenda.component.base;

import cn.addenda.component.base.exception.SystemException;

/**
 * 这个异常正常情况下是不应该出现的，因为工具类的实现定义了确定的输入输出。
 * 在业务系统，由于依赖用户的输入，确定的输入往往很难达到。
 *
 * @author addenda
 * @since 2022/2/14 19:16
 */
public abstract class BaseException extends SystemException {
  protected BaseException() {
    super();
  }

  protected BaseException(String message) {
    super(message);
  }

  protected BaseException(String message, Throwable cause) {
    super(message, cause);
  }

  protected BaseException(Throwable cause) {
    super(cause);
  }

  protected BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  @Override
  public String moduleName() {
    return "base";
  }

  @Override
  public String componentName() {
    return "base";
  }

}
