package net.tupi.display;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import net.tupi.display.impl.ColorTranslator;

public class ColorTranslatorTest {

	@Test
	public void hexToRgb_test_happyPath_1() {

		String hexaColor = "8EBDF2";

		float[] hexToRgb = ColorTranslator.hexToRgb(hexaColor);

		assertThat(Float.valueOf(hexToRgb[0]), equalTo(Float.valueOf(0.5569f)));
		assertThat(Float.valueOf(hexToRgb[1]), equalTo(Float.valueOf(0.7412f)));
		assertThat(Float.valueOf(hexToRgb[2]), equalTo(Float.valueOf(0.9491f)));
	}

	@Test
	public void hexToRgb_test_happyPath_2() {

		String hexaColor = "FdFCBD";

		float[] hexToRgb = ColorTranslator.hexToRgb(hexaColor);

		assertThat(Float.valueOf(hexToRgb[0]), equalTo(Float.valueOf(0.9922f)));
		assertThat(Float.valueOf(hexToRgb[1]), equalTo(Float.valueOf(0.9883f)));
		assertThat(Float.valueOf(hexToRgb[2]), equalTo(Float.valueOf(0.7412f)));
	}

	@Test()
	public void hexToRgb_test_invalid_hexa() {

		String hexaColor = "XdFCBD";

		Assertions.assertThrows(NumberFormatException.class, () -> {
			ColorTranslator.hexToRgb(hexaColor);
		});

	}

	@Test()
	public void hexToRgb_test_invalid_color() {

		String hexaColor = "XdFCBDaaa";

		Assertions.assertThrows(AssertionError.class, () -> {
			ColorTranslator.hexToRgb(hexaColor);
		});

	}

}
