docker-compose -f ./adapter-api/docker-compose.yml down
gradle wrap
./gradlew bootJar
docker-compose -f ./adapter-api/docker-compose.yml up -d