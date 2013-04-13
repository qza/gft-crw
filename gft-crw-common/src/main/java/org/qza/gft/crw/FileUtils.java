package org.qza.gft.crw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

public class FileUtils {

	public static void loadData(String fileName, Collection<String> data) {
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);
			String s = null;
			while ((s = br.readLine()) != null) {
				String link = s.trim();
				data.add(link);
			}
			br.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void writeData(String fileName, Collection<String> data) {
		FileWriter writer;
		try {
			File file = new File(fileName);
			writer = new FileWriter(file);
			Iterator<String> it = data.iterator();
			while (it.hasNext()) {
				String lnk = it.next();
				writer.write(lnk + "\r\n");
			}
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
