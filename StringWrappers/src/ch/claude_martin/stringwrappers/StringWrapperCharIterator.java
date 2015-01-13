package ch.claude_martin.stringwrappers;

import static java.util.Objects.requireNonNull;

import java.util.NoSuchElementException;

/**
 * Iterator for StringWrapper or any CharSequence.
 */
public final class StringWrapperCharIterator implements CharIterator {
  private final CharSequence str;
  final int                  length;

  private int                pos = 0;

  private StringWrapperCharIterator(final CharSequence s) {
    this.str = s;
    this.length = this.str.length();
  }

  public static StringWrapperCharIterator of(final CharSequence s) {
    requireNonNull(s, "s");
    return new StringWrapperCharIterator(s);
  }

  @Override
  public boolean hasNext() {
    return this.pos < this.length;
  }

  public char nextChar() {
    if (this.pos >= this.length)
      throw new NoSuchElementException();
    return this.str.charAt(this.pos++);
  }

}
