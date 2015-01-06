package ch.claude_martin.stringwrappers;

/** Maps each character to a some character. */
public class CharWrapper extends AbstractSourceWrapper {

	private final CharMapper	mapper;

	CharWrapper(final CharSequence source, final CharMapper mapper) {
		super(source);
		this.mapper = mapper;
	}

	public static StringWrapper of(final CharSequence source, final CharMapper mapper) {
		if (0 == source.length())
			return EmptyWrapper.INSTANCE;
		return new CharWrapper(source, mapper);
	}

	final CharMapper getMapper() {
		return this.mapper;
	}

	@Override
	public final char charAt(final int index) {
		return this.mapper.map(this.getSource().charAt(index));
	}

	@Override
	public final CharSequence subSequence(final int begin, final int end) {
		return of(Substring.of(this.getSource(), begin, end), this.mapper);
	}

}
