package ch.claude_martin.stringwrappers;

import static java.util.Objects.requireNonNull;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Wraps one single source sequence. */
abstract class AbstractSourceWrapper extends AbstractStringWrapper {
  private final CharSequence source;

  public AbstractSourceWrapper(CharSequence source) {
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
