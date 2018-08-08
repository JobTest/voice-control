
Voice Control
-------------

Stack:
- `Java 8`
- `Spring Boot 2` (для `Reactive Spring 5` Swagger не поддерживается)
- `Spring Webflux` & `WebFluxTest`
- JavaScript `WebSocket`
- `MongoDB Reactive`
- `Gradle`

############################################################

`spring.data.mongodb.host` and `spring.data.mongodb.port` are not supported if you’re using the **Mongo 3.0 Java driver**.
In such cases, `spring.data.mongodb.uri` should be used to provide all of the configuration, like this:
```properties
spring:
    data:
        mongodb:
            uri: mongodb://admin:1978@localhost:27017/test?authMechanism=SCRAM-SHA-1
```

* [Mongo Plugin](https://plugins.jetbrains.com/plugin/7141-mongo-plugin)
  ![1](tutorial/1.jpg)
  ![2](tutorial/2.jpg)
  ![3](tutorial/3.jpg)

* [Support for Query-by-Example](https://github.com/spring-projects/spring-data-examples/tree/master/jpa/query-by-example)
  ![4](tutorial/4.jpg)

* [Reactive Spring Data MongoDB](http://javasampleapproach.com/reactive-programming/angular-4-spring-webflux-spring-data-reactive-mongodb-example-full-reactive-angular-4-http-client-spring-boot-restapi-server)
  ![5](tutorial/5.png)

############################################################

Цели:
---

Клиент может подключаться к серверу с помощью JavaScript-а `WebSocket` **(** [ws://localhost/echo](ws://localhost/echo) **)** из веб-браузера для передачи аудио-потока (голосовой команды...)
* https://www.websocket.org/echo.html
* http://demos.kaazing.com/echo/index.html
