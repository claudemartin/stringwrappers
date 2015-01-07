package ch.claude_martin.stringwrappers;

import static java.util.Objects.requireNonNull;

/**
 * This wrapper doesn't modify the CharSequence at all. If the input is already
 * a StringWrapper then it will return that object.
 */
public final class NullWrapper extends AbstractSourceWrapper {

  private NullWrapper(final CharSequence source) {
    super(source);
  }

  /**
   * This wrapper doesn't modify the CharSequence at all. If the input is
   * already a StringWrapper then it will return that object.
   */
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
