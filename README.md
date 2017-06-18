# spring-starter

This is the Spring server application by using spring @version 4.

# How to?

This spring server provides restful service for the Angular-Starter web application.

# Features

## Spring & Angular
This application is the back-end server by Spring. There is front-end web application 'Angular-Starter'.

## Spring Boot

## MVC

## Cross Origin Resource Sharing (CORS)
Implemented CORS to support 'Angular-Starter' web application.

## Restful service

## JPA

## MySQL

## Log Aspect
Implemented log aspect to intercept access of restful service.
Usage:
	@Loggable(level = LogLevel.INFO)
	public class UserController {}

## Exception Handler
Convert specific Exception to generic customized RestfulException.
