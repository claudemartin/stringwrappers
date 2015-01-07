package ch.claude_martin.stringwrappers;

import static java.util.Objects.requireNonNull;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Represents an empty string. This is a singleton because every empty string is
 * the same.
 */
public enum EmptyWrapper implements StringWrapper {
  INSTANCE;

  @Override
  public int length() {
    return 0;
  }

  @Override
  public char charAt(final int index) {
    throw new StringIndexOutOfBoundsException(index);
  }

  @Override
  public CharSequence subSequence(final int start, final int end) {
    return this.substring(start, end);
  }

  @Override
  public StringWrapper toUpperCase() {
    return this;
  }

  @Override
  public StringWrapper toLowerCase() {
    return this;
  }

  @Override
  public StringWrapper trim() {
    return this;
  }

  @Override
  public StringWrapper trim(final char chr, final char... more) {
    return this;
  }

  @Override
  public StringWrapper trim(final Collection<Character> chars) {
    return this;
  }

  @Override
  public StringWrapper concat(final CharSequence... s) {
    return Concat.of(s);
  }

  @Override
  public StringWrapper map(final CharMapper mapper) {
    return this;
  }

  @Override
  public StringWrapper substring(final int begin, final int end) {
    if (begin == 0 && end == 0)
      return this;
    throw new StringIndexOutOfBoundsException(begin != 0 ? begin : end);
  }

  @Override
  public List<StringWrapper> split(final String regexp) {
    return new ArrayList<>();
  }

  @Override
  public boolean matches(final String regex) {
    return Pattern.matches(regex, "");
  }

  @Override
  public boolean contentEquals(final CharSequence cs) {
    requireNonNull(cs, "cs");
    return cs.length() == 0;
  }

  @Override
  public byte[] getBytes(final Charset charset) {
    return new byte[0];
  }

  @Override
  public boolean isEmpty() {
    return true;
  }

  @Override
  public StringWrapper reversed() {
    return this;
  }

  @Override
  public StringWrapper repeat(final int x) {
    if (x < 0)
      throw new IllegalArgumentException("x<0");
    return this;
  }

  @Override
  public String toString() {
    return "";
  }

  @Override
  public boolean endsWith(final CharSequence string) {
    return requireNonNull(string, "string").length() == 0;
  }

  @Override
  public Iterator<Character> iterator() {
    return Collections.emptyIterator();
  }

}
