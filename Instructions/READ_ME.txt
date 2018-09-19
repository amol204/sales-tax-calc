# Time spent: 5 hrs
# What I would have liked to add if I had more time?
	1) Logging using some logging framework such as logback.
	2) The request, responses could be saved in database and used for reporting purposes.
	3) A simple web page to make the requests.

# How to Run the program?
	# From eclipse:
		1) Start Main.java .This will start the webservice (available at http://localhost:8080/calculate ).
		2) Program is now running.
	# Building and Running the JAR:
		1) Right-click on project and do a Maven install.
		2) go to the 'target' directory in your workspace from command prompt and type below command:
			java -jar sales-tax-calc-0.0.1.jar
		3) Program is now running.
		
# How to make requests to the program?
	2) Use the Postman plugin (available for Chrome browser) or something similar to hit the POST request to webservice.
	3) Please find sample requests in Instructions/sample_requests folder.
	4) postman_request.png depicts the point to be noted while making the request.
		These points are as follows:
		a) Service URL is http://localhost:8080/calculate
		b) Request method has to be POST
		c) Request should be made with Content-Type = application/json
		
# Configuration files:
Following configuration files can be found in src/main/resources directory.
	1) keywords.properties
	2) taxConfig.properties
After the build, the configuration files will be available in target/config directory