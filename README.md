[![Build Status](https://travis-ci.org/DatabaseGroup9/ProjectGutenberg_G9.svg?branch=master)](https://travis-ci.org/DatabaseGroup9/ProjectGutenberg_G9)

# Gutenberg-Geolocation

### _Which database engines are used?_ <br>
- MongoDB (Document-based database)
- Neo4J (Graph database)

_extra:_
- MySQL (Relational Database)

### _How data is modeled in the database?_ <br>
> _Our data model is designed as shown in the ER Diagram, wherein we have 3 collections: Book, Author and City. Books can be written by multiple authors and have multiple cities mentioned._

![alt ERDiagram](https://github.com/DatabaseGroup9/Documentation/blob/master/images/ER.jpg)

##### In MongoDB, the document is structured like this: (JSON Format)
```
{ "_id" : ObjectId("5b06bc8246391031d0a72538"), 
  "bookID" : "1257",
  "bookTitle" : "The Three Musketeers",
  "author" : {
    "authorID" : "v10500468",
    "fullName" : "Dumas, Alexandre",
    "firstName" : "Alexandre",
    "surName" : "Dumas",
    "title" : "" 
  },
  "cities" : [{ 
    "cityID" : "1002145",
    "name" : "George",
    "lat" : -33.963,
    "lon" : 22.46173 
   },... 
] }
```
##### In Neo4J, it is structured like this:(Cypher Text)
```
MATCH (c:City)<-[:MENTIONS]-(b:Book)<-[:AUTHORED]-(a:Author) return c,b,a limit 1
```
![Neo4j Structure](https://github.com/DatabaseGroup9/Documentation/blob/master/images/graph.png)
##### In MySQL, it is structured like this:(Database Schema Diagram)
![MySQLSchemaDiagram](https://github.com/DatabaseGroup9/Documentation/blob/master/images/mysqlSchemaModelingDiagram.png)

### _How data is modeled in your application?_ <br>
> _We make used of entities/Java objects to map the collections that we have in the database with its attributes as shown in the Class Diagram below. This objects are sent to the REST API and displayed in the frontend._
![entity](https://github.com/DatabaseGroup9/Documentation/blob/master/images/ClassDiagram_Entity.png)
> _This is the class diagram representing the data layer of the application, wherein we make connection to the respective database and perform the query requested by the user._
![data](https://github.com/DatabaseGroup9/Documentation/blob/master/images/ClassDiagram_Data.png)

See more in-detailed implementation [here](https://github.com/DatabaseGroup9/Documentation).

### _How the data is imported?_ <br>
> _The data was fetched from Project Gutenberg Data Collection and processed by our PGParser and extracted the relevant data for   the final project such as the book title, authors, and cities mentioned, see [PGParser](https://github.com/DatabaseGroup9/PGParser) for complete details. We created Digital Ocean droplets for each database, and imported the returned csv files from PGParser to MongoDB and Neo4J by using a Java Application project here, [parseMaster](https://github.com/DatabaseGroup9/parseMaster) and phpAdmin for MySQL. During the development of the application, we have encountered the necessity of refactoring the data to make it cleaner and removed duplicates. We made data cleaner that generates new set of nicely formed data, see the implementation in [dataimport](https://github.com/DatabaseGroup9/dataimport)._

### _Behavior of query test set?_ <br>

_Database Queries corresponding to the end-user queries:_

ID  | MongoDB                                                 |
----|---------------------------------------------------------|
M1  | db.books.find({"cities.name": $cityName})               |
M2  | db.books.find({bookTitle: $bookTitle})                  | 
M3  | db.books.find({"author.fullName",$authorFullName})      | 
M4  | db.books.find({"cities.lat":$lat},{"cities.lon", $lot}) |
*** 
ID  | Neo4J                                                 |
----|---------------------------------------------------------|
N1  | MATCH (a:Author)-[ra:AUTHORED]->(b:Book)-[r:MENTIONS ]-(c:City) WHERE LOWER(c.name) = LOWER($cityName) RETURN a,b |
N2  | MATCH (b:Book)-[r:MENTIONS]->(c:City) WHERE LOWER(b.bookTitle) = LOWER($bookTitle) RETURN c| 
N3  | MATCH (a:Author)-[ra:AUTHORED]->(b:Book)-[r:MENTIONS ]->(c:City) WHERE LOWER(a.fullName) = LOWER($fullName) RETURN a,b,collect(c)| 
N4  | MATCH (a:Author)-[ra:AUTHORED]->(b:Book)-[r:MENTIONS ]->(c:City) WHERE c.lat = $lat AND c.lon = $lon RETURN a,b,collect(c)|
***
ID  | MySQL                                                 |
----|---------------------------------------------------------|
MS1  | |
MS2  | | 
MS3  | | 
MS4  | |
***

#### A. Query Runtime is influenced by the DB engine <br>

#### B. Query Runtime is influenced by the application frontend <br>

### _Recommendation_ <br>
_which database engine to use in such a project for production_

OBS Remember to use the approprite diagram notaions for documentation.

##### Additional Documentation:
> See here: https://github.com/DatabaseGroup9
