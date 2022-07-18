To enable Debug mode

Edit svc file and the below 

- name: debgu
    port: 5005
    nodePort: 30007
    protocol: TCP
    targetPort: 5005

	
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

Alternatively, you can run the app without packaging it using -

```bash
mvn spring-boot:run
```

The backend server will start at <http://localhost:8080>.