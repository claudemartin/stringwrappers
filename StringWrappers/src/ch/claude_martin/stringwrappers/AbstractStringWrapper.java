package ch.claude_martin.stringwrappers;

import static java.util.Objects.requireNonNull;

/**
 * Base class for all StringSwappers. Note that most methods are implemented in
 * the interface (default methods). However, this contains an implementation of
 * {@link #toString()} and makes use of a {@link #canContain(char)} method.
 */
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
    if (!this.canContain(chr))
      return -1;
    return StringWrapper.super.indexOf(chr, fromIndex);
  }

  @Override
  public int indexOf(final int codePoint, final int fromIndex) {
    // TODO use concontain if code point needs only one "char"!

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
  public int lastIndexOf(final char chr, final int fromIndex) {
    if (!this.canContain(chr))
      return -1;
    return StringWrapper.super.lastIndexOf(chr, fromIndex);
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
  public boolean startsWith(final CharSequence prefix) {
    if (this.length() == prefix.length())
      return this.contentEquals(prefix);
    return this.startsWith(prefix, 0);
  }

  @Override
  public boolean startsWith(final CharSequence prefix, final int toffset) {
    return StringWrapper.super.startsWith(prefix, toffset);
  }

  @Override
  public boolean endsWith(final CharSequence suffix) {
    requireNonNull(suffix, "suffix");
    int lenThis = this.length();
    int lenThat = suffix.length();
    if (0 == lenThat)
      return true;
    if (lenThis < lenThat)
      return false;
    if (1 == lenThat)
      return this.charAt(lenThis - 1) == suffix.charAt(0);
    if (lenThis == lenThat)
      return this.contentEquals(suffix);

    while (lenThat > 0) {
      if (this.charAt(--lenThis) != suffix.charAt(--lenThat))
        return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return new StringBuilder(this).toString();
  }

  @Override
  public final int hashCode() {
    return super.hashCode();
  }

  @Override
  public final boolean equals(final Object obj) {
    return super.equals(obj);
  }

}
