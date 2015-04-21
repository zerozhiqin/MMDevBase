package dev.misono.util;

public class CastNumberUtils {

	public static Integer toInteger(String str, Integer defValue) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			return defValue;
		}
	}

	public static Float toFloat(String str, Float defValue) {
		try {
			return Float.parseFloat(str);
		} catch (Exception e) {
			return defValue;
		}
	}

	public static Double toDouble(String str, Double defValue) {
		try {
			return Double.parseDouble(str);
		} catch (Exception e) {
			return defValue;
		}
	}
}
