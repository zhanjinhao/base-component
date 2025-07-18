package cn.addenda.component.base.test.exception;

import cn.addenda.component.base.BaseException;
import cn.addenda.component.base.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class SystemExceptionTest {

  @Test
  public void test1() {
    SystemException systemException = SystemException.unExpectedException();
    log.info("", systemException);
  }

  @Test
  public void test2() {
    BaseException baseException = new BaseException() {
    };
    log.info("", baseException);
  }

}
