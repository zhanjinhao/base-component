package cn.addenda.component.jdk.exception.component;

public class ComponentServiceErrorCode implements IComponentServiceErrorCode {

  private final String message;

  public ComponentServiceErrorCode(String message) {
    this.message = message;
  }

  @Override
  public String code() {
    return "component-service";
  }

  @Override
  public String message() {
    return message;
  }
}
