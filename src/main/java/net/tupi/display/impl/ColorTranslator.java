package net.tupi.display.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ColorTranslator {

	private static int HEX_BASE = 16;

	public static float[] hexToRgb(String hexaCode) {

		assert(hexaCode.length() == 6);
		
		float[] converted = new float[3];
	
		converted[0] = getfloatValue(hexaCode.substring(0, 2));
		converted[1] = getfloatValue(hexaCode.substring(2, 4));
		converted[2] = getfloatValue(hexaCode.substring(4, 6));

		return converted;

	}

	private static float getfloatValue(String hexaValue) {
		return new BigDecimal(Integer.valueOf(hexaValue, HEX_BASE))
				.divide(BigDecimal.valueOf(255), 4, RoundingMode.CEILING).floatValue();
	}

}
