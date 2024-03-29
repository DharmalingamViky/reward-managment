## Overview

In this application we are storing the transaction details of the customer based on the unique Id and each every transaction 
will create a new entry into database. (no sql database). and we are not performing any aggregation details.

when ever we fetch customer details we will fetch all transaction done by the customer and calculate the reward point based on 
the cumulative amount .


## Debugging


In kubernetes deployment To enable debug mode please update the svc file with below configuration


``` 
 name: debug
    port: 5005
    nodePort: 30007
    protocol: TCP
    targetPort: 5005
    
  ```
    


Then restart the service and then create debug configuration in intellj


# Spring boot and MongoDB REST API - Reward management on purchase

The application demonstrates the REST API using Spring boot and MongoDB. The application demonstrates all the database operations like Create, Read, Update and Delete.

## Tools and Technologies used

* Spring boot 2.5.4
* MongoDB
* Intellj
* Java1.8


## Step to install

1. **Clone the application**

```bash
git clone https://github.com/DharmalingamViky/reward-managment.git
```

2. **Build and run the backend app using maven**

```bash
cd reward-managment
mvn package
java -jar target/reward-api-1.0.0-SNAPSHOT.jar
```

3. **Alternatively, you can run the app without packaging it using**

```bash
mvn spring-boot:run
```

The backend server will start at <http://localhost:9090>.


## Prerequisite:

Install mongo db and update the application.yaml with right information

	dpPort: 37020
	dpIp : localhost
	dbUserName: Admin2
	dbpassword: Admin2
	database: test

## To build Docker using mvn
* cd docker
* mvn clean install
* it will build docker

## To build docker using manual
* docker build -t rewardmanagment:latest .

## start the docker
* docker run -p 9090:9090 rewardmanagment:latest

## Schema

![schema](doc/image/db/database-schema.png)


## Sample postman request

Here we have attached some sample screenshot for reference.

![get-request](doc/image/postman/get-request.png)

![pos-trequest](doc/image/postman/post-request.png)

## Api collection

[**download-api**] (https://github.com/DharmalingamViky/reward-managment/blob/main/doc/api-collection/Reward-Managment.postman_collection.json)
 

## Testing:

To do Testing through postman I have provide sample inputs

![unitest](doc/image/unit-test-result.png)

* Post : http://localhost:9091/purchase/v1/reward

* Sample Json :

	 {
	  "customerType":"NORMAL",
	 "phoneNumber":"9524435880",
	 "customerName":"dharma",
	 "amountOfTransaction":120,
	 "transactionTime":"1658574199000"
	 }
 
 
	 {
	 "customerType":"NORMAL",
	 "phoneNumber":"9524435880",
	 "amountOfTransaction":5000,
	 "transactionTime":"1657359210000"
	 
	 }

	 {
	 "customerType":"NORMAL",
	 "phoneNumber":"9524435880",
	 "amountOfTransaction":5000,
	 "transactionTime":"1657445610000"
	 
	 }
 
 
 * GET http://localhost:9091/purchase/v1/reward/9524435880
 
 * Sample output for GET
 
	``` class RewardHistory {
		rewardPoint: class RewardPoint {
			id: 9524435880
			customerType: NORMAL
			customerName: dharma
			phoneNumber: 9524435880
			summaryOfReward: [class Summary {
				startTime: 1655983368959
				endTime: 1658575368959
				totalPurchase: 15600
				reward: 31100
				}, class Summary {
				startTime: null
				endTime: null
				totalPurchase: null
				reward: null
				}, class Summary {
				startTime: null
				endTime: null
				totalPurchase: null
				reward: null
				}
			]
		}
	}```
 

 
## Document from mongo collection:
 
	 {
		"_id" : ObjectId("62dbc2185409bf46f18b49a2"),
		"customerType" : "NORMAL",
		"phoneNumber" : "9524435880",
		"TotalAmountOfPurchase" : NumberLong(5000),
		"transactionTime" : NumberLong(1658568810)
	}