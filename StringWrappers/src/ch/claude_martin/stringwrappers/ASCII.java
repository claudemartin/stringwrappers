package ch.claude_martin.stringwrappers;

import static java.util.Objects.requireNonNull;

/**
 * This can be used to substitute characters that are not available in the ASCII
 * character-encoding scheme or that are greater than some "highest character".
 * Note that surrogate pairs are replaced by two substitute characters.
 *
 * @see StringWrapper#getBytes(java.nio.charset.Charset)
 */
public final class ASCII extends AbstractSourceWrapper {

  /**
   * Convenience method to convert string to array of bytes.
   *
   * @return ASCII characters.
   */
  public static byte[] toASCII(final CharSequence source, final byte substitute, final boolean extended) {
    final char highChar = extended ? '\u00FF' : '\u007F';
    final int length = source.length();
    final byte[] result = new byte[length];
    for (int i = 0; i < length; i++) {
      final char c = source.charAt(i);
      if (c > highChar)
        result[i] = substitute;
      else
        result[i] = (byte) c;
    }
    return result;
  }

  /**
   * A regular qestion mark: '?'.
   */
  public static final char QUESTION_MARK         = '?';
  /**
   * Control character "SUBSTITUTE". This is not printable, but it is a valid
   * ASCII character.
   *
   * @see #SYMBOL_FOR_SUBSTITUTE
   */
  public static final char CONTROL_SUBSTITUTE    = '\u001A';
  /**
   * Substitute character: '&#x241A;'. This is printable but <b>not</b> part of
   * ASCII!
   *
   * @see http://en.wikipedia.org/wiki/Substitute_character
   */
  public static final char SYMBOL_FOR_SUBSTITUTE = '\u241A';

  /** The last character not to be substituted. */
  private final char       highChar;
  /** The substitute character. */
  private final char       substitute;

  private ASCII(final CharSequence source, final char substitute, final char highChar) {
    super(source);
    this.highChar = highChar;
    this.substitute = substitute;
  }

  /**
   * Characters that are above a given <i>highest character</i> (starting with
   * highChar+1) are replaced by given substitute. Note that the
   * <i>substitute</i> can be greater than <i>highChar</i>.
   */
  public static StringWrapper of(final CharSequence source, final char substitute, final char highChar) {
    requireNonNull(source, "source");
    if (source instanceof ASCII) {
      final ASCII ascii = (ASCII) source;
      if (ascii.highChar == highChar && ascii.substitute == substitute)
        return ascii;
    }
    if (0 == source.length())
      return EmptyWrapper.INSTANCE;
    return new ASCII(source, highChar, substitute);
  }

  /**
   * Cahracters that are not ASCII (starting with \u0080) are replaced by given
   * substitute. Note that the substitute does not need to be ASCII.
   */
  public static StringWrapper of(final CharSequence source, final char substitute) {
    return of(source, '\u007F', substitute);
  }

  /** Characters that are not ASCII (starting with \u0080) are replaced by '?'. */
  public static StringWrapper of(final CharSequence source) {
    return of(source, QUESTION_MARK);
  }

  /**
   * Extended ASCII, aka ISO-8859-1, aka "Latin alphabet no. 1".
   */
  public static StringWrapper ofLatin1(final CharSequence source, final char substitute) {
    return of(source, '\u00FF', substitute);
  }

  /**
   * Extended ASCII, aka ISO-8859-1, aka "Latin alphabet no. 1".
   */
  public static StringWrapper ofLatin1(final CharSequence source) {
    return ofLatin1(source, QUESTION_MARK);
  }

  @Override
  public char charAt(final int index) {
    final char c = this.getSource().charAt(index);
    if (c > this.highChar) {
      return this.substitute;
    }
    return c;
  }

  @Override
  protected boolean canContain(final char chr) {
    return chr <= this.highChar;
  }
}
