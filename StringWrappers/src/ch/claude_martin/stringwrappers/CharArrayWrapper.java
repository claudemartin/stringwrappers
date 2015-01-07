package ch.claude_martin.stringwrappers;

import static java.util.Objects.requireNonNull;

/** Wraps an array of characters. */
public class CharArrayWrapper extends AbstractStringWrapper {
  protected final char[] chars;

  public CharArrayWrapper(final char[] chars) {
    super();
    this.chars = chars;
  }

  @Override
  public char charAt(final int index) {
    return this.chars[index];
  }

  @Override
  public int length() {
    return this.chars.length;
  }

  /**
   * Wraps a given array of characters.
   */
  public static StringWrapper of(final char[] cs) {
    requireNonNull(cs, "cs");
    if (cs.length == 0)
      return StringUtils.empty();
    return new CharArrayWrapper(cs);
  }

  /**
   * Wraps a given array of characters.
   */
  public static StringWrapper of(final char[] cs, final int offset, final int length) {
    requireNonNull(cs, "cs");
    if (offset < 0 || length > cs.length)
      throw new IllegalArgumentException();
    if (length == 0)
      return StringUtils.empty();
    return new CharArrayWrapper2(cs, offset, length);
  }

  static class CharArrayWrapper2 extends CharArrayWrapper {
    private final int offset, length, end;

    public CharArrayWrapper2(final char[] chars, final int offset, final int length) {
      super(chars);
      if (offset < 0)
        throw new IllegalArgumentException(Integer.toString(offset));
      this.offset = offset;
      this.length = length;
      this.end = offset + length;
      if (chars.length < this.end)
        throw new IllegalArgumentException();
    }

    public int getLength() {
      return this.length;
    }

    @Override
    public char charAt(final int index) {
      if (index < 0 || index >= this.end)
        throw new StringIndexOutOfBoundsException(index);
      return this.chars[this.offset + index];
    }
  }
}
