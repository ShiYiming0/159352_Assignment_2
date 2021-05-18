# 159352_Assignment_2
## Author: Yiming Shi ID: 19023253

### There are two projects in the folder.
### "assignment2_server_19023253" is the java web application,
### "assignment2_test_19023253" is a java application for test.
### fastjson-1.2.47 and httpcomponents-client-4.5.13 is needed, they are in the project folder.

# Game:
### Please open the projects in netbeans12, run the web application project with JDK11 and Tomcat8.5.

* This will open the blackjack game in your browser(I recommend Chrome), press "start new game" to start a game. 
* "hit" can get a card, "stand" can stop getting card and let dealer gat cards, then decide who is the winner, 
* press button "state" can display the score, press button "stats" can display number of games played and winning rate, "possible moves" can show possible moves. 
* After pressing "stand", this round of game is over, you can choose to start a new game.

### The winning rate and number of games played data will be stored in file "stats.json" generated in /bin directory under your tomcat folder.

# Test:
### JUnit4 test files are in the test package in java application "assignment2_test_19023253"
### Please run the web application before running tests.

# Deploy
### Under the java web directory, there is a folder "docker", which stores a "jack.war" file and Dockerfile for deployment.
### In CMD tool, change your directory to /docker, and build the image:
`docker image build -t blackjack:latest .`
### Run the container:
`docker container run -d --name blackjack -p 8080:8080 blackjack:latest`
### Then you can access the blackjack game in http://localhost:8080/jack/
 
