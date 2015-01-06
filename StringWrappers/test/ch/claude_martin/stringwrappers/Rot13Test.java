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

    if (this.input.length() == 26) {
      // UperCase.of("\u0131") => "I"
      final StringWrapper upper_rot13 = UpperCase.of(rot13);
      final StringWrapper rot13_upper = Rot13.of(UpperCase.of(this.input));
      this.assertEqualStrings(upper_rot13, rot13_upper);
    }
  }

}
