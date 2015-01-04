package ch.claude_martin.stringwrappers;

import static java.util.Objects.requireNonNull;

import java.nio.CharBuffer;

/**
 * This wrapper doesn't modify the CharSequence at all.
 */
public final class NullWrapper extends AbstractSourceWrapper {

  private NullWrapper(final CharSequence source) {
    super(source);
  }

  /**
   * Wraps a given array of characters in a {@link CharBuffer} and then wraps it in a
   * {@link NullWrapper}.
   */
  public static StringWrapper of(final char... cs) {
    requireNonNull(cs, "cs");
    if (cs.length == 0)
      return StringUtils.empty();
    return of(CharBuffer.wrap(cs));
  }

  public static StringWrapper of(final CharSequence source) {
    requireNonNull(source, "source");
    if (source instanceof StringWrapper)
      return (StringWrapper) source;
    return new NullWrapper(source);
  }

  @Override
  public char charAt(final int index) {
    return this.getSource().charAt(index);
  }

  @Override
  public String toString() {
    return this.getSource().toString();
  }
}
