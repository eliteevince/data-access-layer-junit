# DAL Persistence layer in java.

**Pre Requirement**
- MySQL JDBC connector
- JDK > v1.7

## Introduction
DAL Persistence layer is JAVA based program. to handle many persistence layer oprations like table creation, updation, record insertion, record deletion, record updation, read record, generate next sequence number etc.
In this program we are using MySQL tool as database structure.

## Features
- Create table dynamically
- Update table dynamically
- Insert data into table dynamically
- Update data into table dynamically
- Read data from table dynamically
- Delete data from table dynamically
- Fetch next sequence number genrator

## Step to setup
- Download DAL Persistence layer program from repository.
- Extract downloaded file and import into eclips or netbeans anything what you like.
- After successfully imported project, you may found two packages com.radix.dal and com.radix.dal.mysql in which com.radix.dal package containes all utility files and persistence factory and com.radix.dal.mysql package is containes mysql database connectivity and Persistence layer.
- To test this program you need to configure mysql connector library. that you may found mysql-connector-java-8.0.18.jar in root folder of our repository. bind this jar with our program.
- Now need to change mysql configuration like path, username and password in MySQLPersistenceManager constructor and create database according to your convenience.
- After all configuration is done Dal Persistence Layer is ready to test.

## Test program
- We are using JUnit v4.10 framwork here for test cases.
- The junit-4.10 library is also kept in root folder with name junit-4.10.jar.
- The test case file with named DalMainTest.java is also kept in the com.radix.dal package.
- To run test case file in eclips right click on DalMainTest.java file and select Run As then select JUnit Test.
- Now it will be run our test cases and you will able see test case results.
