package ch.claude_martin.stringwrappers;

/**
 * Maps one character to some other character.
 * 
 * In java 8 this is a "Functional Interface".
 * */
public interface CharMapper {
  /** Map character to character. */
  public char map(char c);

  public static final CharMapper TO_UPPER_CASE = new CharMapper() {
    @Override
    public char map(char c) {
      return Character.toUpperCase(c);
    }
  };
  public static final CharMapper TO_LOWER_CASE = new CharMapper() {
    @Override
    public char map(char c) {
      return Character.toLowerCase(c);
    }
  };
}
