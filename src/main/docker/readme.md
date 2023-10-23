PUT .JAR ARCHIVE INTO THIS FOLDER


separate docker compose for this service and its db only

docker network ls

docker network create -d bridge my_bridge_network
docker network rm my_bridge_network

mvnw clean package -DskipTests
docker-compose up
docker-compose down
docker rmi resourceservice:latest
docker rmi songservice:latest
docker rmi postgres:13.1-alpine
docker-compose up

docker network connect my_bridge_network appr
docker network connect my_bridge_network dbr
docker network connect my_bridge_network apps
docker network connect my_bridge_network dbs