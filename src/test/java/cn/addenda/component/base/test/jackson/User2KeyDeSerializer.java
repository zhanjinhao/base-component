package cn.addenda.component.base.test.jackson;

import cn.addenda.component.base.jackson.util.JacksonUtils;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import lombok.SneakyThrows;

public class User2KeyDeSerializer extends KeyDeserializer {

  @Override
  @SneakyThrows
  public Object deserializeKey(String key, DeserializationContext ctxt) {
    if (key == null || key.isEmpty()) {
      return null;
    }
    return JacksonUtils.toObj(key, User2.class);
  }

}
