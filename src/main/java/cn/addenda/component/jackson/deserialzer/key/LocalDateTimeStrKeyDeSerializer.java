package cn.addenda.component.jackson.deserialzer.key;

import cn.addenda.component.jdk.util.DateUtils;
import cn.addenda.component.jdk.util.StringUtils;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import lombok.SneakyThrows;

/**
 * @author addenda
 * @since 2021/9/13
 */
public class LocalDateTimeStrKeyDeSerializer extends KeyDeserializer {

  @Override
  @SneakyThrows
  public Object deserializeKey(String s, DeserializationContext ctxt) {
    if (s == null || s.isEmpty() || "null".equals(s)) {
      return null;
    }
    if (StringUtils.checkIsDigit(s) && s.length() > 8) {
      return DateUtils.timestampToLocalDateTime(Long.parseLong(s));
    }
    String yyyy = s.length() >= 4 ? s.substring(0, 4) : null;
    String MM = s.length() >= 7 ? s.substring(5, 7) : null;
    String dd = s.length() >= 10 ? s.substring(8, 10) : null;
    String HH = s.length() >= 13 ? s.substring(11, 13) : null;
    String mm = s.length() >= 16 ? s.substring(14, 16) : null;
    String ss = s.length() >= 19 ? s.substring(17, 19) : "00";
    String SSS = s.length() >= 23 ? s.substring(20, 23) : "000";
    if (yyyy == null || MM == null || dd == null || HH == null || mm == null) {
      throw new IllegalArgumentException(s);
    }
    return DateUtils.parseLdt(yyyy + "-" + MM + "-" + dd + " " + HH + ":" + mm + ":" + ss + "." + SSS, DateUtils.yMdHmsS_FORMATTER);
  }

}
