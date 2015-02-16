package ch.claude_martin.stringwrappers;

import static java.util.Objects.requireNonNull;

/**
 * Wraps this character sequence as the reverse of the sequence. If there are
 * any surrogate pairs included in the sequence, these are treated as single
 * characters for the reverse operation. Thus, the order of the high-low
 * surrogates is never reversed.
 *
 * @see StringBuilder#reverse()
 */
public final class Reversed extends AbstractSourceWrapper {

  Reversed(final CharSequence source) {
    super(source);
  }

  public static StringWrapper of(final CharSequence source) {
    requireNonNull(source, "source");
    if (source.length() < 2)
      return NullWrapper.of(source);
    if (source instanceof Reversed) {
      return NullWrapper.of(((Reversed) source).getSource());
    }
    return new Reversed(source);
  }

  @Override
  public char charAt(final int index) {
    final CharSequence source = this.getSource();

    final int l_i = source.length() - index;
    final char ch = source.charAt(l_i - 1);

    if (Character.isHighSurrogate(ch) && l_i < source.length()) {
      return source.charAt(l_i);
    } else if (Character.isLowSurrogate(ch) && l_i - 2 >= 0) {
      return source.charAt(l_i - 2);
    }

    return ch;
  }

  @Override
  public String toString() {
    return new StringBuilder(this.getSource()).reverse().toString();
  }

  @Override
  protected boolean canContain(final char chr) {
    final CharSequence src = this.getSource();
    if (src instanceof AbstractStringWrapper)
      return ((AbstractStringWrapper) src).canContain(chr);
    return true;
  }

  @Override
  public int indexOf(final char chr) {
    if (this.getSource() instanceof AbstractStringWrapper)
      return ((AbstractStringWrapper) this.getSource()).lastIndexOf(chr);
    return super.indexOf(chr);
  }

  @Override
  public int indexOf(final char chr, final int fromIndex) {
    if (this.getSource() instanceof AbstractStringWrapper)
      return ((AbstractStringWrapper) this.getSource()).lastIndexOf(chr, fromIndex);
    return super.indexOf(chr, fromIndex);
  }

  @Override
  public int indexOf(final int codePoint) {
    if (this.getSource() instanceof AbstractStringWrapper)
      return ((AbstractStringWrapper) this.getSource()).lastIndexOf(codePoint);
    return super.indexOf(codePoint);
  }

  @Override
  public int indexOf(final int codePoint, final int fromIndex) {
    if (this.getSource() instanceof AbstractStringWrapper)
      return ((AbstractStringWrapper) this.getSource()).lastIndexOf(codePoint, fromIndex);
    return super.indexOf(codePoint, fromIndex);
  }

  @Override
  public int lastIndexOf(final char chr) {
    if (this.getSource() instanceof AbstractStringWrapper)
      return ((AbstractStringWrapper) this.getSource()).indexOf(chr);
    return super.lastIndexOf(chr);
  }

  @Override
  public int lastIndexOf(final char chr, final int fromIndex) {
    if (this.getSource() instanceof AbstractStringWrapper)
      return ((AbstractStringWrapper) this.getSource()).indexOf(chr, fromIndex);
    return super.lastIndexOf(chr, fromIndex);
  }

  @Override
  public int lastIndexOf(final int codePoint) {
    if (this.getSource() instanceof AbstractStringWrapper)
      return ((AbstractStringWrapper) this.getSource()).indexOf(codePoint);
    return super.lastIndexOf(codePoint);
  }

  @Override
  public int lastIndexOf(final int codePoint, final int fromIndex) {
    if (this.getSource() instanceof AbstractStringWrapper)
      return ((AbstractStringWrapper) this.getSource()).indexOf(codePoint, fromIndex);
    return super.lastIndexOf(codePoint, fromIndex);
  }

}
