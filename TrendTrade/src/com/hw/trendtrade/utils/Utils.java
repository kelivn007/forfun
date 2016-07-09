package com.hw.trendtrade.utils;

import java.text.DecimalFormat;

public class Utils {

	public static boolean isBiggerZero(double num) {
		return (num - 0.000001) > 0;
	}

	public static String formatDouble(double num) {
		DecimalFormat dfFormat = new DecimalFormat("######0.00");
		return dfFormat.format(num);
	}
}
