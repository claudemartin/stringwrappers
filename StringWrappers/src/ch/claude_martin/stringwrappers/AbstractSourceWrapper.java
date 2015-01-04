package ch.claude_martin.stringwrappers;


/** Wraps one single source sequence. */
abstract class AbstractSourceWrapper extends AbstractStringWrapper {
  private final CharSequence source;

  public AbstractSourceWrapper(final CharSequence source) {
    super();
    this.source = source;
  }

  final CharSequence getSource() {
    return this.source;
  }

  @Override
  public int length() {
    // This is correct for all but Concat and Substring:
    return this.source.length();
  }

}
