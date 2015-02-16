package ch.claude_martin.stringwrappers;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;

/**
 * Wrapper that uses a file. The encoding must be UTF-16.
 *
 * <p>
 * Note: This does not implement {@link AutoCloseable}. If a
 * {@link RandomAccessFile} is passed then if must be close by the code that
 * created it. If a path to a file is given then the internally created
 * {@link RandomAccessFile} will be closed automatically.
 *
 * @author Claude Martin
 *
 */
public final class FileWrapper extends AbstractStringWrapper {
  private final RandomAccessFile input;
  private final int              length;
  private final boolean          closeOnFinalize;

  // Finalizer Guardian idiom:
  @SuppressWarnings("unused")
  private final Object   finalizerGuardian = new Object() {
    @Override
    protected void finalize() throws Throwable {
      FileWrapper.this.cleanup();
    }
  };

  void cleanup() throws IOException {
    if (this.closeOnFinalize)
      this.input.close();
  }

  private FileWrapper(final RandomAccessFile input, final int length, final boolean closeOnFinalize) {
    this.input = input;
    this.length = length;
    this.closeOnFinalize = closeOnFinalize;
  }

  /**
   * Creates a wrapper from the given RandomAccessFile. Note that the input will
   * never be closed by this wrapper.
   *
   * @param input
   *          The input.
   * @return new wrapper.
   * @throws IOException
   */
  public static StringWrapper of(final RandomAccessFile input) throws IOException {
    return _of(input, false);
  }

  /**
   * Creates a wrapper from the given RandomAccessFile. Note that the input will
   * be closed by this wrapper when it is removed by garbage collection.
   *
   * @param file
   *          the file
   * @return New wrapper.
   * @throws IOException
   */
  public static StringWrapper of(final Path file) throws IOException {
    requireNonNull(file, "file");
    return of(file.toFile());
  }

  /**
   * Creates a wrapper from the given RandomAccessFile. Note that the input will
   * be closed by this wrapper when it is removed by garbage collection.
   *
   * @param file
   *          the file
   * @return New wrapper.
   * @throws IOException
   */
  public static StringWrapper of(final File file) throws IOException {
    requireNonNull(file, "file");
    final RandomAccessFile raf = new RandomAccessFile(file, "r");
    return _of(raf, true);
  }

  /**
   * Creates a wrapper from the given RandomAccessFile. Note that the input will
   * be closed by this wrapper when it is removed by garbage collection.
   *
   * @param file
   *          the system-dependent filename
   * @return New wrapper.
   * @throws IOException
   */
  public static StringWrapper of(final CharSequence file) throws IOException {
    requireNonNull(file, "file");
    return of(new File(file.toString()));
  }

  private static StringWrapper _of(final RandomAccessFile input, final boolean closeOnFinalize) throws IOException {
    final long len = input.length();
    if (0 == len) {
      if (closeOnFinalize)
        input.close();
      return EmptyWrapper.INSTANCE;
    }
    if (Integer.MAX_VALUE <= len) {
      throw new IOException("File too long.");
    }

    return new FileWrapper(input, (int) len, closeOnFinalize);
  }

  @Override
  public int length() {
    return this.length;
  }

  @Override
  public char charAt(final int index) {
    try {
      this.input.seek(index);
      return this.input.readChar();
    } catch (final IOException e) {
      throw new RuntimeException("Can't get char at " + index, e);
    }
  }
}
