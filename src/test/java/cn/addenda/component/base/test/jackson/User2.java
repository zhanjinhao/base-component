package cn.addenda.component.base.test.jackson;

import cn.addenda.component.base.jackson.deserialzer.LocalDateTimeTsDeSerializer;
import cn.addenda.component.base.jackson.serialzer.LocalDateTimeTsSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@JsonSerialize(keyUsing = User2KeySerializer.class, nullsUsing = User2KeySerializer.class)
@JsonDeserialize(keyUsing = User2KeyDeSerializer.class)
public class User2<T> {

  @JsonSerialize(using = LocalDateTimeTsSerializer.class)
  @JsonDeserialize(using = LocalDateTimeTsDeSerializer.class)
  private LocalDateTime birth;
  private LocalDateTime death;

  private T t;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User2<?> user = (User2<?>) o;
    return Objects.equals(birth, user.birth) && Objects.equals(death, user.death) && Objects.equals(t, user.t);
  }

  @Override
  public int hashCode() {
    return Objects.hash(birth, death, t);
  }

  @Override
  public String toString() {
    return "User2{" +
            "birth=" + birth +
            ", death=" + death +
            ", t=" + t +
            '}';
  }
}
