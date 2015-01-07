package ch.claude_martin.stringwrappers;

import static java.util.Objects.requireNonNull;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public final class StringUtils {
  public StringUtils() {
    throw new RuntimeException("No instace for you!");
  }

  public static StringWrapper empty() {
    return EmptyWrapper.INSTANCE;
  }

  /** Creates a StringWrapper by a given charAt-method and a length. */
  public static StringWrapper wrap(final int length, final CharAt charAt) {
    return new AbstractStringWrapper() {
      @Override
      public int length() {
        return length;
      }

      @Override
      public char charAt(final int index) {
        return charAt.get(index);
      }
    };
  }

  /** @see NullWrapper#of(CharSequence) */
  public static StringWrapper wrap(final CharSequence s) {
    return NullWrapper.of(s);
  }

  /** @see NullWrapper#of(CharSequence) */
  public static StringWrapper wrap(final List<Character> c) {
    return new AbstractStringWrapper() {
      @Override
      public int length() {
        return c.size();
      }

      @Override
      public char charAt(final int index) {
        return c.get(index);
      }
    };
  }

  /** @see NullWrapper#of(CharSequence) */
  public static StringWrapper wrap(final char[] c) {
    return new CharArrayWrapper(c);
  }

  /** Quick check if both are the same strings. */
  static boolean same(final CharSequence a, final CharSequence b) {
    if (a == b)
      return true;

    if (a.length() != b.length())
      return false;

    if (a.getClass() != b.getClass())
      return false;

    if (a instanceof AbstractSourceWrapper) {
      if (!same(((AbstractSourceWrapper) a).getSource(), ((AbstractSourceWrapper) b).getSource()))
        return false;
    }

    if (a instanceof Substring) {
      final Substring _a = (Substring) a;
      final Substring _b = (Substring) b;
      if (_a.getBegin() == _b.getBegin() && _a.getEnd() == _b.getEnd())
        return true;
    }

    if (a instanceof CharWrapper) {
      if (((CharWrapper) a).getMapper() == ((CharWrapper) b).getMapper())
        return true;
    }

    if (a instanceof Reversed) {
      return true;
    }

    if (a instanceof NullWrapper) {
      return true;
    }

    return false;
  }

  /**
   * Checks if two character sequences represent the same text, ignoring the
   * case. This compares characters (UTF-16 code units). You may want to use
   * {@link java.text.Normalizer} to compare normalized strings.
   *
   * @see java.util.Objects#equals(Object, Object)
   * @see String#equalsIgnoreCase(Object)
   */
  public static boolean equalsIgnoreCase(final CharSequence a, final CharSequence b) {
    // both null:
    if (a == null && b == null)
      return true;

    // exactly one argument is null:
    if (a == null || b == null)
      return false;

    // both the same:
    if (same(a, b))
      return true;

    int len = a.length();
    if (len != b.length())
      return false;
    int i = 0;
    while (len-- > 0) {
      final char c1 = a.charAt(i);
      final char c2 = b.charAt(i);
      i++;

      if (c1 == c2) {
        continue;
      }
      final char u1 = Character.toUpperCase(c1);
      final char u2 = Character.toUpperCase(c2);
      if (u1 == u2) {
        continue;
      }
      if (Character.toLowerCase(u1) == Character.toLowerCase(u2)) {
        continue;
      }
      return false;
    }
    return true;
  }

  /**
   * Checks if two character sequences represent the same text. This compares
   * characters (UTF-16 code units). You may want to use
   * {@link java.text.Normalizer} to compare normalized strings.
   *
   * @see java.util.Objects#equals(Object, Object)
   * @see String#equals(Object)
   */
  public static boolean equals(final CharSequence a, final CharSequence b) {
    // both null:
    if (a == null && b == null)
      return true;

    // exactly one argument is null:
    if (a == null || b == null)
      return false;

    // both the same:
    if (same(a, b))
      return true;

    int n = a.length();
    if (n == b.length()) {
      int i = 0;
      while (n-- != 0) {
        if (a.charAt(i) != b.charAt(i))
          return false;
        i++;
      }
      return true;
    }

    return false;
  }

  /**
   * Calculates a hash code for a character sequence.
   *
   * @see String#hashCode()
   * @param s
   *          The sequence
   * @return a hash code
   */
  public static int hashCode(final CharSequence s) {
    final int length = s.length();
    int h = 0;
    for (int i = 0; i < length; i++) {
      h = 31 * h + s.charAt(i);
    }
    return h;
  }

  public static byte[] getBytes(final CharSequence s, final Charset charset) {
    requireNonNull(s, "s");
    requireNonNull(charset, "charset");
    if (s.length() == 0)
      return new byte[0];
    final CharsetEncoder ce = charset.newEncoder();
    final int maxLen = (int) (s.length() * (double) ce.maxBytesPerChar());
    final byte[] ba = new byte[maxLen];

    ce.onMalformedInput(CodingErrorAction.REPLACE)//
    .onUnmappableCharacter(CodingErrorAction.REPLACE)//
    .reset();
    try {
      final ByteBuffer buffer = ce.encode(CharBuffer.wrap(s));
      if (buffer.position() == ba.length)
        return ba;
      return Arrays.copyOf(ba, buffer.position());
    } catch (final CharacterCodingException x) {
      throw new Error(x);
    }
  }

  public Iterator<Character> iterator(final CharSequence s) {
    return StringIterator.of(s);
  }
}
