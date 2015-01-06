package ch.claude_martin.stringwrappers;

import static org.junit.Assert.assertSame;

import org.junit.Test;

public class Rot13Test extends AbstractStringWrapperTest {

	public Rot13Test(final String input) {
		super(input);
	}

	@Test
	public void testOf() {
		final StringWrapper rot13 = Rot13.of(this.input);
		if (this.input.isEmpty())
			assertSame(EmptyWrapper.INSTANCE, rot13);
		final String str = rot13.toString();
		final StringWrapper rot26 = Rot13.of(str);
		this.assertEqualStrings(this.input, rot26);
	}

}
