# potassium

A barebones test framework with testng, selenium, and restassured

To start, clone this repository then run
```
mvn test -Dtest=SampleApiTest
mvn test -Dtest=SampleUiTest
```

To build docker image (don't forget the ending dot)
```
docker build -t biphenyl .
```

To download latest chrome driver
```
curl -O http://chromedriver.storage.googleapis.com/`curl -sS chromedriver.storage.googleapis.com/LATEST_RELEASE`/chromedriver_linux64.zip
curl -O http://chromedriver.storage.googleapis.com/`curl -sS chromedriver.storage.googleapis.com/LATEST_RELEASE`/chromedriver_mac64.zip
```
