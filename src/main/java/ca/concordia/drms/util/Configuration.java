package ca.concordia.drms.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import ca.concordia.drms.model.Account;
import ca.concordia.drms.model.Book;
import ca.concordia.drms.model.Reservation;


/**
 * This class will be used to provide common variables under the same banner 
 * @author murindwaz
 */
public class Configuration {
	
	public static final int PORT_NUMBER 	= 2020;
	public static final int UDP_SERVER_PORT	= 2121;
	public static final int REPORT_PROCESSING_PORT = 2323;
	public static final int RESERVATION_PROCESSING_PORT = 2424;
	public static final int COMMAND_PROCESSING_PORT = 2525;
	
	//1024 * 2 
	public static final int BUFFER_SIZE				= 2048;
	
	
	//variables to be used with command switch case, they reflect the order in which commands are organized 
	public static final int BONJOUR = 0; 
	public static final int OVERDUE = 1; 
	public static final int ACCOUNT = 2; 
	public static final int RESERVATION = 3; 
	public static final int INTERLIB = 4; 
	public static final int EXIT = 5; 
	//Replication tasks --- these task IDs are used for managerial purposes
	public static final int BYZANTINE = 6;
	public static final int RESYNC = 7;
	public static final int REPLICATION = 8;

	/**
	 * @todo merge reserveInterLiba and reservation
	 * The code becomes a bit smaller
	 */
	private static String OPERATION_BONJOUR = "bonjour";
	private static String OPERATION_ACCOUNT = "account";
	private static String OPERATION_OVERDUE = "overdue";
	private static String OPERATION_RESERVATION = "reservation";
	private static String OPERATION_INTERLIB = "interlib";

	
	/**
	 * Replica Manager administration tasks
	 */
	private static String OPERATION_BYZANTINE = "byzantine";
	private static String OPERATION_RESYNC = "resync";
	private static String OPERATION_REPLICATION = "replication";

	public static final int REPLICA_MANAGER_PORT =  2626;
	public static final String REPLICA_MANAGER_OPERATION_ACCOUNT = OPERATION_ACCOUNT;
	public static final String REPLICA_MANAGER_OPERATION_OVERDUE = OPERATION_OVERDUE;
	public static final String REPLICA_MANAGER_OPERATION_RESERVATION = OPERATION_RESERVATION;
	public static final String REPLICA_MANAGER_OPERATION_INTERLIB = OPERATION_INTERLIB;
	//Replica Manager administration tasks 
	public static final String REPLICA_MANAGER_OPERATION_BYZANTINE = OPERATION_BYZANTINE;
	public static final String REPLICA_MANAGER_OPERATION_RESYNC = OPERATION_RESYNC;
	public static final String REPLICA_MANAGER_OPERATION_REPLICATION = OPERATION_REPLICATION;
	
	
	
	
	//InetAddress.getByName("localhost")
	//InetAddress.getLocalHost(); == IP of this computer if used InetAddress.getLocalHost().getHostAddress();
	public static final String REPLICA_ONE_IP = "192.168.2.19";//@todo this is IP address to home in longueuil
	public static final String REPLICA_TWO_IP = "192.168.2.19";//@todo this is IP address to home in longueuil
	public static final String REPLICA_THREE_IP = "192.168.2.19";//@todo this is IP address to home in longueuil
	
	
	//Institutions 
	public static final String INSTITUTION_CONCORDIA = "concordia"; 
	public static final String INSTITUTION_DAWSON = "dawson"; 
	public static final String INSTITUTION_MCGILL = "mcgill"; 
	
	//roles 
	public static final String ROLE_ADMIN = "admin"; 
	public static final String ROLE_STUDENT = "student"; 
	
	//reservation operations for  ReservationServer & ReservationClient 
	public static final String DO_RESERVATION = "t";
	public static final String DO_RELEASE	= "r";
	public static final String DO_LOCK		= "l";
	
	//allowed commands 
	public static String[] ALLOWED_COMMANDS = new String[] { "bonjour", "overdue", "account", "reservation", "interlib", "exit" , "byzantine" , "resync" , "replication" };
	
	//Time to wait before timeout.
	public static final int SOCKET_TIMEOUT = (9 * 1000); 
	
