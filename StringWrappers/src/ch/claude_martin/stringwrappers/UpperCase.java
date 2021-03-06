package ch.claude_martin.stringwrappers;

public final class UpperCase extends CharWrapper {
  private UpperCase(final CharSequence source) {
    super(source, CharMapper.TO_UPPER_CASE);
  }

  public static StringWrapper of(final CharSequence source) {
    if (source instanceof UpperCase)
      return (UpperCase) source;
    if (0 == source.length())
      return EmptyWrapper.INSTANCE;
    return new UpperCase(source);
  }

  @Override
  protected boolean canContain(final char chr) {
    return Character.isUpperCase(chr);
  }
}
