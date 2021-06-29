# Web-Mobile Link Converter with Spring Boot

This is a RESTful web service made by using Spring Boot.

* Language: Java 11
* Framework: Spring Boot
* Database: MySQL

## Requirements

For building and running the application you need:

- [JDK 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Maven 3](https://maven.apache.org)

## Setting Up the Local Environment

You need to have a running MySQL server on your local machine on 3306 (default) port.\
application.properties file already has the initial configuration to that end.\
But, you need to change `spring.datasource.username` and `spring.datasource.password` values
according to your local MySQL server login criteria.

## Running the Application

Execute the `main` method in `com/trendyol/demo/DemoApplication.java`.

## Application Details

The application has 2 endpoints:

#### Endpoints

```
[GET] /api/weburl (to convert the deep links to the web urls.)
[GET] /api/deeplink (to convert the web urls to the deep links.)
```

#### Request Sample

Here is a request example to convert a deep link to a web url:
http://localhost:8080/api/weburl with a body like:

```
{
    "deepLink": "ty://?Page=Product&ContentId=1925865&CampaignId=439892"
}
```

