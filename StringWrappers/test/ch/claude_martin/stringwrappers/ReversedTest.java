package ch.claude_martin.stringwrappers;

import static org.junit.Assert.*;

import org.junit.Test;

public class ReversedTest extends AbstractStringWrapperTest {

  public ReversedTest(final String input) {
    super(input);
  }

  private static String reversed(final String s) {
    return new StringBuffer(s).reverse().toString();
  }

  @Test
  public final void testOf() {
    final String str = this.input;

    final StringWrapper rev = Reversed.of(str);
    final String rts = reversed(str);

    try {
      this.assertEqualStrings(rts, rev);
    } catch (final AssertionError e) {
      for (int i = 0; i < str.length(); i++) {
        final char rts_i = rts.charAt(i);
        final char rev_i = rev.charAt(i);
        if (rts_i != rev_i) {
          throw new AssertionError(//
              String.format("char at %d is U+%H instead of U+%H", i, rev_i, rts_i), e);
        }
      }
    }

    if (str.length() < 2) {
      assertSame(rev.reversed(), rev);
    }
    this.assertEqualStrings(str, rev.reversed());

    // test bad surrogate:
    if (rev.length() == 2 && Character.isHighSurrogate(str.charAt(0))) {
      // If str is a surrogate pair then rev1 will only contain one
      // high-surrogate:
      final StringWrapper rev1 = Reversed.of(Concat.of("XYZ", Substring.ofLength(str, 0, 1)));
      assertTrue(Character.isHighSurrogate(rev1.charAt(0)));
    }

    // test bad input:
    try {
      Reversed.of(null);
      fail();
    } catch (final NullPointerException t) {
      // expected!
    }

  }

}
