selenium-java-record-vaadin
===========================

The sample project uses Java Selenium JUnit and Testcontainer with Chrome browser. Recording the VNC Testcontainer session as video (FLV | MP4) is skipped because of bad ffmpeg performance on small headless VPS servers.

Selenium TestCase:
- Open netcup Forum URL
- Click the search button
- Enter "Selenium" and wait
- Verify some text and results

Preconditions
-------------
A small VPS or server with Java, Maven and Docker setup.

Run the test
------------
To run all tests - currently just the one described above:

`mvn test`
