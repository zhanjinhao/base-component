package cn.addenda.component.base.sql;

import cn.addenda.component.base.BaseException;

public class ConnectionException extends BaseException {

  public ConnectionException() {
    super();
  }

  public ConnectionException(String message) {
    super(message);
  }

  public ConnectionException(String message, Throwable cause) {
    super(message, cause);
  }

  public ConnectionException(Throwable cause) {
    super(cause);
  }

  public ConnectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  @Override
  public String moduleName() {
    return "base";
  }

  @Override
  public String componentName() {
    return "connect";
  }
}
