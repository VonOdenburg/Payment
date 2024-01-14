# Payments Demo App

### Basic request
The following request are the basic requests to complete the tasl:

* http://localhost:8080/payments - get the list of payments
* http://localhost:8080/stat/average?cuurency=HUF - get average value of payments for a given currency
* http://localhost:8080//stat/monthly-stat?cuurency=HUF&year=2022 - get monthly statistics (average, number of transactions) for a given currency / year by months

### Restful API
There is a restful API provided by Spring Data Rest with HAL support

* http://localhost:8080/api - the API

When the above host is opened in a browser a HAL exporter is loaded to browse the API easier.

