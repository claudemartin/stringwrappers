package ch.claude_martin.stringwrappers;

import static java.util.Objects.requireNonNull;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is the common interface of all wrapper classes. This extends
 * {@link CharSequence} and adds some methods of {@link String}. It also
 * contains conveniance methods to create other wrappers.
 *
 * <p>
 * Note that a {@link StringWrapper} is not actually a {@link String} and no
 * String is {@link #equals(Object) equal} to any StringWrapper and vice versa.
 * The same is true for other {@link CharSequence}s like {@link StringBuilder}
 * and {@link CharBuffer}. Use
 * {@link StringUtils#equals(CharSequence, CharSequence)} or
 * {@link #contentEquals(CharSequence)} to check for equality of two
 * {@link CharSequence}s.
 */
public interface StringWrapper extends CharSequence, Iterable<Character> {

  /**
   * Converts all of the characters in this String to upper case.
   *
   * @see String#toUpperCase()
   */
  StringWrapper toUpperCase();

  /**
   * Converts all of the characters in this String to lower case.
   *
   * @see String#toLowerCase()
   */
  StringWrapper toLowerCase();

  default StringWrapper trim() {
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

  default StringWrapper trim(final char chr, final char... more) {
    final Set<Character> set = new TreeSet<>();
    set.add(chr);
    for (final Character c : more) {
      set.add(c);
    }
    return this.trim(set);
  }

  default StringWrapper trim(final Collection<Character> chars) {
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

  default StringWrapper concat(final CharSequence... s) {
    return Concat.of(this, s);
  }

  /** Maps all characters. */
  default StringWrapper map(final CharMapper mapper) {
    return CharWrapper.of(this, mapper);
  }

  default StringWrapper substring(final int begin, final int end) {
    return Substring.of(this, begin, end);
  }

  @Override
  default CharSequence subSequence(final int start, final int end) {
    return this.substring(start, end);
  }

  default List<StringWrapper> split(final String regexp) {
    final List<StringWrapper> list = new ArrayList<>();
    final Matcher m = Pattern.compile(regexp).matcher(this);
    while (m.find()) {
      list.add(this.substring(m.start(), m.end()));
    }
    return list;
  }

  default List<StringWrapper> split(final char chr) {
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

  default boolean matches(final String regex) {
    return Pattern.matches(regex, this);
  }

  /**
   * Compares this to the specified {@code CharSequence}. The result is
   * {@code true} if and only if this {@code StringWrapper} represents the same
   * sequence of char values as the specified sequence. Note that if the
   * {@code CharSequence} is a {@code StringBuffer} then the method synchronizes
   * on it.
   *
   * @param cs
   *          The sequence to compare this {@code String} against
   *
   * @return {@code true} if this {@code StringWrapper} represents the same
   *         sequence of char values as the specified sequence, {@code false}
   *         otherwise
   */
  default boolean contentEquals(final CharSequence cs) {
    if (cs instanceof StringBuffer) {
      synchronized (cs) {
        return StringUtils.equals(this, cs);
      }
    }
    return StringUtils.equals(this, cs);
  }

  /**
   * Encodes this {@code StringWrapper} into a sequence of bytes using the given
   * {@linkplain java.nio.charset.Charset charset}, storing the result into a
   * new byte array.
   *
   * @param charset
   *          The {@linkplain java.nio.charset.Charset} to be used to encode the
   *          {@code String}
   *
   * @return The resultant byte array
   *
   */
  default byte[] getBytes(final Charset charset) {
    requireNonNull(charset, "charset");
    return StringUtils.getBytes(this, charset);
  }

  default boolean isEmpty() {
    return this.length() == 0;
  }

  default StringWrapper reversed() {
    return Reversed.of(this);
  }

  /** Repeat this string x times. */
  default StringWrapper repeat(final int x) {
    return Concat.repeat(this, x);
  }

  default boolean startsWith(final CharSequence prefix) {
    return startsWith(prefix, 0);
  }

  default boolean startsWith(final CharSequence prefix, final int toffset) {
    final int len = this.length();
    for (int i = toffset, j = 0; i < len; i++) {
      if (this.charAt(i) != prefix.charAt(j++))
        return false;
    }
    return true;
  }

  default boolean endsWith(final CharSequence suffix) {
    return this.startsWith(suffix, this.length() - suffix.length());
  }

  /**
   * Iterate over all Characters.
   */
  @Override
  default CharIterator iterator() {
    return StringWrapperCharIterator.of(this);
  }

  default int indexOf(final int codePoint) {
    return indexOf(codePoint, 0);
  }

  default int indexOf(final char chr) {
    return this.indexOf(chr, 0);
  }

  int indexOf(final int codePoint, final int fromIndex);

  default int indexOf(final char chr, final int fromIndex) {
    final int length = this.length();
    if (fromIndex >= length)
      return -1;
    for (int i = Math.max(0, fromIndex); i < length; i++) {
      if (this.charAt(i) == chr) {
        return i;
      }
    }
    return -1;
  }

  default int lastIndexOf(final int codePoint) {
    return this.lastIndexOf(codePoint, 0);
  }

  default int lastIndexOf(final char chr) {
    return lastIndexOf(chr, 0);
  }

  int lastIndexOf(final int codePoint, final int fromIndex);

  default int lastIndexOf(final char chr, final int fromIndex) {
    final int length = this.length();
    if (fromIndex >= length)
      return -1;
    for (int i = length - 1 - fromIndex; i <= 0; i--) {
      if (this.charAt(i) == chr) {
        return i;
      }
    }
    return -1;
  }

}
