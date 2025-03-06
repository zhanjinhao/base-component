package cn.addenda.component.base.allocator;

import lombok.Setter;

public abstract class AbstractNamed implements Named {

  @Setter
  private String name;

  @Override
  public String getName() {
    return name;
  }
}
