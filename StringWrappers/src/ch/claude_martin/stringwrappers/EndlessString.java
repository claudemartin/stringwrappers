package ch.claude_martin.stringwrappers;

import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

/**
 * Pseudo-endless String. The length is fixed to <code>Integer.MAX_VALUE</code>.
 * However, the Iterator is actually endless.
 */
public class EndlessString extends AbstractStringWrapper {

  private final CharAt generator;

  public EndlessString(final CharAt generator) {
    super();
    this.generator = generator;
  }

  @Override
  public int length() {
    return Integer.MAX_VALUE;
  }

  @Override
  public char charAt(final int index) {
    return this.generator.get(index);
  }

  @Override
  public CharIterator iterator() {
    return new CharIterator() {
      int pos = 0;

      @Override
      public boolean hasNext() {
        return true;
      }

      @Override
      public char nextChar() {
        return EndlessString.this.generator.get(this.pos++);
      }

    };
  }

  PrimitiveIterator.OfInt intIterator() {
    return new PrimitiveIterator.OfInt() {
      int pos = 0;

      @Override
      public boolean hasNext() {
        return true;
      }

      @Override
      public int nextInt() {
        return EndlessString.this.generator.get(this.pos++);
      }

    };
  }

  @Override
  public IntStream chars() {
    return StreamSupport.intStream(
        () -> Spliterators.spliterator(this.intIterator(), Integer.MAX_VALUE, Spliterator.ORDERED),
        Spliterator.ORDERED, false);
  }
}
