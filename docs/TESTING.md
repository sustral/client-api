# Local Testing

## Tests

Prerequisites:
* Install OpenJDK 11: [OpenJDK Install Instructions](https://openjdk.java.net/install/)
* Install Docker: [Docker Install Instructions](https://docs.docker.com/get-docker/)

1. Configure a MySQL database using Docker:

    1. Create a MySQL container:
    
        `docker run --name [CONTAINER_NAME] -e MYSQL_ROOT_PASSWORD=root_password -e MYSQL_DATABASE=test_db -e MYSQL_USER=mysql_user
        -e MYSQL_PASSWORD=mysql_password -p 3306:3306 -v [PATH_TO_STORAGE_DIRECTORY]:/var/lib/mysql -d mysql:8`
     
        * CONTAINER_NAME can be any string
        * PATH_TO_STORAGE_DIRECTORY can be any writable path
        * Make sure port 3306 is open on your machine or use another port
        * It will be faster and less painful to use a brand-new container and storage directory as shown above as 
        opposed to using an old mysql container, vm, or otherwise

    2. Copy the provided schema, data, and clear files to the container:
    
        `docker cp [PATH_TO_REPO]/src/test/resources/schema.sql [CONTAINER_NAME]:/schema.sql`
        
        `docker cp [PATH_TO_REPO]/src/test/resources/data.sql [CONTAINER_NAME]:/data.sql`
        
        `docker cp [PATH_TO_REPO]/src/test/resources/clear.sql [CONTAINER_NAME]:/data.sql`
    
    3. Execute the files on test_db:
        
        1. Start a bash shell in the container:
        
            `docker exec -it [CONTAINER_NAME] /bin/bash`
        
        2. Execute the schema file from the aforementioned shell; this creates the DB schema:
        
            `mysql -u mysql_user -pmysql_password test_db < schema.sql`
        
        3. Execute the data file from the aforementioned shell; this populates the DB:
        
            `mysql -u mysql_user -pmysql_password test_db < data.sql`
        
        4. STOP! The third script we copied over (clear.sql) is for deleting all the data while preserving the schema.
        Use this between tests (followed by rerunning the data script) and before running the application as described 
        in the next section:
        
            `mysql -u mysql_user -pmysql_password test_db < clear.sql`
        
2. Fill in `src/test/resources/application.properties` from the company keys stored in S3.

    1. You can fill in the MySQL and JPA properties according to your database config (the default should be correct).
    
    2. Optionally, you can create your own S3 bucket and associated user.
    You will have to add images (any images) corresponding to the files in data.sql or some tests will fail.
    You can also just fill the values in with arbitrary strings just so that the context loads, but the AWS and 
    object tests will fail.
    
    3. You can fill out the SendGrid and Email Service sections with arbitrary strings, but those
    related tests will fail. You must fill those with something though, or the context will fail to load.
    
    4. Leave the Key sections as is, but you can change them if you wish to do something specific.
    
3. Build the app and run the tests from inside the repo folder:

    `./gradlew build`

## Demo

This section assumes that you completed the above section regarding running the tests locally.

1. Clear the MySQL database:
    
    1. Start a bash shell in the container if you haven't already:
    
        `docker exec -it [CONTAINER_NAME] /bin/bash`
    
    2. Clear the pre-existing data while maintaining the schema:
    
        `mysql -u mysql_user -pmysql_password test_db < clear.sql`
    
2. Fill in `src/main/resources/application.properties` if any changes are needed (or just edit the test configuration,
we will instruct the jar file to select a specific config file later). The same configuration as above should be fine
for most purposes. Keep in mind that some functions will not work with fake keys, but never use production keys.
You can safely ignore the rollbar api key while running locally; it will fail silently.

3. While in the repo folder, run the application:

    `java -jar build/libs/clientapi-[VERSION].jar --spring.config.location=file://[ABSOLUTE_PATH_TO_CONFIG_FILE]`

    * Make sure port 8080 is open
