docker-compose -f ./adapter-api/docker-compose.yml down
gradle wrap
./gradlew clean
./gradlew bootJar
docker-compose -f ./adapter-api/docker-compose.yml up -d