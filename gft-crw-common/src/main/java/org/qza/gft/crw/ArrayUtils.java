package org.qza.gft.crw;

import java.util.Arrays;

public class ArrayUtils {

	public static <C> String sqlInArray(C[] ids) {
		String idsString = Arrays.toString(ids);
		idsString = idsString.replace("[", "(");
		idsString = idsString.replace("]", ")");
		return idsString;
	}

}
