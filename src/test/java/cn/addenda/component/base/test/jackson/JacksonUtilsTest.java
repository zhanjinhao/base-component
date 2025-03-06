package cn.addenda.component.base.test.jackson;

import cn.addenda.component.base.jackson.deserialzer.LocalDateTimeTsDeSerializer;
import cn.addenda.component.base.jackson.serialzer.LocalDateTimeTsSerializer;
import cn.addenda.component.base.jackson.util.JacksonUtils;
import cn.addenda.component.base.jackson.util.TypeFactoryUtils;
import cn.addenda.component.base.pojo.Binary;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class JacksonUtilsTest {

  @Test
  public void test1() {
    User<String> user = new User<>();
    String userJson = JacksonUtils.toStr(user);
    log.info("{}", userJson);
    User<String> user1 = JacksonUtils.toObj(userJson, TypeFactoryUtils.construct(User.class));
    log.info("{}", JacksonUtils.toStr(user1));
    Assert.assertEquals(user1, user);
  }

  @Test
  public void test2() {
    User<String> user = new User<>();
    user.setBirth(LocalDateTime.now());
    user.setDeath(LocalDateTime.now());
    user.setT("asd");
    String userJson = JacksonUtils.toStr(user);
    log.info("{}", userJson);
    User<String> user1 = JacksonUtils.toObj(userJson, TypeFactoryUtils.constructParametricType(User.class, String.class));
    log.info("{}", JacksonUtils.toStr(user1));
    Assert.assertEquals(user1, user);
  }

  @Test
  public void test3() {
    User2<String> user2String = new User2<>();
    user2String.setBirth(LocalDateTime.now());
    user2String.setDeath(LocalDateTime.now());
    user2String.setT("asd");
    User2<Integer> user2Integer = new User2<>();
    user2Integer.setBirth(LocalDateTime.now());
    user2Integer.setDeath(LocalDateTime.now());
    user2Integer.setT(123);
    Map<User2<String>, User2<Integer>> map1 = new HashMap<>();
    map1.put(user2String, user2Integer);

    JavaType user2StringType = TypeFactoryUtils.constructParametricType(User2.class, String.class);
    JavaType user2IntegerType = TypeFactoryUtils.constructParametricType(User2.class, Integer.class);
    JavaType javaType = TypeFactoryUtils.constructMap(user2StringType, user2IntegerType);

    String map1Json = JacksonUtils.toStr(map1);
    log.info("{}", map1Json);
    Map<User2<String>, User2<Integer>> map2 = JacksonUtils.toObj(map1Json, javaType);
    log.info("{}", JacksonUtils.toStr(map2));
    Assert.assertEquals(map1, map2);
  }

  @Test
  public void test4() {
    User2<Integer> user2Integer = new User2<>();
    user2Integer.setBirth(LocalDateTime.now());
    user2Integer.setDeath(LocalDateTime.now());
    user2Integer.setT(123);
    Map<User2<String>, User2<Integer>> map1 = new HashMap<>();
    map1.put(null, user2Integer);

    JavaType user2StringType = TypeFactoryUtils.constructParametricType(User2.class, String.class);
    JavaType user2IntegerType = TypeFactoryUtils.constructParametricType(User2.class, Integer.class);
    JavaType javaType = TypeFactoryUtils.constructMap(user2StringType, user2IntegerType);

    String map1Json = JacksonUtils.toStr(map1);
    log.info("{}", map1Json);
    Map<User2<String>, User2<Integer>> map2 = JacksonUtils.toObj(map1Json, javaType);
    log.info("{}", JacksonUtils.toStr(map2));
    Assert.assertEquals(map1, map2);
  }


  @Test
  public void test5() {
    testDefault();
    System.out.println("\n------------------\n");
    testCustomized();
    System.out.println("\n------------------\n");
    testJsonPropertyOrder();
  }

  private static void testJsonPropertyOrder() {
    Binary<Map<String, String>, Map<String, String>> binary = new Binary<>();
    Map<String, String> before = new HashMap<>();
    before.put("1", "a");
    Map<String, String> after = new HashMap<>();
    after.put("2", "b");
    binary.setF1(before);
    binary.setF2(after);

    String s = JacksonUtils.toStr(binary);
    System.out.println(s);
    Binary<Map<String, String>, Map<String, String>> mapMapBinary = JacksonUtils.toObj(s, new TypeReference<Binary<Map<String, String>, Map<String, String>>>() {
    });

    System.out.println(mapMapBinary);
    Assert.assertEquals(binary, mapMapBinary);

  }

  private static void testDefault() {
    Pojo source = new Pojo();
    String json = JacksonUtils.toStr(source);

    System.out.println("json: " + json);
    System.out.println("formatJson: " + JacksonUtils.formatJson(json));
    System.out.println("trimNull: " + JacksonUtils.trimNull(json));

    Pojo pojo = JacksonUtils.toObj(json, new TypeReference<Pojo>() {
    });

    System.out.println("pojo: " + pojo);
    Assert.assertEquals(source, pojo);
  }

  private static void testCustomized() {
    CustomizedPojo source = new CustomizedPojo();
    String json = JacksonUtils.toStr(source);

    System.out.println("json: " + json);
    System.out.println("formatJson: " + JacksonUtils.formatJson(json));
    System.out.println("trimNull: " + JacksonUtils.trimNull(json));

    CustomizedPojo pojo = JacksonUtils.toObj(json, new TypeReference<CustomizedPojo>() {
    });

    System.out.println("pojo: " + pojo);
    Assert.assertEquals(source, pojo);
  }

  @Setter
  @Getter
  @ToString
  private static class Pojo {

    private LocalDateTime localDateTime = LocalDateTime.now();
    private LocalDate localDate = LocalDate.now();
    private LocalTime localTime = LocalTime.now();
    private LocalDateTime nullLocalDateTime;
    private LocalDate nullLocalDate;
    private LocalTime nullLocalTime;

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Pojo pojo = (Pojo) o;
      return Objects.equals(localDateTime, pojo.localDateTime) && Objects.equals(localDate, pojo.localDate) && Objects.equals(localTime, pojo.localTime) && Objects.equals(nullLocalDateTime, pojo.nullLocalDateTime) && Objects.equals(nullLocalDate, pojo.nullLocalDate) && Objects.equals(nullLocalTime, pojo.nullLocalTime);
    }

    @Override
    public int hashCode() {
      return Objects.hash(localDateTime, localDate, localTime, nullLocalDateTime, nullLocalDate, nullLocalTime);
    }
  }

  @Setter
  @Getter
  @ToString
  private static class CustomizedPojo {

    @JsonSerialize(using = LocalDateTimeTsSerializer.class)
    @JsonDeserialize(using = LocalDateTimeTsDeSerializer.class)
    private LocalDateTime localDateTime = LocalDateTime.now();
    private LocalDate localDate = LocalDate.now();
    private LocalTime localTime = LocalTime.now();

    @JsonSerialize(using = LocalDateTimeTsSerializer.class)
    @JsonDeserialize(using = LocalDateTimeTsDeSerializer.class)
    private LocalDateTime nullLocalDateTime;
    private LocalDate nullLocalDate;
    private LocalTime nullLocalTime;

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      CustomizedPojo that = (CustomizedPojo) o;
      return Objects.equals(localDateTime, that.localDateTime) && Objects.equals(localDate, that.localDate) && Objects.equals(localTime, that.localTime) && Objects.equals(nullLocalDateTime, that.nullLocalDateTime) && Objects.equals(nullLocalDate, that.nullLocalDate) && Objects.equals(nullLocalTime, that.nullLocalTime);
    }

    @Override
    public int hashCode() {
      return Objects.hash(localDateTime, localDate, localTime, nullLocalDateTime, nullLocalDate, nullLocalTime);
    }
  }


}
