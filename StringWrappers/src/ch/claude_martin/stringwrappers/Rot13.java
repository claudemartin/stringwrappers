package ch.claude_martin.stringwrappers;

import static java.util.Objects.requireNonNull;

public final class Rot13 extends AbstractSourceWrapper {

  private Rot13(final CharSequence source) {
    super(source);
  }

  public static StringWrapper of(final CharSequence source) {
    requireNonNull(source, "source");
    if (source instanceof Rot13)
      return NullWrapper.of(((Rot13) source).getSource());
    if (0 == source.length())
      return EmptyWrapper.INSTANCE;
    return new Rot13(source);
  }

  @Override
  public char charAt(final int index) {
    char c = this.getSource().charAt(index);
    // a=97; m=109; z=122
    // A=65; M=77; Z=90
    if (c >= 'A' && c <= 'z') {
      if (c <= 'M')
        c += 13;
      else if (c >= 'n')
        c -= 13;
      else if (c >= 'a' && c <= 'm')
        c += 13;
      else if (c >= 'N' && c <= 'Z')
        c -= 13;
    }
    return c;
  }
}
