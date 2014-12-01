package ca.concordia.drms.util.task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Calendar;
import java.util.Date;
import ca.concordia.drms.model.Account;


/**
 * This class is designed to stay on server 
 * @author murindwaz
 */
public class AccountActivity {
	
	
	
	private Account account;
	public AccountActivity( Account account){
			this.account = account;
			String path = "/tmp/" + account.getInstitution().trim();
			if(!new File(path).isDirectory() ){
				if(System.getProperty("os.name").startsWith("Windows") ){
					new File( "C:/" + path).mkdirs();
				}else{
					new File(path).mkdirs();
				}	
			}
	}
	
	
	/**
	 * Logs account activity from account creation to any single operation done on this account
	 * @param operation
	 */
	public void logActivity(String operation){
		Writer writer =  null;
		synchronized (this) {
			File accountFile = getAccountLogFile();
			Calendar now = Calendar.getInstance(); 
			now.setTime(new Date());
			try {
				boolean created = accountFile.exists();
				writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream( accountFile , true) , "utf-8" ));
				String _date = now.get(Calendar.YEAR) + "-" + now.get(Calendar.MONTH) + "-" + now.get(Calendar.DATE) + " " + now.get(Calendar.HOUR) + ":" +  now.get(Calendar.MINUTE) + ":" + now.get(Calendar.SECOND);
				if( !created ) writer.write(String.format( "%s - %s%n", _date, account.toString() ));
				 writer.write(String.format( "%s - %s%n",_date, operation ));
			} catch (IOException ex) {
				System.out.println( " IOException " + accountFile.getAbsolutePath() );
			} finally {
			   try {
				   writer.close();
				 } catch (Exception ex) {
					System.out.println( " IOException " + accountFile.getAbsolutePath() );
				 }
			}
		}
	}
	
	
	public File getAccountLogFile(){
		String path = "/tmp/" + account.getInstitution() + "/" + account.getUsername() + ".md";
		return System.getProperty("os.name").startsWith("Windows")  ? new File("C:"+path) : new File( path );
	}
	
	
}
