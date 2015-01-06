package ch.claude_martin.stringwrappers;

import static java.util.Objects.requireNonNull;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
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
    while ((begin < end) && (this.charAt(begin) <= ' ')) {
      begin++;
    }
    while ((begin < end) && (this.charAt(end - 1) <= ' ')) {
      end--;
    }
    return ((begin > 0) || (end < this.length())) ? this.substring(begin, end) : this;
  }

  @Override
  public StringWrapper trim(final char chr, final char... more) {
    final Set<Character> set = new TreeSet<>();
    set.add(chr);
    for (final Character c : more) {
      set.add(c);
    }
    return this.trim(set);
  }

  @Override
  public StringWrapper trim(final Collection<Character> chars) {
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
  public StringWrapper concat(final CharSequence... s) {
    return Concat.of(this, s);
  }

  @Override
  public StringWrapper map(final CharMapper mapper) {
    return CharWrapper.of(this, mapper);
  }

  @Override
  public StringWrapper reversed() {
    return Reversed.of(this);
  }

  @Override
  public StringWrapper repeat(final int x) {
    if (x < 0)
      throw new IllegalArgumentException("x<0");
    if (x == 1)
      return this;
    return Concat.repeat(this, x);
  }

  @Override
  public StringWrapper substring(final int begin, final int end) {
    return Substring.of(this, begin, end);
  }

  @Override
  public CharSequence subSequence(final int start, final int end) {
    return this.substring(start, end);
  }

  @Override
  public List<StringWrapper> split(final String regexp) {
    final List<StringWrapper> list = new ArrayList<>();
    final Matcher m = Pattern.compile(regexp).matcher(this);
    while (m.find()) {
      list.add(this.substring(m.start(), m.end()));
    }
    return list;
  }

  @Override
  public boolean matches(final String regex) {
    return Pattern.matches(regex, this);
  }

  @Override
  public boolean contentEquals(final CharSequence cs) {
    if (cs instanceof StringBuffer) {
      synchronized (cs) {
        return StringUtils.equals(this, cs);
      }
    }
    return StringUtils.equals(this, cs);
  }

  @Override
  public boolean endsWith(final CharSequence string) {
    requireNonNull(string, "string");
    int lenThis = this.length();
    int lenThat = string.length();
    if (lenThat == 0)
      return true;
    if (lenThis < lenThat)
      return false;
    if (lenThis == lenThat)
      return this.contentEquals(string);

    while (lenThat > 0) {
      if (this.charAt(--lenThis) != string.charAt(--lenThat))
        return false;
    }
    return true;
  }

  @Override
  public byte[] getBytes(final Charset charset) {
    requireNonNull(charset, "charset");
    return StringUtils.getBytes(this, charset);
  }

  @Override
  public String toString() {
    return new StringBuilder(this).toString();
  }

  @Override
  public boolean isEmpty() {
    return this.length() == 0;
  }
}
