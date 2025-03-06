package cn.addenda.component.base.allocator;

import cn.addenda.component.base.BaseException;

/**
 * @author addenda
 * @since 2023/9/16 12:24
 */
public class AllocatorException extends BaseException {

  public AllocatorException() {
    super();
  }

  public AllocatorException(String message) {
    super(message);
  }

  public AllocatorException(String message, Throwable cause) {
    super(message, cause);
  }

  public AllocatorException(Throwable cause) {
    super(cause);
  }

  public AllocatorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  @Override
  public String moduleName() {
    return "base";
  }

  @Override
  public String componentName() {
    return "allocator";
  }
}
