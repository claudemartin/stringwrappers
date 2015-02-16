package ch.claude_martin.stringwrappers;

import static java.util.Objects.requireNonNull;

/** Wraps an array of characters. */
public class ByteArrayWrapper extends AbstractStringWrapper {
  protected final byte[] bytes;

  public ByteArrayWrapper(final byte[] bytes) {
    super();
    this.bytes = bytes;
  }

  @Override
  public char charAt(final int index) {
    return (char) (this.bytes[index] & 0xFF);
  }

  @Override
  public int length() {
    return this.bytes.length;
  }

  /**
   * Wraps a given array of characters.
   */
  public static StringWrapper of(final byte[] bytes) {
    requireNonNull(bytes, "bytes");
    if (bytes.length == 0)
      return StringUtils.empty();
    return new ByteArrayWrapper(bytes);
  }

  /**
   * Wraps a given array of characters.
   */
  public static StringWrapper of(final byte[] bytes, final int offset, final int length) {
    requireNonNull(bytes, "bytes");
    if (offset < 0 || length > bytes.length)
      throw new IllegalArgumentException();
    if (length == 0)
      return StringUtils.empty();
    return new ByteArrayWrapper2(bytes, offset, length);
  }

  @Override
  protected boolean canContain(final char chr) {
    return chr <= 255;
  }

  static class ByteArrayWrapper2 extends ByteArrayWrapper {
    private final int offset, length, end;

    public ByteArrayWrapper2(final byte[] bytes, final int offset, final int length) {
      super(bytes);
      if (offset < 0)
        throw new IllegalArgumentException(Integer.toString(offset));
      this.offset = offset;
      this.length = length;
      this.end = offset + length;
      if (bytes.length < this.end)
        throw new IllegalArgumentException();
    }

    public int getLength() {
      return this.length;
    }

    @Override
    public char charAt(final int index) {
      if (index < 0 || index >= this.end)
        throw new StringIndexOutOfBoundsException(index);
      return (char) (this.bytes[this.offset + index] & 0xFF);
    }
  }
}
