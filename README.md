# go-train-schedule-api

running on local port 8081
endpoint examples:
localhost:8081/schedule
localhost:8081/schedule/Lakeshore
localhost:8081/schedule/Lakeshore?departure=04:00pm
localhost:8081/schedule/Lakeshore?departure=1600

User Pattern to match the regex patern for 24/12-hour format
Used SimpleDateFormat to format and match the departure time(Intend to use DateTimeFormatter but parsing was giving a trouble)

## Installation

`mvn clean install`

## Quickstart

`mvn spring-boot:run`

### Testing

Run all tests:
`mvn test`