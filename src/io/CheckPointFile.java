package io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class CheckPointFile {
	private static final String CardinalDirections[] = {"NW", "N_", "NE", "W_", "E_", "SW", "S_", "SE"};
	private File folder, file;
	private BufferedWriter writer;
	private FileWriter file_writer;

    public CheckPointFile() {
    	
    }
    public void save(String folderPath, int number, String[][] tokens){
        folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        int fileCount = 1;
        do {
            String fileName = "_permutation_log/permutation_log" + fileCount + ".txt";
            file = new File(folder, fileName);
            fileCount++;
        } while (file.exists());

        try{
        	file_writer = new FileWriter(file);
        	writer = new BufferedWriter(file_writer);
        	
            writer.write(folderPath);
            writer.newLine();
            writer.write(String.valueOf(number));
            writer.newLine();
            writer.newLine();

            writer.write("...\torbit1\torbit2\torbit3\torbit4\torbit5");
            writer.newLine();

            for (int i = 0; i < tokens.length && i < CardinalDirections.length; i++) {
                writer.write(CardinalDirections[i]);
                for (int j = 0; j < tokens[i].length; j++) {
                    writer.write("\t");
                    writer.write(tokens[i][j] != null ? tokens[i][j] : "");
                }
                writer.newLine();
            }
            
            writer.close();
        }catch(Exception e) {
        	e.printStackTrace();
        }
    }
}
