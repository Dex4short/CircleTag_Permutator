package io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class CheckPointFile {
	private static final String CardinalDirections[] = {"NW", "N_", "NE", "W_", "E_", "SW", "S_", "SE"};
	public static String folderName = "/_permutation_log/";

    private CheckPointFile() {
    	
    }
    
    public static void save(String folderPath, int totalTags, String[][] tokens){
    	File folder, file;
    	FileWriter fileWriter;
    	BufferedWriter writer;
        String fileName;
        int fileCount = 1;
    	
    	folder = new File(folderPath);
        if (!folder.exists()) folder.mkdirs();

        do {
            fileName = folderName + "permutation_log" + fileCount + ".txt";
            file = new File(folder, fileName);
            fileCount++;
        } while (file.exists());

        try{
        	folder = new File(folderPath + folderName);
            if (!folder.exists()) folder.mkdirs();
        	
        	fileWriter = new FileWriter(file);
        	writer = new BufferedWriter(fileWriter);
        	
            writer.write(folderPath);
            writer.newLine();
            writer.write(String.valueOf(totalTags));
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
    
    public static Object[] read(String folderPath) {
    	Object data[] = {0, new String[8][]};
    	
    	try {
    		File folder, file, nextFile=null;
    		int fileCount=1;
    		
        	folder = new File(folderPath);
            if (!folder.exists()) folder.mkdirs();
            
            do {
            	file = nextFile;
            	nextFile = new File(folder, folderName + "permutation_log" + fileCount + ".txt");
                fileCount++;
            } while (nextFile.exists());
            
            if(file == null) {
            	return null;
            }
            
    		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
				String line;
				
				reader.readLine();//skip path

				line = reader.readLine();
				data[0] = Integer.parseInt(line);

				reader.readLine();//skip line
				reader.readLine();//skip columns
				
				String token = "";
				int r,c;
				for(r=0; r<8; r++) {
					line = reader.readLine();
					
					String row[] = new String[5];
					c=-1;
					for(char l: line.toCharArray()) {
						if(l == '\t') {
							if(c >= 0) {
								row[c] = token;
							}
							
							c++;
							token = "";
						}
						else {
							token += l;
						}
					}
					row[c] = token;
					token = "";
					
					((String[][])data[1])[r] = row;
				}
				
				reader.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return data;
    }
    
}
