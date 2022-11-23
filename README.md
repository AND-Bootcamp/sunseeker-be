# sunseeker-be

### Requirements

    maven 3.8.6, java 19

### Contribution

- Clone this repository.
- Create a new branch.
- Develop your task.
- Create a pull request.
- Do not push to main branch directly.

## Installation and execution

#### Token for OpenUV
Add certificate for OpenUV APIs by retrieving it from the OpenUV portal

Login to the portal and get your API key in the page:
https://www.openuv.io/uvindex


> To authorise your client just add your API Key 971032f4243d5aaf5729c2a118302089 to "x-access-token" header for each request.

In file
sunshine/src/main/resources/application.properties
add at the end of the property
openuv-client.request-options.tokens your own API key

```
openuv-client.request-options.base-url=https://api.openuv.io/api/v1/uv
openuv-client.request-options.tokens=0e67670beedffdddfbf06d4e2d3a6776,971032f4243d5aaf5729c2a118302089
resilience4j.retry.instances.openuv.max-attempts=3
...
...
```

### Running the server in IntelliJ

Set the JDK for the project

In the top right RUN section in IntelliJ add a Run configuration with the following setting:

```
Name: SunSeeker_BE
Build and Run:
- java 19
- digital.and.sunshine.SunshineApplication
```

From the top right RUN section in IntelliJ select the Run configuration SunSeeker_BE and press the green '>' button

This should open a Run section at the bottom of your IntelliJ and after a while display something like:

```
...
...
2022-11-23 11:08:11.595  INFO 41760 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2022-11-23 11:08:11.601  INFO 41760 --- [  restartedMain] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2022-11-23 11:08:11.601  INFO 41760 --- [  restartedMain] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.68]
2022-11-23 11:08:11.639  INFO 41760 --- [  restartedMain] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2022-11-23 11:08:11.639  INFO 41760 --- [  restartedMain] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 1037 ms
2022-11-23 11:08:13.003  INFO 41760 --- [  restartedMain] o.s.b.d.a.OptionalLiveReloadServer       : LiveReload server is running on port 35729
2022-11-23 11:08:13.014  INFO 41760 --- [  restartedMain] o.s.b.a.e.web.EndpointLinksResolver      : Exposing 1 endpoint(s) beneath base path '/actuator'
2022-11-23 11:08:13.039  INFO 41760 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2022-11-23 11:08:13.051  INFO 41760 --- [  restartedMain] d.and.sunshine.SunshineApplication       : Started SunshineApplication in 2.667 seconds (JVM running for 2.948)
```

You can now check the good start of service by browsing the Swagger documentation.


### Swagger Documentation

Access the Swagger API documentation at:

http://localhost:8080/swagger-ui/index.html

To fully test the service:
- open the GET /sun-seeker endpoint description 
- click on the "Try it out" button
- fill some value in the lat and lon parameters
- oresse the "Execute" button

You should get a 200 response with a Response body like this:


```
{
    "userLocation": {
        "coordination": {
            "lon": 2,
            "lat": 1
        },
        "uvLevel": "VERY_HIGH"
    },
    "alternatives": [
        {
            "coordination": {
                "lon": 1.9999999999999774,
                "lat": 1
            },
            "uvLevel": "VERY_HIGH"
        }
    ]
}
```