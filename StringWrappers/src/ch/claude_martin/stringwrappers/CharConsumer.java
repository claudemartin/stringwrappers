package ch.claude_martin.stringwrappers;

import java.util.function.Consumer;

@FunctionalInterface
public interface CharConsumer extends Consumer<Character> {
  /** Performs this operation on the given character. */
  public char acceptChar(final char c);

  @Override
  default void accept(final Character c) {
    this.acceptChar(c);
  }

  public static final CharConsumer TO_UPPER_CASE = new CharConsumer() {
    @Override
    public char acceptChar(final char c) {
      return Character.toUpperCase(c);
    }
  };
  public static final CharConsumer TO_LOWER_CASE = new CharConsumer() {
    @Override
    public char acceptChar(final char c) {
      return Character.toLowerCase(c);
    }
  };
}
