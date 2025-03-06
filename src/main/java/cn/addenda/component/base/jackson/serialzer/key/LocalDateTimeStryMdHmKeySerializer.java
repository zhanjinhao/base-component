package cn.addenda.component.base.jackson.serialzer.key;

import cn.addenda.component.base.datetime.DateUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.SneakyThrows;

import java.time.LocalDateTime;

/**
 * @author addenda
 * @since 2021/9/13
 */
public class LocalDateTimeStryMdHmKeySerializer extends JsonSerializer<LocalDateTime> {

  @Override
  @SneakyThrows
  public void serialize(LocalDateTime localDateTime, JsonGenerator jgen, SerializerProvider provider) {
    if (localDateTime == null) {
      jgen.writeFieldName("null");
      return;
    }
    jgen.writeFieldName(DateUtils.format(localDateTime, DateUtils.yMdHm_FORMATTER));
  }

}
