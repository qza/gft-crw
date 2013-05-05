package org.qza.gft.crw;

import java.util.Collection;
import java.util.Set;

/**
 * @author gft
 */
public class ValidUtils {

	public static boolean notBlank(Set<String> strings) {
		return strings != null && strings.size() > 0;
	}

	public static boolean notBlank(String... strings) {
		boolean result = true;
		for (int i = 0; i < strings.length && result; i++) {
			result = notBlank(strings[i]);
		}
		return result;
	}

	public static boolean notBlank(String string) {
		return string != null && string.length() > 0;
	}
	
	public static <C> boolean notBlank(C[] longs) {
		return longs != null && longs.length > 0;
	}
	
	public static <C> boolean notBlank(Collection<C> data) {
		return data != null && data.size() > 0;
	}
	
}
