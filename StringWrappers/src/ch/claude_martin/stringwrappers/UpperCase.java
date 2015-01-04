package ch.claude_martin.stringwrappers;

import java.util.Locale;

public final class UpperCase extends CharWrapper {
  private UpperCase(CharSequence source) {
    super(source, CharMapper.TO_UPPER_CASE);
  }

  public static StringWrapper of(CharSequence source) {
    if (source instanceof UpperCase)
      return (UpperCase) source;
    return new UpperCase(source);
  }
}
