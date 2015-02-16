package ch.claude_martin.stringwrappers;

import static java.util.Objects.requireNonNull;

/**
 * A substring of some {@link CharSequence}, that holds a reference to the
 * original sequence. In contrast to {@link String} this does not allocate a new
 * array. For large source sequences this may lead to more memory consumption
 * because the source cen not be garbage collected.
 *
 * The source doesn't have to be immutable, but shouldn't be altered while a
 * Substring is used on it.
 */
public final class Substring extends AbstractSourceWrapper {
  /** The start index, inclusive. */
  private final int begin;
  /** The end index, exclusive. */
  private final int end;

  private Substring(final CharSequence source, final int begin, final int end) {
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

  public static StringWrapper of(final CharSequence source, final int begin, final int end) {
    requireNonNull(source, "source");
    if (begin == 0 && end == source.length())
      return NullWrapper.of(source);
    if (source instanceof Substring) {
      final Substring substr = (Substring) source;
      new Substring(substr.getSource(), substr.getBegin() + begin, substr.getBegin() + end);
    }
    return new Substring(source, begin, end);
  }

  public static StringWrapper of(final CharSequence source, final int begin) {
    requireNonNull(source, "source");
    return of(source, begin, source.length());
  }

  public static StringWrapper ofLength(final CharSequence source, final int begin, final int length) {
    requireNonNull(source, "source");
    if (begin == 0 && length == source.length())
      return NullWrapper.of(source);
    if (source instanceof Substring) {
      final Substring substr = (Substring) source;
      new Substring(substr.getSource(), substr.getBegin() + begin, substr.getBegin() + length);
    }
    return new Substring(source, begin, begin + length);
  }

  /** Create string wrapper that is no longer than the given maximum. */
  public static StringWrapper ofMaxLength(final CharSequence source, final int max) {
    requireNonNull(source, "source");
    if (max < 0)
      throw new IllegalArgumentException(Integer.toString(max));
    return ofMaxLength(source, max, EmptyWrapper.INSTANCE);
  }

  /**
   * Create string wrapper that is no longer than the given maximum. If the
   * <i>source</i> is longer than <i>max</i> then the <i>appendix</i> is
   * concatenated to the source.
   */
  public static StringWrapper ofMaxLength(final CharSequence source, final int max, final CharSequence appendix) {
    requireNonNull(source, "source");
    requireNonNull(appendix, "appendix");
    if (max < 0)
      throw new IllegalArgumentException(Integer.toString(max));
    if (appendix.length() >= max)
      throw new IllegalArgumentException("appendix is longer than " + max);
    if (source.length() > max)
      return Concat.of(ofLength(source, 0, max - appendix.length()), appendix);
    return NullWrapper.of(source);
  }

  @Override
  public int length() {
    return this.getEnd() - this.getBegin();
  }

  @Override
  public char charAt(final int index) {
    if (index < 0 || index >= this.length())
      throw new StringIndexOutOfBoundsException(index);
    return this.getSource().charAt(this.getBegin() + index);
  }

  @SuppressWarnings("hiding")
  @Override
  public CharSequence subSequence(final int begin, final int end) {
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

  @Override
  protected boolean canContain(final char chr) {
    if (this.getSource() instanceof AbstractStringWrapper)
      return ((AbstractStringWrapper) this.getSource()).canContain(chr);
    return true;
  }
}
