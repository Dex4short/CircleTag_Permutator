package io;

import java.io.File;

public class FileHandling {

	private FileHandling() {}

	public static int countFilesInFolder(String url) {
		File folder = new File(url);

		int count=0;
        if (folder.isDirectory()) {
            File[] files = folder.listFiles(File::isFile); // filter only files
            count = (files != null) ? files.length : 0;
        }
        
        return count;
	}
	
}
