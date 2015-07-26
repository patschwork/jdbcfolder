JdbcFolder is a type-4 Jdbc driver. It allows to execute SQL queries on the underlying filesystem of the JVM.

The global view is the folowing : a folder is symbolized as a table, each column of this table is a property of the elements contained in this folder.

Eg : select FILENAME, SIZE, EXTENSION from c:/myfolder

The result will be a dataset containing three columns ( FILENAME, SIZE et EXTENSION ) and a number of rows corresponding to the number of files present in the directory.

Of course, the number of returned columns will be growing as we will advance in implementation.

Current released version of jdbcFolder works with ODI until version prior to 10.1.3.5.0. For 10.1.3.5.0 and above, upcoming version seems to work.