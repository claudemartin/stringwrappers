package ch.claude_martin.stringwrappers;

import java.util.function.Function;

/**
 * Maps one character to some other character.
 * */
@FunctionalInterface
public interface CharMapper extends Function<Character, Character> {
  /** Map character to character. */
  public char map(final char c);

  @Override
  public default Character apply(final Character t) {
    return this.map(t);
  }

  public static final CharMapper TO_UPPER_CASE = new CharMapper() {
    @Override
    public char map(final char c) {
      return Character.toUpperCase(c);
    }
  };
  public static final CharMapper TO_LOWER_CASE = new CharMapper() {
    @Override
    public char map(final char c) {
      return Character.toLowerCase(c);
    }
  };
}
