package ch.claude_martin.stringwrappers;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterator for StringWrapper or any CharSequence.
 */
// Java 8 : implements PrimitiveIterator<Character, ???>
public final class StringIterator implements Iterator<Character> {
  private final CharSequence str;
  final int                  length;

  private int                pos = 0;

  private StringIterator(final CharSequence s) {
    this.str = s;
    this.length = this.str.length();
  }

  public static StringIterator of(final CharSequence s) {
    requireNonNull(s, "s");
    return new StringIterator(s);
  }

  @Override
  public boolean hasNext() {
    return this.pos < this.length;
  }

  @Override
  public Character next() {
    return this.nextChar();
  }

  public char nextChar() {
    if (this.pos >= this.length)
      throw new NoSuchElementException();
    return this.str.charAt(this.pos++);
  }

  // Use @Override if PrimitiveIterator.OfInt is used!
  public int nextInt() {
    return this.nextChar();
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }

}
