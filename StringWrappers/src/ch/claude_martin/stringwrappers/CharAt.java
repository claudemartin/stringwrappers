package ch.claude_martin.stringwrappers;

import java.util.function.IntFunction;

/** Interface of {@link CharSequence#charAt(int)}. */
@FunctionalInterface
public interface CharAt extends IntFunction<Character> {
  char get(int i);

  @Override
  default Character apply(final int i) {
    return this.get(i);
  }

  public default CharSequence toCharSequence(final int length) {
    return StringUtils.wrap(length, this);
  }
}
