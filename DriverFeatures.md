# 0.1 version #

### What's possible ###
  * Select files from a directory :
> > `SELECT * from c:/mydirectory `
  * Use of a WHERE clause :
> > `SELECT * from c:/mydirectory where extension = 'pdf'`
  * Associate several filters in the WHERE clause ( AND and OR operators )
> > `SELECT * from c:/mydirectory where extension = 'pdf' or extension = 'vbs'`
  * Use parenthesis in the WHERE clause
> > `SELECT * from c:/mydirectory where extension = 'pdf' or extension = 'vbs' and SIZE > 0 ` sends a different result than `SELECT * from c:/mydirectory where ( extension = 'pdf' and SIZE > 0 ) or extension = 'vbs'`
  * Retrieve the following properties from files :
    * FILENAME
    * SIZE
    * EXTENSION
  * Filter on the following file properties :
    * FILENAME
    * SIZE
    * EXTENSION
  * Use paths in Windows or Unix format
  * Repeat several times the same field name in the SELECT clause

### What's coming ###
  * Use of aggregation functions
  * Use of utilitary functions.
  * Retrieve of the usefull metadata from the "database"
  * Handle logs in a clean way
  * Add of unit testing