	/**@todo remove these messages since they are reserved to clients and not RM and Server side*/
	private static final Map<String,String> help;
	static {
		help = new HashMap<String,String>();
		/**list available books - so the user can choose one**/
        help.put(OPERATION_BONJOUR, "**To better serve you, type in order \"bonjour -i [your institution] -r [your role: student of admin]\" to the following prompt **%s");
        help.put(OPERATION_OVERDUE, "**For a report about non-returners, type \"overdue -u [admin username] -p [admin password] -d [overdue minimun days]\" to the following prompt**%s"); 
        help.put(OPERATION_ACCOUNT, "**to register, type \"account -f [first name] -l [last name] -u [username] -p [password] -e [email] -t [telephone] \"**%s");
        help.put(OPERATION_RESERVATION, "**to make a reservation type \"reservation -u [username] -p [password] -b [Book title] -a [Book author] \"**%s");
        help.put(OPERATION_INTERLIB, "**to make an inter-library reservation type \"interlib -u [username] -p [password] -b [Book title] -a [Book author] \"**%s");
        help.put("exit", "**To close this application, type \"exit\"** to prompt. %s");
	}
	
	
	
	
	
	
	
	public static final Map<String,String> getCommandHelp(){
		 return help;
	}
	
	
	
	
	/**
	 * @todo finalize while testing this thing 
	 * Reservation initialization 
	 * @param library
	 * @return
	 */
	public static final Map<String, Reservation> initReservations( String library ){
		
		
		
		if( library.equals(INSTITUTION_CONCORDIA)){}
		if( library.equals(INSTITUTION_DAWSON)){}
		if( library.equals(INSTITUTION_MCGILL)){}
		
		return null;
	}

	
	/**
	 * This function will be used at initialization time, and is used for testing purposes
	 * @param String library
	 * @return Map<String, Book> books
	 */
	public static final Map<String, Book> initBooks( String library){
		if( library.equals(INSTITUTION_CONCORDIA) ){
			return Collections.unmodifiableMap(new HashMap<String, Book>(){
				private static final long serialVersionUID = -3847019556251171010L;
			{
				
				//put("co-both-kr-2014-1", new Book("Bones of the host", "Kathy Reichs", "co-both-kr-2014-1", INSTITUTION_CONCORDIA));
				//put("co-both-kr-2014-2", new Book("Bones of the host", "Kathy Reichs", "co-both-kr-2014-2", INSTITUTION_CONCORDIA));
			
				//put("co-tbon-lh-2007-1", new Book("The Book of Negroes", "Lawrence Hill", "co-tbon-lh-2007-1", INSTITUTION_CONCORDIA));
				//put("co-tbon-lh-2007-2", new Book("The Book of Negroes", "Lawrence Hill", "co-tbon-lh-2007-2", INSTITUTION_CONCORDIA));
				//put("co-ttom-ws-1606-1", new Book("The Tragedy of Macbeth", "William Shakespeare", "co-ttom-ws-1606-1", INSTITUTION_CONCORDIA));
				//put("co-ttom-ws-1606-2", new Book("The Tragedy of Macbeth", "William Shakespeare", "co-ttom-ws-1606-2", INSTITUTION_CONCORDIA));
				//put("co-mp-p-2007-1", new Book("MulticorePg", "Peter", "co-mp-p-2007-1",INSTITUTION_CONCORDIA));
				//put("co-mp-p-2007-2", new Book("MulticorePg", "Peter", "co-mp-p-2007-2",INSTITUTION_CONCORDIA));
				//put("co-ad-r-1606-1", new Book("Advc++", "Robert", "co-ad-r-1606-1",INSTITUTION_CONCORDIA));
				//put("co-ds-d-1606-1", new Book("DataStr", "David", "co-ds-d-1606-1",INSTITUTION_CONCORDIA));
			
			}});
		}
		if(library.equals(INSTITUTION_DAWSON) ){
			return Collections.unmodifiableMap(new HashMap<String, Book>(){
			
				private static final long serialVersionUID = 522082787535726605L;

			{
				
					
				//put("da-ttohpod-ws-1599-1", new Book("The Tragedy of Hamlet, Prince of Denmark", "William Shakespeare", "da-ttohpod-ws-1599-1", INSTITUTION_DAWSON));
				//put("da-ttohpod-ws-1599-2", new Book("The Tragedy of Hamlet, Prince of Denmark", "William Shakespeare", "da-ttohpod-ws-1599-2", INSTITUTION_DAWSON));
				//put("da-ttom-ws-1606-1", new Book("The Tragedy of Macbeth", "William Shakespeare", "da-ttom-ws-1606-1", INSTITUTION_DAWSON));
				//put("da-ttom-ws-1606-2", new Book("The Tragedy of Macbeth", "William Shakespeare", "da-ttom-ws-1606-2", INSTITUTION_DAWSON));
				//put("da-mp-p-2007-1", new Book("MulticorePg", "Peter", "da-mp-p-2007-1",INSTITUTION_DAWSON));
				//put("da-mp-p-2007-2", new Book("MulticorePg", "Peter", "da-mp-p-2007-2",INSTITUTION_DAWSON));
				//put("da-ad-r-1606-1", new Book("Advc++", "Robert", "da-ad-r-1606-1", INSTITUTION_DAWSON));
				//put("da-ds-d-1606-1", new Book("DataStr", "David", "da-ds-d-1606-1", INSTITUTION_DAWSON));
		
			
			}});
		}
		if(library.equals(INSTITUTION_MCGILL) ){
			return Collections.unmodifiableMap(new HashMap<String, Book>(){
				private static final long serialVersionUID = -6972548486897856666L;

			{
				
				put("mc-both-kr-2014-1", new Book("Bones of the host", "Kathy Reichs", "mc-both-kr-2014-1", INSTITUTION_MCGILL));
				put("mc-both-kr-2014-2", new Book("Bones of the host", "Kathy Reichs", "mc-both-kr-2014-2", INSTITUTION_MCGILL));
				put("mc-both-kr-2014-3", new Book("Bones of the host", "Kathy Reichs", "mc-both-kr-2014-3", INSTITUTION_MCGILL));
				put("mc-both-kr-2014-4", new Book("Bones of the host", "Kathy Reichs", "mc-both-kr-2014-4", INSTITUTION_MCGILL));
			
				//put("mc-ttohpod-ws-1599-1", new Book("The Tragedy of Hamlet, Prince of Denmark", "William Shakespeare", "mc-ttohpod-ws-1599-1", INSTITUTION_MCGILL));
				//put("mc-ttohpod-ws-1599-2", new Book("The Tragedy of Hamlet, Prince of Denmark", "William Shakespeare", "mc-ttohpod-ws-1599-2", INSTITUTION_MCGILL));
				//put("mc-la-jbp1-668-1", new Book("L'Avare", "Jean-Baptiste Poquelin", "mc-la-jbp1-668-1", INSTITUTION_MCGILL));
				//put("mc-la-jbp1-668-2", new Book("L'Avare", "Jean-Baptiste Poquelin", "mc-la-jbp1-668-2", INSTITUTION_MCGILL));
				//put("mc-mp-p-2007-1", new Book("MulticorePg", "Peter", "mc-mp-p-2007-1", INSTITUTION_MCGILL));
				//put("mc-mp-p-2007-2", new Book("MulticorePg", "Peter", "mc-mp-p-2007-2", INSTITUTION_MCGILL));
				//put("mc-ad-r-1606-1", new Book("Advc++", "Robert", "mc-ad-r-1606-1", INSTITUTION_MCGILL));
				//put("mc-ds-d-1606-1", new Book("DataStr", "David", "mc-ds-d-1606-1", INSTITUTION_MCGILL));
		
	
			}});
		}
		return null;
	}

	
	
	
	
