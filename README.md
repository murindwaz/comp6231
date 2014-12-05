#COMP 6231 - Project 
##Replica Manager and Servers  




###Installation procedures 

```
	- download and install eclipse 
	- download and install eclipse maven plugin 
	- to install the project, run the following command
	>$ git clone https://github.com/murindwaz/comp6231.git 
	- Or, download zipped file at https://github.com/murindwaz/comp6231 
	- Import the project in Eclipse
	- At root directory, find the pom.xml 
	- Right click the pom.xml and run install. 
	- If all dependencies are ready to use, compile the project.
```

###Configuration and Running a Replica Instance 

```
	
```


###Operations available 
The client can send following requests 

```
	- { operation: OPERATION, id : MESSAGE_ID, destination : INSTITUTION, payload : MESSAGE, replica: REPLICA_FIELD }
	- OPERATIONS are:
		- account : to create account 
		- reservation: to make a book reservation ( this covers the interlibrary reservation as well ) 
		- overdue: to get a list of non-returners 
	- PAYLOAD: 
		- Account model JSON string representation 
		- Reservation model JSON string representation
	- REPLICA: 
		- Replica field is added by the replica that processed the request
```

###Servers 
Each replica manager runs 3 instance of servers 
	- Server One : Concordia 
	- Server Two : McGill 
	- Server Three : Daswon

###Library Content 
	- Go to ca.concordia.drms.util.Configuration
	- Find 

References
====


1. [Import the project in Eclipse] 

[Import the project in Eclipse]: http://people.cs.uchicago.edu/~kaharris/10200/tutorials/eclipse/Step_04.html "Import the project in Eclipse"

