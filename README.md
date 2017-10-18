**Example SparkJava microservice**

Here is a small, realistic starting point for building a CRUD microservice using [SparkJava](http://www.sparkjava.com/).
It's a Wine Database with in-memory storage. Why wine? Because we're all tired of example code that stores users.
 
It establishes patterns for separating implementation concerns:
HTTP request handling, persistence, and error handling. 

100% unit test coverage is provided using [Spock](http://spockframework.org/). 

Build with java 8:

```./gradlew clean jar```

Run:

```java -jar ./build/libs/WineREST.jar```

CRUD endpoints:
```
Create: POST   http://localhost:8080/wine          Returns created wine's UUID as a String.
Read:   GET    http://localhost:8080/wine/:uuid    Returns specified wine's JSON.
Update: PUT    http://localhost:8080/wine          Returns updated wine's JSON.
Delete: DELETE http://localhost:8080/wine/:uuid    Returns nothing.
```

```
200 status code for successful operations.
404 status code when the supplied UUID is not found.
500 status code for any internal error
```

Example JSON for posting to `http://localhost:8080/wine`:

```
{
  "name" : "2014 Cameron Reserve Chardonnay",
  "winery" : "Cameron Winery",
  "varietal" : "Chardonnay",
  "vintage" : 2014,
  "appellation" : "Dundee Hills"
}

```

```
{
  "name" : "2013 Elizabeth's Reserve Pinot Noir",
  "winery" : "Adelsheim Winery",
  "varietal" : "Pinot Noir",
  "vintage" : 2013,
  "appellation" : "Chehalem Mountains"
}

```
