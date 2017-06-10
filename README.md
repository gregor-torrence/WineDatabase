**Toy Wine Database**

Build with java 8:

```./gradlew clean jar```

Run:

```java -jar ./build/libs/GregorAssessment.jar```

CRUD endpoints:
```
Create: POST   http://localhost:8080/wine
Read:   GET    http://localhost:8080/wine/:uuid
Update: POST   http://localhost:8080/wine
Delete: DELETE http://localhost:8080/wine/:uuid
```

Example JSON for posting:

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
