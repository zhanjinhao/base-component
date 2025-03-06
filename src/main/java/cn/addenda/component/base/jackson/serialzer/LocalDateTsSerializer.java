package cn.addenda.component.base.jackson.serialzer;

import cn.addenda.component.base.datetime.DateUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.SneakyThrows;

import java.math.BigInteger;
import java.time.LocalDate;

/**
 * @author addenda
 * @since 2021/9/13
 */
public class LocalDateTsSerializer extends JsonSerializer<LocalDate> {

  @Override
  @SneakyThrows
  public void serialize(LocalDate localDate, JsonGenerator jgen, SerializerProvider provider) {
    if (localDate == null) {
      jgen.writeNumber((BigInteger) null);
      return;
    }
    jgen.writeFieldName(String.valueOf(DateUtils.localDateTimeToTimestamp(localDate.atTime(0, 0))));
  }

}
