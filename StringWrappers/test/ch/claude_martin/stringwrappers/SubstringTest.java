package ch.claude_martin.stringwrappers;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

import org.junit.Test;

public class SubstringTest extends AbstractStringWrapperTest {

  public SubstringTest(final String input) {
    super(input);
  }

  @Test
  public final void testOf() {
    final String str = this.input;
    final int m = str.length() / 2;

    final CharSequence str_0_m = Substring.of(str, 0, m);
    this.assertEqualStrings(str.substring(0, m), str_0_m);

    final CharSequence wrapped = NullWrapper.of(str);
    final CharSequence str_0_l = Substring.of(wrapped, 0, str.length());
    assertSame(wrapped, str_0_l);

    for (int i = 0; i <= m; i++) {
      final CharSequence str_0_i = Substring.of(str, 0, i);
      this.assertEqualStrings(str.subSequence(0, i), str_0_i);

      final CharSequence str_i_m = Substring.of(str, i, m);
      this.assertEqualStrings(str.subSequence(i, m), str_i_m);

      final CharSequence str_i = Substring.of(str, i);
      this.assertEqualStrings(str.substring(i), str_i);

      if (m < 1024)
        for (int j = i; j <= m; j++) {
          final CharSequence str_i_j = Substring.of(str, i, j);
          this.assertEqualStrings(str.subSequence(i, j), str_i_j);
        }
      else
        i += 1012;
    }

    // test bad input:

    try {
      Substring.of(str, -1);
      fail();
    } catch (final StringIndexOutOfBoundsException t) {
      // expected!
    }

    try {
      Substring.of(str, 0, str.length() + 1);
      fail();
    } catch (final StringIndexOutOfBoundsException t) {
      // expected!
    }

    try {
      Substring.of(str, 1, 0);
      fail();
    } catch (final StringIndexOutOfBoundsException t) {
      // expected!
    }

    try {
      Substring.of(null, 1, 0);
      fail();
    } catch (final NullPointerException t) {
      // expected!
    }

  }

  @Test
  public void testOfMaxLength() throws Exception {
    final StringWrapper max10 = Substring.ofMaxLength(this.input, 10);
    assertTrue(max10.length() <= 10);

    final StringWrapper max10_ = Substring.ofMaxLength(this.input, 10, "...");
    assertTrue(max10_.length() <= 10);
    if (this.input.length() > 10)
      assertTrue(max10_.endsWith("..."));

  }

  @Test
  public void testTrim() throws Exception {
    // Trim uses Substring!

    final StringWrapper wrapped = NullWrapper.of(this.input);

    final StringWrapper trimmed1 = NullWrapper.of(this.input.trim());
    final StringWrapper trimmed2 = wrapped.trim();

    this.assertEqualStrings(trimmed1, trimmed2);

    final StringWrapper trimmed3 = wrapped.trim(' ', '\t', '\n', '\r', '\f');
    final StringWrapper trimmed4 = wrapped.trim(asList(' ', '\t', '\n', '\r', '\f'));

    this.assertEqualStrings(trimmed3, trimmed4);

  }

  @Test
  public final void testOfLength() {
    final String str = this.input;
    final int l = str.length();
    final int m = l / 2;
    this.assertEqualStrings(str.substring(0, m), Substring.ofLength(str, 0, m));
    this.assertEqualStrings(str.substring(m, l), Substring.ofLength(str, m, l - m));

    for (int i = 0; i <= str.length(); i++) {
      this.assertEqualStrings(Substring.of(str, 0, i), Substring.ofLength(str, 0, i));
      this.assertEqualStrings("", Substring.ofLength(str, i, 0));

      if (l > 1024)
        i += 1012;
    }

  }

}
