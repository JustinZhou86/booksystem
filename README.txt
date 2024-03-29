
1.local Run

Prerequites software: Node Js,  NPM, JAVA 17, MYSQL, Maven

1.1 import data to mysql server from init.sql file

1.2 change db password for spring.datasource.password key in config-server/src/resources/book-backend-dev.properties

1.3 maven build and run
config-server>mvn spring-boot:run
register-server>mvn spring-boot:run
gateway-server>mvn spring-boot:run
book-backend>mvn spring-boot:run
book-frontend>yarn run start


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


config-server>mvn clean install
register-server>mvn clean install
gateway-server>mvn clean install
book-backend>mvn clean install



2.2.1 use docker to build frontend

edit book-frontend/src/Config.js
export const API_PREFIX = "http://localhost:8072/book-proxy"; 

 ===> change to http://192.168.17.71:31200/book-proxy, this  url base on your actuall k8s node's IP and node port for book-frontend.

because our react ui is hosted on nginx, and it will retrun html & js code to browser side, and the browser will execute the js code.  

if the api endpoint can not accessed by your local browser, your react ui will not wrok.

so here the proxy url, we must use the public IP to the browser, not the internal k8s service url. eg.(http://justin-gateway-server.default.svc.cluster.local:8072/book-proxy)


#build the binary for react app.
book-frontend>yarn run build        

Option A:  build image with the artifact built in your local  book-frontend/build folder.
use build or buildx is also ok, please change the ip to your docker server 's ip address.
docker -H tcp://192.168.17.72:2375 build -t book/book-frontend:0.0.1-SNAPSHOT .

docker -H tcp://192.168.17.72:2375 buildx build -t book/book-frontend:0.0.1-SNAPSHOT .

Option B:  react build and image build all in docker builder container.
if you want to build you react code inside the node builder container. you can try multiple stage build

docker -H tcp://192.168.17.72:2375  build -t book/book-frontend:0.0.1-SNAPSHOT . -f Dockerfile-mul-stage


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

open k8s-manifest folder, change the service url in enviroment section in all ymls to the correct url

          env:
          - name: SPRING_CLOUD_CONFIG_URI
            value: "http://justin-config-server.default.svc.cluster.local:8071"

see the bellow pattern.  
the url base on your actually k8s namespace, k8s cluster name.
http://justin-config-server.[namespace].svc.cluster.[clustername]:8071


must execute in bellow order
kubectl apply -f config-server.yaml
kubectl apply -f register-server.yaml
kubectl apply -f gateway.yaml
kubectl apply -f book-backend.yaml
kubectl apply -f book-frontend.yaml

use kubectl get svc to find the nodeport expose by all of the service.



3.Health Check.

3.1 health Check URL in local:
register server
http://127.0.0.1:8070/

gateway
http://127.0.0.1:8072/actuator/gateway/routes

config-server
http://localhost:8071/register-server/dev
http://localhost:8071/gateway/dev

frontend
http://localhost:3000/

3.2 health Check URL for apps deployed on K8s cluster
register server
http://192.168.17.72:31100/

gateway
http://192.168.17.72:31200/actuator/gateway/routes

config-server
http://192.168.17.72:31000/register-server/dev
http://192.168.17.72:31000/gateway/dev

frontend
http://192.168.17.72:31400/
