package ch.claude_martin.stringwrappers;

import static java.util.Objects.requireNonNull;

/**
 * Wraps this character sequence as the reverse of the sequence. If there are any surrogate pairs
 * included in the sequence, these are treated as single characters for the reverse operation. Thus,
 * the order of the high-low surrogates is never reversed.
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
}
