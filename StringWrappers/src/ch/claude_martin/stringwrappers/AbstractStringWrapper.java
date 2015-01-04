package ch.claude_martin.stringwrappers;

import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNull;

import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class AbstractStringWrapper implements StringWrapper {

  public AbstractStringWrapper() {
  }

  @Override
  public StringWrapper toUpperCase() {
    return UpperCase.of(this);
  }

  @Override
  public StringWrapper toLowerCase() {
    return LowerCase.of(this);
  }

  @Override
  public StringWrapper trim() {
    int begin = 0;
    int end = this.length();
    while ((begin < end) && (charAt(begin) <= ' ')) {
      begin++;
    }
    while ((begin < end) && (charAt(end - 1) <= ' ')) {
      end--;
    }
    return ((begin > 0) || (end < this.length())) ? substring(begin, end) : this;
  }

  @Override
  public StringWrapper trim(char chr, char... more) {
    Set<Character> set = new TreeSet<>();
    set.add(chr);
    for (Character c : more) {
      set.add(c);
    }
    return trim(set);
  }

  @Override
  public StringWrapper trim(Collection<Character> chars) {
    Set<Character> set;
    if (chars instanceof Set)
      set = (Set<Character>) chars;
    else
      set = new TreeSet<>(chars);

    int begin = 0;
    int end = this.length();

    while (begin < end && set.contains(this.charAt(begin))) {
      begin++;
    }

    while (begin < end && set.contains(this.charAt(end - 1))) {
      end--;
    }

    return Substring.of(this, begin, end);
  }

  @Override
  public StringWrapper concat(CharSequence... s) {
    return Concat.of(this, s);
  }

  @Override
  public StringWrapper map(CharMapper mapper) {
    return CharWrapper.of(this, mapper);
  }

  @Override
  public StringWrapper reversed() {
    return Reversed.of(this);
  }

  @Override
  public StringWrapper substring(int begin, int end) {
    return Substring.of(this, begin, end);
  }

  @Override
  public CharSequence subSequence(int start, int end) {
    return substring(start, end);
  }

  @Override
  public List<StringWrapper> split(String regexp) {
    List<StringWrapper> list = new ArrayList<>();
    Matcher m = Pattern.compile(regexp).matcher(this);
    while (m.find()) {
      list.add(this.substring(m.start(), m.end()));
    }
    return list;
  }

  @Override
  public boolean matches(String regex) {
    return Pattern.matches(regex, this);
  }

  @Override
  public boolean contentEquals(CharSequence cs) {
    if (cs instanceof StringBuffer) {
      synchronized (cs) {
        return StringUtils.equals(this, cs);
      }
    }
    return StringUtils.equals(this, cs);
  }

  @Override
  public byte[] getBytes(Charset charset) {
    requireNonNull(charset, "charset");
    return StringUtils.getBytes(this, charset);
  }

  @Override
  public String toString() {
    return new StringBuilder(this).toString();
  }

}
