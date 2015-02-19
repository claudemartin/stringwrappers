package ch.claude_martin.stringwrappers;

import static java.util.Objects.requireNonNull;

import java.util.Random;

/**
 * Wrapper for l33t p34k. Note that this can not replace one letter with
 * multiple characters. So "M" can not be replaced by "/\/\". The generated text
 * is compatible with any system that supports Windows Codepage 1252 (Western
 * European).
 */
public class L337Wrapper {
  public static StringWrapper of(final CharSequence s) {
    requireNonNull(s, "s");
    if (s.length() == 0)
      return EmptyWrapper.INSTANCE;
    final Random rng = new Random(s.hashCode());
    return new CharWrapper(s, c -> {
      for (final Symbols sym : Symbols.values())
        if (sym.chars[0] == c || sym.chars[1] == c)
          return sym.get(rng);
      return c;
    });
  }

  private static enum Symbols {
    // These characters should be available on any system and most fonts support
    // them. Windows Codepage 1252 contains them all.
    E('3', '€', 'É'), T('7', '+', '†'), O('0', 'ø'), I('|', '!', 'ï', '¡'), G('9'), A('4', '@', 'ä', 'å'), //
    C('(', 'ç', '©', '¢'), S('$', '§'), L('£'), U('û', 'µ'), Y('ý', '¥'), N('ñ'), X('×'), D('Ð'), //
    Z('ž', '2'), F('ƒ'), B('ß', '8'), R('®');
    final char[] chars;

    private Symbols(final char... chars) {
      this.chars = new char[2 + chars.length];
      this.chars[0] = this.name().charAt(0);
      this.chars[1] = Character.toLowerCase(this.chars[0]);
      for (int x = 0; x < chars.length; x++) {
        this.chars[2 + x] = chars[x];
      }
    }

    public char get(final Random rng) {
      return this.chars[rng.nextInt(this.chars.length)];
    }
  }

}
