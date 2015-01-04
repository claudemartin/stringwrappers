package ch.claude_martin.stringwrappers;

import java.util.Locale;

/** Maps each character to a some character. */
public class CharWrapper extends AbstractSourceWrapper {

  private final CharMapper mapper;

  CharWrapper(CharSequence source, CharMapper mapper) {
    super(source);
    this.mapper = mapper;
  }

  public static StringWrapper of(CharSequence source, CharMapper mapper) {
    return new CharWrapper(source, mapper);
  }

  final CharMapper getMapper() {
    return this.mapper;
  }

  @Override
  public final char charAt(int index) {
    return this.mapper.map(this.getSource().charAt(index));
  }

  @Override
  public final CharSequence subSequence(int begin, int end) {
    return of(Substring.of(getSource(), begin, end), this.mapper);
  }

}
