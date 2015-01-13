package ch.claude_martin.stringwrappers;

import java.util.Objects;
import java.util.PrimitiveIterator;

public interface CharIterator extends PrimitiveIterator<Character, CharConsumer> {

  abstract char nextChar();

  default int nextInt() {
    return this.nextChar();
  }

  @Override
  default Character next() {
    return this.nextChar();
  }

  @Override
  default void remove() {
    throw new UnsupportedOperationException();
  }

  @Override
  default void forEachRemaining(final CharConsumer action) {
    Objects.requireNonNull(action);
    while (this.hasNext())
      action.acceptChar(this.nextChar());
  }
}
