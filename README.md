# UserPortal

# Pre-requisite
- Setup MySQL Workbench
- Setup Docker and Docker compose to local machine
- Setup maven and jdk-11

# Overview
This application exposes endpoints to add/search/list/update/delete
user profiles. The data is stored and fetched from MySQL DB.

# How to run the application:
- Start up docker on your machine
- Run mvn clean install (generate test documentation that can be found in target folder)
- Open terminal and below command to start up servers:
  docker-compose up --build --scale server={number of container instances}
- Open terminal and run command to view the list of servers and ports they're running on:
  docker ps -a 
- Test the application by hitting the endpoints, eg:
  http://localhost:{port}/digicert/users
- Verify the data in MySql userdb.user
