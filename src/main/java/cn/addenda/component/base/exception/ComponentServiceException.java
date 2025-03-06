package cn.addenda.component.base.exception;

/**
 * 使用场景：内部组件
 */
public class ComponentServiceException extends ServiceException {
  public ComponentServiceException() {
    super();
  }

  public ComponentServiceException(String message) {
    super(message);
  }

  public ComponentServiceException(String message, Throwable cause) {
    super(message, cause);
  }

  public ComponentServiceException(Throwable cause) {
    super(cause);
  }

  protected ComponentServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
