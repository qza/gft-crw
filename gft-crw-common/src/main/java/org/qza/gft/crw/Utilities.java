package org.qza.gft.crw;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collection;

public class Utilities {

	public static void loadData(String fileName, Collection<String> collection) {
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);
			String s = null;
			while ((s = br.readLine()) != null) {
				String link = s.trim();
				collection.add(link);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (fr != null) {
					fr.close();
				}
			} catch (Exception ex1) {
			}
		}
	}

}
