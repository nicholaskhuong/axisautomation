package com.abb.ventyx.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.abb.ventyx.utilities.report.TestMethodResultAdapter;

public class Serializion {
	private ArrayList<TestMethodResultAdapter> resultFromDisks;
	
	String fileLocation = String.format("%s%sresult.ser", System.getProperty("user.dir"), File.separator);
	
	public Serializion(TestMethodResultAdapter result) {
		resultFromDisks = new ArrayList<>();
	}
	
	public Serializion() {
		resultFromDisks = new ArrayList<>();
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TestMethodResultAdapter> getAllTestResult(){
		ArrayList<TestMethodResultAdapter> result = new ArrayList<>();
		
		try {
			FileInputStream fileIn = new FileInputStream(fileLocation);
	        ObjectInputStream in = new ObjectInputStream(fileIn);
			result = (ArrayList<TestMethodResultAdapter>) in.readObject();
	        in.close();
	        fileIn.close();
	      }catch(IOException i) {
	         i.printStackTrace();

		} catch (Exception c) {
	         System.out.println("Error when reading data from disk");
	         c.printStackTrace();
	      }
		return result;
	}
	
	public void saveToDisk(TestMethodResultAdapter result){
		File resultFile = new File(fileLocation);
		if (resultFile.exists()) {
			resultFromDisks = getAllTestResult();
		}
		resultFromDisks.add(result);
		
		try {
			FileOutputStream fileOut = new FileOutputStream(fileLocation);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(resultFromDisks);
	         out.close();
	         fileOut.close();
	         System.out.println("Serialized data is saved");
	      }catch(IOException i) {
	         i.printStackTrace();
	      }
	}

	public void saveToDisk(ArrayList<TestMethodResultAdapter> results) {
		File resultFile = new File(fileLocation);
		if (resultFile.exists()) {
			resultFromDisks = getAllTestResult();
		}
		for (TestMethodResultAdapter result : results) {
			resultFromDisks.add(result);
		}
		try {
			FileOutputStream fileOut = new FileOutputStream(fileLocation);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(resultFromDisks);
			out.close();
			fileOut.close();
			System.out.println("Serialized data is saved");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}
	
}
