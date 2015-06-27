package ch.claude_martin.stringwrappers;

import static java.util.Objects.requireNonNull;

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
  /**
   * Creates an endless string by repeating a given string. Note that an empty
   * string causes an {@link IllegalArgumentException}.
   *
   * @param s
   *          a nonempty string
   * @return endless repetition of given string.
   */
  public static StringWrapper repeat(final CharSequence s) {
    requireNonNull(s, "s");
    if (s.length() == 0)
      throw new IllegalArgumentException("String must not be empty.");
    if (s.length() == 1) {
      final char first = s.charAt(0);
      return new EndlessString(i -> first);
    }
    if (s.length() == 2) {
      final char first = s.charAt(0);
      final char second = s.charAt(1);
      return new EndlessString(i -> i % 2 == 0 ? first : second);
    }

    final int length = s.length();
    return new EndlessString(i -> {
      if (i < 0) {
        i += Integer.MAX_VALUE + 2;
        i += Integer.MAX_VALUE % length;
      }
      return s.charAt(i % length);
    });
  }

  /**
   * Creates an endless string by the use of a given generator. Note that the
   * index of a character can be negative.
   *
   * @param generator
   *          creates characters for all values of an integer.
   * @return endless string.
   */
  public static StringWrapper of(final CharAt generator) {
    requireNonNull(generator, "generator");
    return new EndlessString(generator);
  }

  final CharAt generator;

  private EndlessString(final CharAt generator) {
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
