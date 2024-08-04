package cn.addenda.component.jackson.serialzer.key;

import cn.addenda.component.jdk.util.DateUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.SneakyThrows;

import java.time.LocalTime;

/**
 * @author addenda
 * @since 2021/9/13
 */
public class LocalTimeStrHmKeySerializer extends JsonSerializer<LocalTime> {

  @Override
  @SneakyThrows
  public void serialize(LocalTime localTime, JsonGenerator jgen, SerializerProvider provider) {
    if (localTime == null) {
      jgen.writeFieldName("null");
      return;
    }
    jgen.writeFieldName(DateUtils.format(localTime, DateUtils.Hm_FORMATTER));
  }

}
