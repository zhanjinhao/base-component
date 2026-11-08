package cn.addenda.component.base.string;

import cn.addenda.component.base.util.Assert;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author addenda
 * @since 2022/2/7 12:38
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtils {

  public static String expandWithSpecifiedChar(String str, char specifiedChar, int expectLength) {
    int length = str.length();
    StringBuilder zero = new StringBuilder();
    for (int i = length; i < expectLength; i++) {
      zero.append(specifiedChar);
    }
    return zero.append(str).toString();
  }

  public static String expandWithZero(String str, int expectLength) {
    return expandWithSpecifiedChar(str, '0', expectLength);
  }

  public static String join(String separator, String... values) {
    if (values.length == 0) {
      return "";
    }

    StringBuilder result = new StringBuilder();
    for (int i = 0; i < values.length; i++) {
      if (i == 0) {
        result.append(!hasText(values[i]) ? "" : values[i]);
      } else {
        result.append(!hasText(values[i]) ? "" : separator + values[i]);
      }
    }
    return result.toString();
  }

  public static boolean checkIsDigit(String piece) {
    if (!hasText(piece)) {
      return false;
    }
    return piece.matches("\\d+");
  }

  public static String joinArrayToString(String[] pieces, int fromIndex, int endIndex) {
    if (pieces == null) {
      return null;
    }
    int length = pieces.length;
    Assert.isTrue(endIndex <= length, StrFormatUtils.format("`endIndex` should be less than or equal {}!", length));
    Assert.isTrue(fromIndex >= 0, "`fromIndex` should be greater than or equal 0!");
    return String.join(" ", Arrays.stream(pieces).collect(Collectors.toList()).subList(fromIndex, endIndex));
  }

  public static String joinArrayToString(String content, int fromIndex, int endIndex) {
    if (!hasText(content)) {
      return content;
    }
    return joinArrayToString(content.split("\\s+"), fromIndex, endIndex);
  }

  public static String replaceCharAtIndex(String str, int index, char newChar) {
    if (str == null) {
      return null;
    }
    int length = str.length();
    Assert.isTrue(index < length, StrFormatUtils.format("`index` should be less than {}!", length));
    Assert.isTrue(index >= 0, "`index` should be greater than or equal 0!");
    return str.substring(0, index) + newChar + str.substring(index + 1);
  }

  public static String discardNull(String str) {
    if (str == null) {
      return "";
    }
    return str;
  }

  public static boolean hasText(CharSequence str) {
    return (str != null && str.length() > 0 && containsText(str));
  }

  public static boolean hasText(String str) {
    return str != null && !str.isEmpty() && containsText(str);
  }

  public static boolean containsText(CharSequence str) {
    int strLen = str.length();

    for (int i = 0; i < strLen; ++i) {
      if (!Character.isWhitespace(str.charAt(i))) {
        return true;
      }
    }

    return false;
  }

  public static boolean hasLength(CharSequence str) {
    return (str != null && str.length() > 0);
  }

  public static String biTrimSpecifiedChar(String input, char c) {
    if (input == null) {
      return null;
    }

    int length = input.length();

    int start = 0;
    for (int i = 0; i < length; i++) {
      if (input.charAt(i) != c) {
        start = i;
        break;
      }
    }

    int end = length - 1;
    for (int i = length - 1; i >= 0; i--) {
      if (input.charAt(i) != c) {
        end = i;
        break;
      }
    }

    return input.substring(start, end + 1);
  }

  public static boolean isStrictlyNumeric(CharSequence cs) {
    if (cs == null || cs.length() == 0) {
      return false;
    }
    for (int i = 0; i < cs.length(); i++) {
      if (!Character.isDigit(cs.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  private static final String DISPLAY_ELLIPSIS = "...";

  /**
   * 从头部截断字符串，结果长度不超过 {@code maxLength}。
   * <p>如果截断点落在代理对（surrogate pair）中间，被拆开的半个字符不会报错，
   * 而是以 {@code \\uXXXX} 形式的转义文本展示，此时结果长度会多出至多 5 个字符。
   *
   * @param string    原字符串，可为 null
   * @param maxLength 最大长度（按 char 计）；为负表示不截断
   * @return 截断后的字符串；{@code string} 为 null 时返回 null
   */
  public static String atMost(String string, int maxLength) {
    if (string == null) {
      return null;
    }
    if (maxLength < 0) {
      return string;
    }
    if (maxLength == 0) {
      return "";
    }
    return string.length() > maxLength
            ? escapeTrailingHighSurrogate(string.substring(0, maxLength))
            : string;
  }

  /**
   * 截断过长的展示文本：保留开头和结尾，中间用省略号代替。
   * <p>正常情况下结果长度不会超过 {@code maxLength}；当 {@code maxLength} 不足以放下
   * “开头 + 省略号 + 结尾”时，退化为只保留开头的 {@link #atMost(String, int)}。
   * <p>如果截断点落在代理对（surrogate pair）中间，被拆开的半个字符不会报错，
   * 而是以 {@code \\uXXXX} 形式的转义文本展示；此时结果长度可能比 {@code maxLength}
   * 多出至多 10 个字符（首尾各最多 5 个）。
   *
   * @param display    待展示的文本，可为 null
   * @param maxLength  最大展示长度（按 char 计）；为负表示不截断
   * @param tailLength 需要保留的结尾字符数；为负按 0 处理
   * @return 截断后的文本；{@code display} 为 null 时返回 null
   */
  public static String displayAtMost(String display, int maxLength, int tailLength) {
    return displayAtMost(display, maxLength, DISPLAY_ELLIPSIS, tailLength);
  }

  /**
   * 同 {@link #displayAtMost(String, int, int)}，但使用自定义省略号。
   *
   * @param ellipsis 自定义省略号；不能为 null
   */
  public static String displayAtMost(String display, int maxLength, String ellipsis, int tailLength) {
    if (display == null) {
      return null;
    }
    Assert.notNull(ellipsis, "ellipsis must not be null");
    if (maxLength < 0) {
      return display;
    }
    if (maxLength == 0) {
      return "";
    }
    if (display.length() <= maxLength) {
      return display;
    }
    int effectiveTailLength = Math.max(tailLength, 0);
    int prefixLength = maxLength - ellipsis.length() - effectiveTailLength;
    if (prefixLength <= 0) {
      return atMost(display, maxLength);
    }
    String prefix = escapeTrailingHighSurrogate(display.substring(0, prefixLength));
    String tail = escapeLeadingLowSurrogate(display.substring(display.length() - effectiveTailLength));
    return prefix + ellipsis + tail;
  }

  /**
   * 若 {@code str} 以孤立的高位代理结尾（说明截断点落在代理对中间），
   * 将其转义为 {@code \\uXXXX} 文本，避免展示出非法字符。
   */
  private static String escapeTrailingHighSurrogate(String str) {
    int lastIndex = str.length() - 1;
    if (lastIndex >= 0 && Character.isHighSurrogate(str.charAt(lastIndex))) {
      return str.substring(0, lastIndex) + toUnicodeEscape(str.charAt(lastIndex));
    }
    return str;
  }

  /**
   * 若 {@code str} 以孤立的低位代理开头（说明截断点落在代理对中间），
   * 将其转义为 {@code \\uXXXX} 文本，避免展示出非法字符。
   */
  private static String escapeLeadingLowSurrogate(String str) {
    if (!str.isEmpty() && Character.isLowSurrogate(str.charAt(0))) {
      return toUnicodeEscape(str.charAt(0)) + str.substring(1);
    }
    return str;
  }

  private static String toUnicodeEscape(char c) {
    return String.format("\\u%04X", (int) c);
  }

}
