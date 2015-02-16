package ch.claude_martin.stringwrappers;

import static java.util.Objects.requireNonNull;

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

  /**
   * Checks if this string can contain the given character. Returns false if it
   * is impossible that this string would contain the given character.
   *
   * @param chr
   *          The caracter
   * @return true, if this can contain chr.
   */
  @SuppressWarnings("static-method")
  protected boolean canContain(final char chr) {
    return true;
  }

  @Override
  public int indexOf(final int codePoint) {
    return this.indexOf(codePoint, 0);
  }

  @Override
  public int indexOf(final char chr) {
    return this.indexOf(chr, 0);
  }

  @Override
  public int indexOf(final char chr, final int fromIndex) {
    final int length = this.length();
    if (fromIndex >= length)
      return -1;
    if (!this.canContain(chr))
      return -1;
    for (int i = Math.max(0, fromIndex); i < length; i++) {
      if (this.charAt(i) == chr) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public int indexOf(final int codePoint, final int fromIndex) {
    final int length = this.length();
    if (fromIndex >= length)
      return -1;

    if (codePoint < Character.MIN_SUPPLEMENTARY_CODE_POINT) {
      return this.indexOf((char) codePoint, fromIndex);
    } else if (Character.isValidCodePoint(codePoint)) {
      final char hi = Character.highSurrogate(codePoint);
      final char lo = Character.lowSurrogate(codePoint);
      int i = Math.max(0, fromIndex), j;
      while ((i = this.indexOf(hi, i)) != -1) {
        j = i + 1;
        if (j == length)
          return -1;
        if (this.charAt(j) == lo)
          return i;
      }
    }
    return -1;
  }

  @Override
  public int lastIndexOf(final int codePoint) {
    return this.lastIndexOf(codePoint, 0);
  }

  @Override
  public int lastIndexOf(final char chr) {
    return this.lastIndexOf(chr, 0);
  }

  @Override
  public int lastIndexOf(final char chr, final int fromIndex) {
    final int length = this.length();
    if (fromIndex >= length)
      return -1;
    if (!this.canContain(chr))
      return -1;
    for (int i = length - 1 - fromIndex; i <= 0; i--) {
      if (this.charAt(i) == chr) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public int lastIndexOf(final int codePoint, final int fromIndex) {
    final int length = this.length();
    if (fromIndex >= length)
      return -1;

    if (codePoint < Character.MIN_SUPPLEMENTARY_CODE_POINT) {
      return this.lastIndexOf((char) codePoint, fromIndex);
    } else if (Character.isValidCodePoint(codePoint)) {
      final char hi = Character.highSurrogate(codePoint);
      final char lo = Character.lowSurrogate(codePoint);
      final int first = length - 1 - Math.max(0, fromIndex);
      int i = first;
      while ((i = this.lastIndexOf(hi, i)) != -1) {
        if (i == first)
          return -1;
        if (this.charAt(i + 1) == lo)
          return i;
      }
    }
    return -1;
  }

  @Override
  public List<StringWrapper> split(final char chr) {
    int off = 0;
    int next = 0;
    final ArrayList<StringWrapper> list = new ArrayList<>();
    while ((next = this.indexOf(chr, off)) != -1) {
      list.add(this.substring(off, next));
      off = next + 1;
    }
    if (off == 0)
      list.add(this);
    else
      list.add(this.substring(off, this.length()));

    return list;
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
    if (0 == lenThat)
      return true;
    if (lenThis < lenThat)
      return false;
    if (1 == lenThat)
      return this.charAt(lenThis - 1) == string.charAt(0);
    if (lenThis == lenThat)
      return this.contentEquals(string);

    while (lenThat > 0) {
      if (this.charAt(--lenThis) != string.charAt(--lenThat))
        return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return new StringBuilder(this).toString();
  }


}
