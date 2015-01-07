package ch.claude_martin.stringwrappers;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

/** Concatenation of multiple strings. */
public final class Concat extends AbstractStringWrapper {
  /** List of character sequences. */
  private final List<CharSequence> list;
  /** Total length - the sum of all characters. */
  private final int                length;

  private Concat(final List<CharSequence> list, final long length) {
    this.list = list;
    if (list.isEmpty())
      throw new AssertionError();
    assert length > 0;
    if (length > Integer.MAX_VALUE)
      throw new RuntimeException("Concatenated Strings are longer than Integer.MAX_VALUE");
    this.length = (int) length;
  }

  public static StringWrapper of(final CharSequence string) {
    requireNonNull(string, "string");
    if (string.length() == 0)
      return StringUtils.empty();
    if (string instanceof StringWrapper)
      return (StringWrapper) string;
    return NullWrapper.of(string);
  }

  public static StringWrapper of(final CharSequence a, final CharSequence b) {
    requireNonNull(a, "a");
    requireNonNull(b, "b");
    final long len = a.length() + b.length();
    if (len == 0)
      return StringUtils.empty();
    final List<CharSequence> _list = new ArrayList<>(2);
    if (a.length() > 0)
      _list.add(a);
    if (b.length() > 0)
      _list.add(b);
    return new Concat(_list, len);
  }

  public static StringWrapper of(final CharSequence... strings) {
    requireNonNull(strings, "strings");
    final List<CharSequence> _list = new ArrayList<>(strings.length);
    long len = 0;
    for (final CharSequence s : strings) {
      if (s.length() == 0)
        continue;
      _list.add(s);
      len += s.length();
    }
    if (len == 0)
      return StringUtils.empty();
    return new Concat(_list, len);
  }

  public static StringWrapper of(final CharSequence first, final CharSequence... more) {
    requireNonNull(first, "first");
    requireNonNull(more, "more");
    final List<CharSequence> _list = new ArrayList<>(more.length + 1);
    long len = first.length();
    if (len > 0)
      _list.add(first);
    for (final CharSequence s : more) {
      if (s.length() == 0)
        continue;
      _list.add(s);
      len += s.length();
    }
    if (len == 0)
      return StringUtils.empty();
    return new Concat(_list, len);
  }

  public static StringWrapper repeat(final CharSequence string, final int count) {
    requireNonNull(string, "string");
    final long len = count * (long) string.length();
    if (count < 0)
      throw new IllegalArgumentException("count = " + count);
    else if (count == 1)
      return NullWrapper.of(string);
    else if (len == 0)
      return StringUtils.empty();
    final List<CharSequence> _list = new ArrayList<>(count);
    for (int i = 0; i < count; i++) {
      _list.add(string);
    }
    return new Concat(_list, len);
  }

  @Override
  public boolean endsWith(final CharSequence string) {
    requireNonNull(string, "string");
    final CharSequence last = this.list.get(this.list.size() - 1);
    if (last.length() == 0)
      return true;
    if (last.length() >= string.length())
      return NullWrapper.of(last).endsWith(string);
    return super.endsWith(string);
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

    for (final CharSequence s : this.list) {
      if (index >= s.length()) {
        index -= s.length();
      } else {
        return s.charAt(index);
      }
    }

    throw new RuntimeException("char not found");
  }

  @Override
  public StringWrapper concat(final CharSequence... s) {
    requireNonNull(s, "s");

    if (s.length == 0)
      return this;

    final List<CharSequence> _list = new ArrayList<>(this.list.size() + s.length);
    for (final CharSequence seq : this.list) {
      _list.add(seq);
    }
    long len = this.length;
    for (final CharSequence seq : s) {
      if (seq.length() == 0)
        continue;
      _list.add(seq);
      len += seq.length();
    }
    if (len == 0)
      return this;
    return new Concat(_list, this.length + len);
  }

  @Override
  public StringWrapper map(final CharMapper mapper) {
    final List<CharSequence> l = new ArrayList<>(this.list.size());
    for (final CharSequence s : this.list) {
      l.add(CharWrapper.of(s, mapper));
    }
    return new Concat(l, this.length);
  }

}
