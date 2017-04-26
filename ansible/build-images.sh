#!/bin/bash
echo "build images"
cd ../uaa
./mvnw package -Pprod docker:build &
cd ../authorization-service
./mvnw package -Pprod docker:build &
cd ../gateway
./mvnw package -Pprod docker:build &
echo "wait"
wait
echo "push to hub"
docker tag uaa quangtien/uaa
docker tag authorization quangtien/authorization
docker tag gateway quangtien/gateway
docker push quangtien/uaa
docker push quangtien/authorization
docker push quangtien/gateway
echo "done"
