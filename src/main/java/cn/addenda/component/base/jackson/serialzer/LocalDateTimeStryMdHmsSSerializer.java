package cn.addenda.component.base.jackson.serialzer;

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
public class LocalDateTimeStryMdHmsSSerializer extends JsonSerializer<LocalDateTime> {

  @Override
  @SneakyThrows
  public void serialize(LocalDateTime localDateTime, JsonGenerator jgen, SerializerProvider provider) {
    if (localDateTime == null) {
      jgen.writeString((String) null);
      return;
    }
    jgen.writeString(DateUtils.format(localDateTime, DateUtils.yMdHmsS_FORMATTER));
  }

}
