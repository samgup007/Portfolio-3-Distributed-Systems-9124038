### Starting the Demo Application

### Performing the integration test

Run `mvn verify` to initiate the integration tests through the terminal. 
This command will build a Docker image and start a container for the application. 
The integration tests will then be executed, and once completed, the container will be stopped and removed.

### Starting the Portfolio execution

Run the `main` method in the `Start` class to start the embedded Tomcat server and deploy the application. 
The application will be accessible at http://localhost:8080/test/api. 
You can test the application using the provided JSON file, 
which contains a Postman collection covering most of the required test cases.

### For manual testing 

Refer to the `src/main/docker/Dockerfile` file as a guide to create a Docker image for the application. 
First, build the WAR file of the demo application using `mvn package`. Next, run `$> docker build .` 
(make sure to include the period after `build`) to create the Docker image. 
Finally, start the container by executing `$> docker run -p 8080:8080 --rm <IMAGE_ID>`.




"# Porfolio-3-Samarth" 
