# Introduction #

> In this page you will find the procedure to follow to use jdbcFolder with two tools :
    * SQuirreL SQL
    * Oracle Data Integrator


# Configuration #

  1. Download the rhino1\_5R5.zip file from the following website :

> [Rhino project](http://www.mozilla.org/rhino)

  1. Go to _Downloads-> Rhino downloads archive_ section
  1. Extract js.jar and add it in the classpath of the java application
  1. Add jdbcFolder0.1.jar at the java application classpath


The driver class is :

_com.ysance.tools.jdbc.driver.JdbcFolderDriver_


By now the driver url is fixed at :

_jdbc:folder_


No login information to enter ( by now )

# SQuirreL SQL #

  1. Add the two jar files in the **lib** directory of SQuirreL an then launch the application.
  1. Configure a new driver named JdbcFolder and fill-up the field with the url and driver class given before
  1. Create an alias selecting the JdbcFolder driver. The url is still the same than above.
  1. Connect and try a `select * from /windows ` query

# Oracle Data Integrator #

  1. Add the two jar files in the **drivers** directory of ODI.
  1. Launch jython.bat once ( it caches the jars ) and exit (Ctrl+Z).

## Configure the technology ##

  1. Insert new logical schema ( ex : JFLDR\_TEST ) in the File Technology

  1. Go in the **Topology -> Physical architecture** and insert a new Dataserver ( ex : JDBCFOLDER\_TEST ) always for the File technology.
  1. Set the driver class as _com.ysance.tools.jdbc.driver.JdbcFolderDriver
  1. Set the driver URL as_jdbc:folder

  1. Under the server, add a new physical schema, it can be blank.
  1. Associate the logical and the physical schemas in a context.

## Create a new Treatment ##

  * Add a new line of **Sunopsis API** type and put in it :
```
    OdiOutFile -FILE=C:/temp/list.txt -APPEND -XROW_SEP=0D0A
    this a file named #filename
```
  * Configure the **Source** tab with the File technology, and JFLDR\_TEST logical schema.
  * Add the following query :
```
    select filename from /temp
```
  * Save all and execute.

  * ==> The C:\temp directory should now have a file called list.txt containing the ligne of files present in this directory ( minus list.txt as it has been created after the sélection.
  * ==> OdiOutFile can be replaced by any other ODI tool, by a jython code line or even SQL, like any other treatment.


The only drawback is that, by now, ODI substitution methods don't work.