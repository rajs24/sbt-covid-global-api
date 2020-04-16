# Getting Started
# Spring Boot - Coronavirus COVID-19 API for Global Cases
* TThis project REST API allows to fetch global cases  details of confirmed, deaths, recovered. 

## Covid19 Global cases REST API details

* ##### GET All global cases
```
http://localhost:8081/api/v1/covid/global/all
```
* ##### GET global cases by Country
```
http://localhost:8081/api/v1/covid/global/all/{country}
```
Example Value:  
`all/US`  
`all/India`  
`all/Singapore`  
`all/Italy` 

* ##### GET Total confirmed cases and previous day confirmed cases
```
http://localhost:8081/api/v1/covid/global/confirm
```
Example Response Data:
```json
{
  "state": "",
  "country": "US",
  "totalCases": 636350,
  "totalPreviousDay": 28680
}
```
