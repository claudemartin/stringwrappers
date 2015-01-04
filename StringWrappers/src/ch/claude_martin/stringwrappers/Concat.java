package ch.claude_martin.stringwrappers;

import static java.util.Objects.requireNonNull;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Concat extends AbstractStringWrapper {

  private final List<CharSequence> list;
  private final int length;

  private Concat(final List<CharSequence> list) {
    this.list = list;
    long l = 0;
    for (CharSequence s : list) {
      l += s.length();
      if (l > Integer.MAX_VALUE)
        throw new RuntimeException("Concatenated Strings are longer than Integer.MAX_VALUE");
    }

    this.length = (int) l;
  }

  public static StringWrapper of(CharSequence first, CharSequence... more) {
    final List<CharSequence> _list = new ArrayList<>(more.length + 1);
    _list.add(first);
    for (CharSequence s : more) {
      _list.add(s);
    }
    return new Concat(_list);
  }

  @Override
  public int length() {
    return this.length;
  }

  @Override
  public char charAt(int index) {
    if ((index < 0) || (index >= this.length)) {
      throw new StringIndexOutOfBoundsException(index);
    }

    for (CharSequence s : this.list) {
      if (index >= s.length()) {
        index -= s.length();
      } else {
        return s.charAt(index);
      }
    }

    throw new RuntimeException("char not found");
  }

  @Override
  public StringWrapper concat(CharSequence... s) {
    requireNonNull(s, "s");

    final List<CharSequence> l = new ArrayList<>(this.list.size() + s.length);
    for (CharSequence seq : this.list) {
      l.add(seq);
    }
    for (CharSequence seq : s) {
      l.add(seq);
    }
    return new Concat(l);
  }

  @Override
  public StringWrapper map(CharMapper mapper) {
    final List<CharSequence> l = new ArrayList<>(this.list.size());
    for (CharSequence s : this.list) {
      l.add(CharWrapper.of(s, mapper));
    }
    return new Concat(l);
  }

}
