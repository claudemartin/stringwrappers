package ch.claude_martin.stringwrappers;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;

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

  StringWrapper trim();

  StringWrapper trim(final char chr, final char... more);

  StringWrapper trim(final Collection<Character> chars);

  StringWrapper concat(final CharSequence... s);

  /** Maps all characters. */
  StringWrapper map(final CharMapper mapper);

  StringWrapper substring(final int begin, final int end);

  List<StringWrapper> split(final String regexp);

  boolean matches(final String regex);

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
  boolean contentEquals(final CharSequence cs);

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
  byte[] getBytes(final Charset charset);

  boolean isEmpty();

  StringWrapper reversed();

  /** Repeat this string x times. */
  StringWrapper repeat(final int x);

  boolean endsWith(final CharSequence string);

  /**
   * Iterate over all Characters.
   */
  @Override
  CharIterator iterator();

}
