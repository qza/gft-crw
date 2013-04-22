package org.qza.gft.crw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.qza.gft.crw.gzip.Gziper;
import org.qza.gft.crw.gzip.MessageGziper;
import org.qza.gft.crw.gzip.StringGziper;

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

	public static void writeTextGzip(String fileName, Collection<String> data) {
		writeDataGzip(fileName, data, new StringGziper());
	}

	public static void writeMessagesGzip(String fileName,
			Collection<Message> data) {
		writeDataGzip(fileName, data, new MessageGziper());
	}

	public static void loadTextGzip(String fileName, Collection<String> data) {
		loadDataGzip(fileName, data, new StringGziper());
	}

	public static void loadMessagesGzip(String fileName,
			Collection<Message> data) {
		loadDataGzip(fileName, data, new MessageGziper());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void writeDataGzip(String fileName, Collection data,
			Gziper gzipper) {
		FileOutputStream out = null;
		GZIPOutputStream gzip = null;
		File file = new File(fileName);
		try {
			out = new FileOutputStream(file);
			gzip = new GZIPOutputStream(out);
			for (Iterator iterator = data.iterator(); iterator
					.hasNext();) {
				gzipper.write(gzip, iterator.next());
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				gzip.close();
				out.close();
			} catch (IOException e) {
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void loadDataGzip(String fileName, Collection data,
			Gziper gzipper) {
		FileInputStream in = null;
		GZIPInputStream gzip = null;
		InputStreamReader sr = null;
		BufferedReader br = null;
		File file = new File(fileName);
		try {
			in = new FileInputStream(file);
			gzip = new GZIPInputStream(in);
			sr = new InputStreamReader(gzip);
			br = new BufferedReader(sr);
			String line;
			while ((line = br.readLine()) != null) {
				gzipper.read(data, line);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				br.close();
				sr.close();
				gzip.close();
				in.close();
			} catch (IOException e) {
				// asshole
			}
		}
	}

	public static void main(String[] args) {
		String baseName = "src/main/resources/data/queue";
		Set<String> data = new HashSet<String>();
		FileUtils.loadData(baseName + ".txt", data);
		FileUtils.writeTextGzip(baseName + ".gz", data);
		int size = data.size();
		data.clear();
		FileUtils.loadTextGzip(baseName + ".gz", data);
		System.out.println("SIZE EQUALS ::: " + (size == data.size()));
	}

}
