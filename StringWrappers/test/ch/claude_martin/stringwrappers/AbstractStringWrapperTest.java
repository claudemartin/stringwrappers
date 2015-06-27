package ch.claude_martin.stringwrappers;

import static java.util.Arrays.asList;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public abstract class AbstractStringWrapperTest {

  private final CharMapper printable = new CharMapper() {
    @Override
    public char map(final char c) {
      if (c < ' ' || c > '~')
        return '?';
      return c;
    }
  };

  CharSequence pretty(final CharSequence s) {
    return CharWrapper.of(Substring.ofMaxLength(s, 20, "..."), this.printable);
  }

  public void assertEqualStrings(final CharSequence expected, final CharSequence actual) {
    if (StringUtils.same(expected, actual))
      return;
    for (int i = 0; i < expected.length(); i++) {
      final char exp = expected.charAt(i);
      final char act = actual.charAt(i);
      if (exp != act) {
        fail(String.format("'%s' != '%s' => char at %d should be <%s>, but was <%s>", //
            this.pretty(expected), this.pretty(actual), i, String.valueOf(exp), String.valueOf(act)));
      }
    }
  }

  private static Collection<Object[]> data = null;

  @Parameters
  public static synchronized Collection<Object[]> data() {
    if (data != null)
      return data;

    final List<Object[]> params = new ArrayList<>();
    final StringBuilder all = new StringBuilder();
    for (final String s : asList("", "x", "X", " \t \n \f \r \b \" \' \\ ", "0123456789", "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
        "abcdefghijklmnopqrstuvwxyz", "+$!(/\"\\|", "\uD834\uDD1E", "\0", "x\0x", " ")) {
      params.add(new Object[] { s });
      all.append(s);
    }

    params.add(new Object[] { all.toString() });

    {
      final StringBuilder sb = new StringBuilder(1 << 19);
      for (int cp = 0; cp < Character.MAX_CODE_POINT; cp++) {
        if (Character.isDefined(cp) && (cp < Character.MIN_SURROGATE || cp > Character.MAX_SURROGATE))
          sb.appendCodePoint(cp);
      }
      params.add(new Object[] { sb.toString() });
    }

    data = Collections.unmodifiableCollection(params);
    return data;
  }

  protected final String input;

  public AbstractStringWrapperTest(final String input) {
    this.input = input;
  }

}
