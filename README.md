# Vaadin + Spring Boot + MongoDB + MariaDB MaxScale demo

[![License](https://img.shields.io/badge/License-MIT-blue.svg?style=plastic)](https://opensource.org/licenses/MIT)

This example shows how to connect a [Vaadin](https://vaadin.com) application to MongoDB databases. This file also shows how to use [MaxScale](https://mariadb.com/docs/ent/architecture/components/maxscale/use-cases) to connect the same application to a MariaDB backend using the NoSQL protocol without modifying the app.

# Preparing the database

You need MongoDB listening on port 27017 or a MariaDB database and a MaxScale instance.

## Setting up the database cluster using Docker

Clone the following repository which contains Docker files to set up MariaDB replication and MaxScale:

```
git clone https://github.com/alejandro-du/mariadb-docker-deployments.git
```

Build the images (don't use these in production environments):

```
cd mariadb-docker-deployments
docker build --file single-node/Dockerfile --tag alejandrodu/mariadb-single-node .
docker build --file primary/Dockerfile --tag alejandrodu/mariadb-primary .
docker build --file replica/Dockerfile --tag alejandrodu/mariadb-replica .
docker build --file maxscale/Dockerfile --tag alejandrodu/mariadb-maxscale .
```

Alternatively, you can use the **build.sh** script if you are on Linux.

Run the containers:

```
docker compose up -d
```

## Run the web application

Clone this repository:

```
git clone https://github.com/alejandro-du/mongodb-vaadin-demo.git
```

Build the Java web application using Maven:

```
cd mongodb-vaadin-demo
mvn package
```

Run the application:

```
java -jar target/mongodb-vaadin-demo.jar
```

Access the application in your browser at http://localhost:9090. Insert some data.

Access the MaxScale GUI at http://localhost:8989/ using the following credentials:

* Username: `admin`
* Password: `mariadb`

In the menu, go to the query editor and connect to the database using:

* Listener name: `query_router_listener`
* Username: `user`
* Password: `password`

Inspect the database and query the `student` table:

```sql
select * from student
```

Or use [MariaDB's JSON functions](https://mariadb.com/resources/blog/using-json-in-mariadb):

```sql
select
	json_value(doc, '$.firstName') as firstName,
	json_value(doc, '$.lastName') as lastName
from student
```

To shutdown the database cluster run:

```
docker compose down
```

Add `-v` to the above command if you want to remove the related Docker volumes as well (you'll lose all the configuration and data).
