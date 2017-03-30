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
	
	private TestMethodResultAdapter result;
	
	public Serializion(TestMethodResultAdapter result) {
		this.result = result;
		resultFromDisks = new ArrayList<>();
	}
	
	public Serializion() {
		resultFromDisks = new ArrayList<>();
	}
	
	public ArrayList<TestMethodResultAdapter> getAllTestResult(){
		ArrayList<TestMethodResultAdapter> result = new ArrayList<>();
		
		try {
	         FileInputStream fileIn = new FileInputStream(System.getProperty("user.dir") + "\\result.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         result = (ArrayList<TestMethodResultAdapter>) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i) {
	         i.printStackTrace();

	      }catch(ClassNotFoundException c) {
	         System.out.println("Error when reading data from disk");
	         c.printStackTrace();
	      }
		return result;
	}
	
	public void saveToDisk(TestMethodResultAdapter result){
		File resultFile = new File(System.getProperty("user.dir") + "\\result.ser");
		if(!resultFile.exists()){
			resultFromDisks.add(result);
		}
		else{
			resultFromDisks = getAllTestResult();
			resultFromDisks.add(result);
		}
		
		try {
	         FileOutputStream fileOut =
	         new FileOutputStream(System.getProperty("user.dir") + "\\result.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(resultFromDisks);
	         out.close();
	         fileOut.close();
	         System.out.println("Serialized data is saved");
	      }catch(IOException i) {
	         i.printStackTrace();
	      }
	}
	
}
