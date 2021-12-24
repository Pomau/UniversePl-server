sudo docker run -d -p 3307:3306 --name mysqldb -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=chat mysql

sudo docker network create spring-net

sudo docker network connect spring-net mysqldb

sudo docker build -t app .  

sudo docker run -p 9090:8080 --name app --net spring-net  -e MYSQL_HOST=mysqldb -e MYSQL_USER=root -e MYSQL_PASSWORD=root -e MYSQL_PORT=3306 app
