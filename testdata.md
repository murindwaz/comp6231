> bonjour -i concordia -r student
> account -f Pascal -l Maniraho -u pmn -p pmn -e pmn@conc.ca -t 5553333
> reservation -u pmn -p pmn -b The Tragedy of Macbeth -a William Shakespeare
> interlib -u pmn -p pmn -b The Tragedy of Hamlet, Prince of Denmark -a William Shakespeare

> bonjour -i concordia -r admin
> overdue -u admin -p admin -d -1

> bonjour -i dawson -r student
> account -f Pascal -l Maniraho -u pmn -p pmn -e pmn@conc.ca -t 5553333
> reservation -u pmn -p pmn -b The Tragedy of Macbeth -a William Shakespeare
> interlib -u pmn -p pmn -b The Tragedy of Hamlet, Prince of Denmark -a William Shakespeare

Server One : Concordia
==========

> bonjour -i concordia -r student
> account -f Borow -l MrKing -u borowmiking -p pmn -e borowmiking@conc.ca -t 5553333
> interlib -u borowmiking -p pmn -b Bones of the host -a Kathy Reichs


> account -f Prince -l Harry -u prha -p prha -e prha@conc.ca -t 5553333
> reservation -u prha -p prha -b Bones of the host -a Kathy Reichs
> interlib -u prha -p prha -b Bones of the host -a Kathy Reichs


Server Two : Dawson
==========

Server Three : McGill
===========

Has 3 copies of


WebServices
===

wsgen
@link https://docs.oracle.com/javase/6/docs/technotes/tools/share/wsgen.html

```sh
  # -s directory for generated source (*.java) files
  # -r directory for generated ressource files( .xsd, .wsdl, ... )
  # -d directory for generated output files
  # -wsdl tells wsdl to generate .wsdl files
  # -cp/-classpath tells wsgen where to search for .class files  
  > wsgen -verbose -cp target/classes ca.concordia.drms.ws.LibraryServerImpl -s src/main/java -r target/classes -d target/classes -wsdl
```
wsimport
@link https://docs.oracle.com/javase/6/docs/technotes/tools/share/wsimport.html

```sh
  # wsimport -keep http://localhost:2020/ws/concordia?wsdl -p ca.concordia.drms.ws.client -s src/main/java  -d target/classes
```
