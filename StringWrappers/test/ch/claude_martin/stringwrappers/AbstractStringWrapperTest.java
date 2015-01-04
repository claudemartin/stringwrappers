package ch.claude_martin.stringwrappers;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertTrue;
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

  @SuppressWarnings("static-method")
  public void assertEqualStrings(CharSequence expected, CharSequence actual) {
    if (!StringUtils.equals(expected, actual))
      fail("'" + expected + "' != '" + actual + "'");
  }

  public static Collection<Object[]> data = null;

  @Parameters
  public static synchronized Collection<Object[]> data() {
    if (data != null)
      return data;

    final List<Object[]> params = new ArrayList<>();
    StringBuilder all = new StringBuilder();
    for (String s : asList("", "x", "X", " \t \n \f \r \b \" \' \\ ", "0123456789",
        "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "abcdefghijklmnopqrstuvwxyz", "+$!(/\"\\|", "\uD834\uDD1E",
        "\0", "x\0x", " ")) {
      params.add(new Object[] { s });
      all.append(s);
    }

    params.add(new Object[] { all.toString() });

    {
      final StringBuilder sb = new StringBuilder(1 << 19);
      for (int cp = 0; cp < Character.MAX_CODE_POINT; cp++) {
        if (Character.isDefined(cp)
            && (cp < Character.MIN_SURROGATE || cp > Character.MAX_SURROGATE))
          sb.appendCodePoint(cp);
      }
      params.add(new Object[] { sb.toString() });
    }

    data = Collections.unmodifiableCollection(params);
    return data;
  }

  protected final String input;

  public AbstractStringWrapperTest(String input) {
    this.input = input;
  }

}
