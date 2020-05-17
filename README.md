# clientapi [![CircleCI](https://circleci.com/gh/sustral/clientapi.svg?style=shield&circle-token=8561dbb51f577f77810a11527982690952048322)](https://circleci.com/gh/sustral/clientapi) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=sustral_clientapi&metric=alert_status&token=745d3dd6882286c2aa97a539edb83e7e410d3c2e)](https://sonarcloud.io/dashboard?id=sustral_clientapi)

Quick REST API built on Spring, to be replaced ASAP.

The data models and services were intentionally denormalized with the goal of soon breaking each into its own microservice.
Inheritance in JPA models for the sake of eliminating duplicated getters and setters is not appropriate. This is doubly true when
any of these entities could be altered at any time.

For local testing, create a MySQL container on Docker using the following command (replace [PATH_TO_STORAGE_DIRECTORY]):<br/>
<code>docker run --name mysql-docker-1 -e MYSQL_ROOT_PASSWORD=root_password -e MYSQL_DATABASE=test_db -e MYSQL_USER=mysql_user
-e MYSQL_PASSWORD=mysql_password -p 3306:3306 -v [PATH_TO_STORAGE_DIRECTORY]:/var/lib/mysql -d mysql:8</code>
