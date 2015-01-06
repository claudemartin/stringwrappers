package ch.claude_martin.stringwrappers;

public final class LowerCase extends CharWrapper {

  private LowerCase(final CharSequence source) {
    super(source, CharMapper.TO_LOWER_CASE);
  }

  public static StringWrapper of(final CharSequence source) {
    if (source instanceof LowerCase)
      return (LowerCase) source;
    if (0 == source.length())
      return EmptyWrapper.INSTANCE;
    return new LowerCase(source);
  }
}
