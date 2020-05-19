# clientapi [![CircleCI](https://circleci.com/gh/sustral/clientapi.svg?style=shield&circle-token=8561dbb51f577f77810a11527982690952048322)](https://circleci.com/gh/sustral/clientapi) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=sustral_clientapi&metric=alert_status&token=745d3dd6882286c2aa97a539edb83e7e410d3c2e)](https://sonarcloud.io/dashboard?id=sustral_clientapi)

Quick REST API built on Spring, to be replaced ASAP.

Running the tests locally
-

1. Configure a MySQL database using Docker:

    1. Create a MySQL container. Don't use an existing container or storage directory for simplicity:<br/>
    <code>docker run --name mysql-docker-1 -e MYSQL_ROOT_PASSWORD=root_password -e MYSQL_DATABASE=test_db -e MYSQL_USER=mysql_user
    -e MYSQL_PASSWORD=mysql_password -p 3306:3306 -v [PATH_TO_STORAGE_DIRECTORY]:/var/lib/mysql -d mysql:8</code>

    2. Copy the provided schema and data files to the container:<br/>
    <code>docker cp [PATH_TO_REPO]/src/test/resources/schema.sql mysql-docker-1:/schema.sql</code><br/>
    <code>docker cp [PATH_TO_REPO]/src/test/resources/data.sql mysql-docker-1:/data.sql</code>
    
    3. Execute the files on test_db:
        
        1. Start a bash shell in the container:<br/>
        <code>docker exec -it mysql-docker-1 /bin/bash</code>
        
        2. Execute the schema file from the aforementioned shell; this creates the DB schema:<br/>
        <code>mysql -u mysql_user -pmysql_password test_db < schema.sql</code>
        
        3. Execute the data file from the aforementioned shell; this populates the DB:<br/>
        <code>mysql -u mysql_user -pmysql_password test_db < data.sql</code>
        

2. Fill in src/test/resources/application.properties from the company keys stored in S3.

    1. You can fill in the MySQL and JPA properties according to your database config (the default should be correct).
    
    2. You can optionally create your own S3 bucket and associated user.
    You will have to add images (any images) corresponding to the files in data.sql or some tests will fail. 
    
3. Build the app and run the tests from inside the repo folder:<br/>
<code>./gradlew build</code>

Notes
-

* The data models and services were intentionally denormalized with the goal of soon breaking each into its own microservice.
  Inheritance in JPA models for the sake of eliminating duplicated getters and setters is not appropriate. This is doubly true when
  any of these entities could be altered at any time.
