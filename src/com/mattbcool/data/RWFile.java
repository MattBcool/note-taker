package com.mattbcool.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class RWFile
{
	public ArrayList<String> readFromFile(String fileName)
	{
		File file = new File(fileName);
		ArrayList<String> text = new ArrayList<String>();
        String line = null;

        try
        {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null)
            {
                text.add(line);
            }
            bufferedReader.close();
        }
        catch(NullPointerException | IOException e)
        {
            System.out.println("Unable to open file '" + fileName + "'");
            e.printStackTrace();
        }
		return text;
	}
	
	public boolean writeToFile(String fileName, ArrayList<String> text)
	{
		File file = new File(fileName);
		try {
			file.createNewFile();
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			for(int i = 0; i < text.size(); i++)
	        {
				String line = text.get(i);
				writer.write(line.replace("\n", ""));
				if(i < text.size()+1) writer.newLine();
	        }
			writer.close();
			return true;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean removeFile(String fileName)
	{
		File file = new File(fileName);
		return file.delete();
	}
}
