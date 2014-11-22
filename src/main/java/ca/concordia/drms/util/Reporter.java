package ca.concordia.drms.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.*;
import java.util.Map.Entry;
import ca.concordia.drms.model.*;




/**
 * This class will write response to a file. This class is designed to be shipped to clients 
 * @author murindwaz
 */
public class Reporter {
	
	private Account admin;
	private String strfile;
	public Reporter( Account admin ){
		this.admin = admin;
		Calendar now = Calendar.getInstance(); 
		now.setTime(new Date());
		String filename = String.format( "%d-%d-%d.md", now.get(Calendar.YEAR) , now.get(Calendar.MONTH) , now.get(Calendar.DATE));
		String path = "/tmp/" + admin.getInstitution().trim();
		if(!new File(path).isDirectory() ){
			if(System.getProperty("os.name").startsWith("Windows") ){
				new File( "C:/" + path).mkdirs();
			}else{
				new File(path).mkdirs();
			}	
		}
		this.strfile = path + "/" +filename;
	}
	
	
	/**
	 * writes a stream to strfile in append mode, and OutpuStreamWriter uses "utf-8" charset encoding
	 * @param reservations
	 */
	public void report(Map<String, Reservation> reservations){
		Writer writer =  null;
		synchronized (this) {
			try {
				writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream( new File(this.strfile) , true) , "utf-8" ));
				for (Entry<String, Reservation> entry : reservations.entrySet()) {
					writer.write(String.format( "%s%n", entry.getValue().toString() ));
				}
			} catch (IOException ex) {
				System.out.println( " IOException " + this.strfile );
			} finally {
			   try {
				   writer.close();
				 } catch (Exception ex) {
					System.out.println( " IOException " + this.strfile );
				 }
			}
		}
	}
	
	public String getFilePath(){
		return this.strfile;
	}
}