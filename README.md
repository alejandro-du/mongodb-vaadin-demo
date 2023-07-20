# Vaadin + Spring Boot + MongoDB + MariaDB MaxScale demo

[![License](https://img.shields.io/badge/License-MIT-blue.svg?style=plastic)](https://opensource.org/licenses/MIT)

This example shows how to connect a [Vaadin](https://vaadin.com) application to MongoDB databases. This file also shows how to use [MaxScale](https://mariadb.com/docs/ent/architecture/components/maxscale/use-cases) to connect the same application to a MariaDB backend using the NoSQL protocol without modifying the app.

# Preparing the database

You need MongoDB listening on port 27017 or a MariaDB database and a MaxScale instance with a Listener using the `nosqlprotocol` protocol.

## Setting up the database cluster using Docker

Clone this repository:

```
git clone https://github.com/alejandro-du/mongodb-vaadin-demo.git
```

Spin up a MariaDB database cluster with one primary node and two replicas plus a database proxy (MaxScale):

```
cd mongodb-vaadin-demo
docker compose up -d
```

Create a NoSQL listener in MaxScale:

```
docker exec -it mongodb-vaadin-demo-maxscale-1 maxctrl create listener query_router_service nosql_listener 27017 protocol=nosqlprotocol 'nosqlprotocol={"user":"user", "password":"Password123!"}'
```

## Run the web application

Build the Java web application using Maven:

```
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
    json_value(doc, '$.lastName') as lastName,
    id as regNumber
from student
```

Or if you want to join data with other tables in the database:

```sql
select s.*, st.id regNumber
from student st,
    json_table(st.doc, '$'
        columns(
            firstName varchar(255) path '$.firstName',
            lastName varchar(255) path '$.lastName'
        )
    ) as s
```

To shutdown the database cluster run:

```
docker compose down
```

Add `-v` to the above command if you want to remove the related Docker volumes as well (you'll lose all the configuration and data).
