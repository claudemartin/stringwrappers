package ch.claude_martin.stringwrappers;

/** Interface of {@link CharSequence#charAt(int)}. */
// Java 8: @FunctionalInterface
public interface CharAt {
  char get(int i);

  /* @formatter:off
   * Java 8:
  public default CharSequence toCharSequence(final int length) {
    return StringUtils.of(this, length);
  }
   */
}
