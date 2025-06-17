package cn.addenda.component.base.string;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.helpers.MessageFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Slf4jUtils {

  public static String format(String tql, Object... args) {
    // todo 检测是否存在MessageFormatter，不存在使用StrFormatUtils
    return MessageFormatter.arrayFormat(tql, args).getMessage();
  }

}
