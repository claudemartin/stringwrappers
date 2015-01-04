package ch.claude_martin.stringwrappers;

import java.util.Locale;

public final class LowerCase extends CharWrapper {

  private LowerCase(CharSequence source) {
    super(source, CharMapper.TO_LOWER_CASE);
  }

  public static StringWrapper of(CharSequence source) {
    if (source instanceof LowerCase)
      return (LowerCase) source;
    return new LowerCase(source);
  }
}
