package ch.claude_martin.stringwrappers;

import static java.util.Objects.requireNonNull;

import javax.swing.JApplet;

/**
 * A substring of some {@link CharSequence}, that holds a reference to the original sequence. In
 * contrast to {@link String} this does not allocate a new array. For large source sequences this
 * may lead to more memory consumption because the source cen not be garbage collected.
 * 
 * The source doesn't have to be immutable, but shouldn't be altered while a Substring is used on
 * it.
 * 
 */
public final class Substring extends AbstractSourceWrapper {
  /** The start index, inclusive. */
  private final int begin;
  /** The end index, exclusive. */
  private final int end;

  private Substring(CharSequence source, int begin, int end) {
    super(source);
    if (begin < 0) {
      throw new StringIndexOutOfBoundsException(begin);
    }
    if (end > source.length()) {
      throw new StringIndexOutOfBoundsException(end);
    }
    final int subLen = end - begin;
    if (subLen < 0) {
      throw new StringIndexOutOfBoundsException(subLen);
    }

    this.begin = begin;
    this.end = end;
  }

  public static StringWrapper of(CharSequence source, int begin, int end) {
    requireNonNull(source, "source");
    if (begin == 0 && end == source.length())
      return NullWrapper.of(source);
    if (source instanceof Substring) {
      final Substring substr = (Substring) source;
      new Substring(substr.getSource(), substr.getBegin() + begin, substr.getBegin() + end);
    }
    return new Substring(source, begin, end);
  }

  public static CharSequence of(CharSequence source, int begin) {
    requireNonNull(source, "source");
    return of(source, begin, source.length());
  }

  public static CharSequence ofLength(CharSequence source, int begin, int length) {
    requireNonNull(source, "source");
    if (begin == 0 && length == source.length())
      return source;
    if (source instanceof Substring) {
      final Substring substr = (Substring) source;
      new Substring(substr.getSource(), substr.getBegin() + begin, substr.getBegin() + length);
    }
    return new Substring(source, begin, begin + length);
  }

  @Override
  public int length() {
    return this.getEnd() - this.getBegin();
  }

  @Override
  public char charAt(int index) {
    if (index < 0 || index >= this.length())
      throw new StringIndexOutOfBoundsException(index);
    return this.getSource().charAt(this.getBegin() + index);
  }

  @SuppressWarnings("hiding")
  @Override
  public CharSequence subSequence(int begin, int end) {
    return of(this, begin, end);
  }

  @Override
  public String toString() {
    return this.getSource().subSequence(this.getBegin(), this.getEnd()).toString();
  }

  int getBegin() {
    return this.begin;
  }

  int getEnd() {
    return this.end;
  }
}
