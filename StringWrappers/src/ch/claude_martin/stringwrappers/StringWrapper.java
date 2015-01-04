package ch.claude_martin.stringwrappers;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

/**
 * This is the common interface of all wrapper classes. This extends {@link CharSequence} and adds
 * some methods of {@link String}. It also contains conveniance methods to create other wrappers.
 *
 * <p>
 * Note that a {@link StringWrapper} is not actually a {@link String} and no String is
 * {@link #equals(Object) equal} to any StringWrapper and vice versa. The same is true for other
 * {@link CharSequence}s like {@link StringBuilder} and {@link CharBuffer}. Use
 * {@link StringUtils#equals(CharSequence, CharSequence)} or {@link #contentEquals(CharSequence)} to
 * check for equality of two {@link CharSequence}s.
 */
public interface StringWrapper extends CharSequence {

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

  StringWrapper trim(char chr, char... more);

  StringWrapper trim(Collection<Character> chars);

  StringWrapper concat(CharSequence... s);

  /** Maps all characters. */
  StringWrapper map(CharMapper mapper);

  StringWrapper substring(int begin, int end);

  List<StringWrapper> split(String regexp);

  boolean matches(String regex);

  /**
   * Compares this to the specified {@code CharSequence}. The result is {@code true} if and only if
   * this {@code StringWrapper} represents the same sequence of char values as the specified
   * sequence. Note that if the {@code CharSequence} is a {@code StringBuffer} then the method
   * synchronizes on it.
   *
   * @param cs
   *          The sequence to compare this {@code String} against
   *
   * @return {@code true} if this {@code StringWrapper} represents the same sequence of char values
   *         as the specified sequence, {@code false} otherwise
   */
  boolean contentEquals(CharSequence cs);

  /**
   * Encodes this {@code StringWrapper} into a sequence of bytes using the given
   * {@linkplain java.nio.charset.Charset charset}, storing the result into a new byte array.
   *
   * @param charset
   *          The {@linkplain java.nio.charset.Charset} to be used to encode the {@code String}
   *
   * @return The resultant byte array
   *
   */
  byte[] getBytes(Charset charset);

  StringWrapper reversed();

}
