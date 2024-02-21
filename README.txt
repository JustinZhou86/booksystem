
1.local Run

Prerequites software: Node Js,  NPM, JAVA 17, MYSQL, Maven

1.1 import data to mysql server from init.sql file

1.2 change db password for spring.datasource.password key in config-server/src/resources/book-backend-dev.properties

1.3 maven build and run
config-server>mvn spring-boot:run
register-server>mvn spring-boot:run
gateway-server>mvn spring-boot:run
book-backend>mvn spring-boot:run
book-frontend>yarn dev


1.3 test the book system

open url :  http://localhost:3000/


2.Run in your Kubernetes cluster.

Prerequites package: helm software, bitnami-mysql helm chart.


2.2 Guide to build docker image, and push into docker deamon in your k8s node.

set the enviroment:  
DOCKER_HOST=tcp://[node-ip]:2375;  or DOCKER_HOST=tcp://[image-registry-ip]:2375;  
eg.DOCKER_HOST=tcp://192.168.17.72:2375;

try to uncomment the dockerfile-maven-plugin this block in all of your pom.xml

try mvn clean install to build the image and push to your image registry.


2.3 Guide used to config mysql pod.

helm repo add bitnami https://charts.bitnami.com/bitnami

helm install  mysql bitnami/mysql \
  --set primary.startupProbe.enabled=false \
  --set primary.readinessProbe.enabled=false \
  --set primary.livenessProbe.enabled=false

after above step, your will find the password in the result statement

create a mysql client in k8s, and execute the sql
kubectl run mysql-client --rm --tty -i --restart='Never' --image  docker.io/bitnami/mysql:8.0.33-debian-11-r17 --namespace default --env MYSQL_ROOT_PASSWORD=j6xHTsFs2p --command -- bash

mysql -h mysql.default.svc.cluster.local -uroot -pj6xHTsFs2p

now you will see. mysql> prompt window, and enter the sql from the init.sql file.


after the mysql client pod is created,  later you can also use the bellow command to go to mysql client, and try you mysql client prompt windows
kubectl exec -it mysql-client -- /bin/bash
mysql -h mysql.default.svc.cluster.local -uroot -pj6xHTsFs2p


2.4 delploy all your deployments, services in your kubernet cluster.

must execute in bellow order
kubectl apply -f config-server.yaml
kubectl apply -f register-server.yaml
kubectl apply -f gateway.yaml
kubectl apply -f book-backend.yaml
kubectl apply -f book-frontend.yaml

use kubectl get svc to find the nodeport expose by all of the service.



health Check URL in local:
register server
http://127.0.0.1:8070/

gateway
http://127.0.0.1:8072/actuator/gateway/routes

config-server
http://localhost:8071/register-server/dev
http://localhost:8071/gateway/dev