	public static final Map<String, Map<String,Account>> initAccounts(String institution){
	
		Map<String, Account> collection = null;
		Map<String, Map<String,Account>> accounts = new HashMap<String, Map<String, Account>>();
		
		//(String first, String last, String email, String telephone, String username, String password,String institution)
		if( institution.equals(INSTITUTION_CONCORDIA) ){
			collection = Collections.unmodifiableMap(new HashMap<String, Account>(){
				private static final long serialVersionUID = 7099089271706241926L;

			{
				put("admin", new Account("Admin", "Admin", "admin@concoridia.ca","514-567-8901", "admin", "admin", INSTITUTION_CONCORDIA){{setAdmin(true);}});
				put("jane.hill", new Account("Jane", "Hill", "jane.hill@concoridia.ca", "514-567-8902", "jane.hill", createPassword("jane.hill"), INSTITUTION_CONCORDIA));
				put("jack.hill", new Account("Jack", "Hill", "jack.hill@concoridia.ca", "514-567-8903", "jack.hill", createPassword("jack.hill"), INSTITUTION_CONCORDIA));
				put("jena.williams", new Account("Jena", "Williams", "jena.williams@concoridia.ca", "514-567-8904", "jena.williams", createPassword("jena.williams"), INSTITUTION_CONCORDIA));
				put("malia.obama", new Account("Malia", "Obama", "malia.obama@concoridia.ca", "514-567-8905", "malia.obama", createPassword("malia.obama"), INSTITUTION_CONCORDIA));
			}});
		}
		if(institution.equals(INSTITUTION_DAWSON) ){
			collection = Collections.unmodifiableMap(new HashMap<String, Account>(){
				private static final long serialVersionUID = -8485071980816548610L;

			{
				put("admin", new Account("Admin", "Admin", "admin@dawson.ca", "514-567-8904", "admin", "admin", INSTITUTION_DAWSON){{setAdmin(true);}});
				put("justin.bieber", new Account("Justin", "Bieber", "justin.bieber@dawson.ca", "514-567-8906", "justin.bieber", createPassword("justin.bieber"), INSTITUTION_DAWSON));
				put("selena.gomez", new Account("Selena", "Gomez", "selena.gomez@dawson.ca", "514-567-8907", "selena.gomez", createPassword("selena.gomez"), INSTITUTION_DAWSON));
				put("aubrey.drake", new Account("Aubrey Drake", "Graham", "aubrey.drake@dawson.ca", "514-567-8908", "aubrey.drake", createPassword("aubrey.drake"), INSTITUTION_DAWSON));
				put("lil.wayne", new Account("Dwayne Michael", "Carter", "lil.wayne@dawson.ca", "514-567-8909", "lil.wayne", createPassword("lil.wayne"), INSTITUTION_DAWSON));
			}});
		}
		if(institution.equals(INSTITUTION_MCGILL) ){
			collection = Collections.unmodifiableMap(new HashMap<String, Account>(){
				private static final long serialVersionUID = 8030116177442730066L;

			{
				put("admin", new Account("Admin", "Admin", "@mcgill.ca", "514-567-8904", "admin", "admin", INSTITUTION_MCGILL){{setAdmin(true);}});
				put("serena.williams", new Account("Serena", "Williams", "@mcgill.ca", "514-567-8914", "serena.williams", createPassword("serena.williams"), INSTITUTION_MCGILL));
				put("victor.hugo", new Account("Victor", "Hugo", "victor.hugo@mcgill.ca", "514-567-8924", "victor.hugo", createPassword("victor.hugo"), INSTITUTION_MCGILL));
				put("prince.harry", new Account("Henry Charles", " Albert David", "prince.harry@mcgill.ca", "514-567-8934", "prince.harry", createPassword("prince.harry"), INSTITUTION_MCGILL));
				put("mahatma.ghandi", new Account("Mohandas", "Gandhi", "mahatma.ghandi@mcgill.ca", "514-567-8944", "mahatma.ghandi", createPassword("mahatma.ghandi"), INSTITUTION_MCGILL));
			}});
		}
		
		
		//formatting the account
		if( collection != null && collection.size() > 0 ){
			for (Entry<String, Account> entry : collection.entrySet()){
				String initial = entry.getValue().getFirst().substring(0, 1).toUpperCase();
				if (!accounts.containsKey(initial)) {
					accounts.put(initial, new HashMap<String,Account>());
				}
				accounts.get(initial).put(entry.getValue().getUsername(), entry.getValue());
			}
			
			return accounts;
		}
		return null;
	}
	
	
	/**
	 * @return
	 */
	public static final String getApplicationTitle(){
		return "Welcome to RDMS - Beta version\r\n"
				+"Available institutions : Concordia ( concordia ), Dawson ( dawson ), McGill (mcgill)\r\n";
	}
	

	/**
	 * @todo test this section to evaluate if string does return true value 
	 * @param username
	 * @return String 
	 */
	public static final String  createPassword( String username ){
		return new StringBuffer( username.replaceAll(".*\\W+.*", "")).reverse().toString();//remove all non characters
	}
	
	
	
	
	

}