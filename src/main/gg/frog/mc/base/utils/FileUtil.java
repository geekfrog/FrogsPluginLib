package gg.frog.mc.base.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FileUtil {

	public interface FindFilesDo {

		boolean isProcess(String fileName);

		void process(String fileName, InputStream is);
	}

	public static void findFilesFromJar(FindFilesDo ffd, Class<?> jarClazz) {
		JarFile jarFile = null;
		try {
			String jarFilePath = jarClazz.getProtectionDomain().getCodeSource().getLocation().getFile();
			jarFilePath = URLDecoder.decode(jarFilePath, Charset.defaultCharset().name());
			jarFile = new JarFile(jarFilePath);
			Enumeration<JarEntry> entries = jarFile.entries();
			while (entries.hasMoreElements()) {
				JarEntry e = entries.nextElement();
				if (ffd.isProcess(e.getName())) {
					InputStream is = jarFile.getInputStream(e);
					ffd.process(e.getName(), is);
					try {
						is.close();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jarFile != null) {
				try {
					jarFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void writeFile(File f, InputStream is) {
		File parentFolder = f.getParentFile();
		if (!parentFolder.exists()) {
			parentFolder.mkdirs();
		}
		try {
			OutputStream os = new FileOutputStream(f);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void writeOnFile(String fileName, String content) {
		FileWriter fw;
		try {
			File f = new File(fileName);
			fw = new FileWriter(f, true);
			fw.write(content + "\r\n");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
