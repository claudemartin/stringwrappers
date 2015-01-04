package ch.claude_martin.stringwrappers;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import org.junit.Test;

public class SubstringTest extends AbstractStringWrapperTest {

  public SubstringTest(String input) {
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
          CharSequence str_i_j = Substring.of(str, i, j);
          this.assertEqualStrings(str.subSequence(i, j), str_i_j);
        }
      else
        i += 1012;
    }

    // test bad input:

    try {
      Substring.of(str, -1);
      fail();
    } catch (StringIndexOutOfBoundsException t) {
      // expected!
    }

    try {
      Substring.of(str, 0, str.length() + 1);
      fail();
    } catch (StringIndexOutOfBoundsException t) {
      // expected!
    }

    try {
      Substring.of(str, 1, 0);
      fail();
    } catch (StringIndexOutOfBoundsException t) {
      // expected!
    }

    try {
      Substring.of(null, 1, 0);
      fail();
    } catch (NullPointerException t) {
      // expected!
    }

  }

  @Test
  public void testTrim() throws Exception {
    // Trim uses Substring!

    StringWrapper wrapped = NullWrapper.of(this.input);

    StringWrapper trimmed1 = NullWrapper.of(this.input.trim());
    StringWrapper trimmed2 = wrapped.trim();

    assertEqualStrings(trimmed1, trimmed2);

    StringWrapper trimmed3 = wrapped.trim(' ', '\t', '\n', '\r', '\f');
    StringWrapper trimmed4 = wrapped.trim(asList(' ', '\t', '\n', '\r', '\f'));

    assertEqualStrings(trimmed3, trimmed4);

  }

  @Test
  public final void testOfLength() {
    String str = this.input;
    int l = str.length();
    int m = l / 2;

    for (int i = 0; i <= str.length(); i++) {
      this.assertEqualStrings(Substring.of(str, 0, i), Substring.ofLength(str, 0, i));
      this.assertEqualStrings("", Substring.ofLength(str, i, 0));
      this.assertEqualStrings(str.substring(0, m), Substring.ofLength(str, 0, m));
      this.assertEqualStrings(str.substring(m, l), Substring.ofLength(str, m, l - m));

      if (l > 1024)
        i += 1012;
    }

  }

}
