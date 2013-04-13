package org.qza.gft.crw;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author gft
 */
public class StatsUtils {

	public static BigDecimal roundNice(long value) {
		return new BigDecimal(String.valueOf(value)).setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public static String decimalFormat(long value) {
		return new DecimalFormat("##.##").format(value);
	}

	public static String decimalFormat(BigDecimal value) {
		return new DecimalFormat("##.##").format(value);
	}

	public static String formatPerSecond(Integer count, long durationNanos) {
		return decimalFormat(calculatePerSecond(count, durationNanos));
	}

	public static String formatPerSecond(Integer count, int seconds) {
		return decimalFormat(calculatePerSecond(count, seconds));
	}

	public static BigDecimal calculatePerSecond(Integer count,
			long durationNanos) {
		long seconds = getSecondsDuration(durationNanos);
		return calculate(count, seconds);
	}

	private static BigDecimal calculate(Integer count, long seconds) {
		if (seconds > 0) {
			BigDecimal result = BigDecimal.valueOf(count).divide(
					BigDecimal.valueOf(seconds), 2, RoundingMode.HALF_UP);
			return result;
		}
		return BigDecimal.ZERO;
	}

	public static String formatDuration(Date start, Date end) {
		long duration = end.getTime() - start.getTime();
		return formatDuration(duration);
	}

	public static String formatDuration(long start, long end) {
		long duration = end - start;
		return formatDuration(duration);
	}
	
	public static String formatDuration(long duration) {
		String res = "";
		duration /= 1000000000;
		int seconds = (int) (duration % 60);
		duration /= 60;
		int minutes = (int) (duration % 60);
		duration /= 60;
		int hours = (int) (duration % 24);
		int days = (int) (duration / 24);
		if (days == 0) {
			res = String.format("%02d:%02d:%02d", hours, minutes, seconds);
		} else {
			res = String.format("%dd%02d:%02d:%02d", days, hours, minutes,
					seconds);
		}
		StringBuilder result = new StringBuilder();
		result.append(res);
		return result.toString();
	}
	
	public static long getSecondsDuration(long nanos) {
		return TimeUnit.SECONDS.convert(nanos, TimeUnit.NANOSECONDS);
	}

	public static long getSecondsDuration(long startNanos, long endNanos) {
		return getSecondsDuration(endNanos - startNanos);
	}

}
