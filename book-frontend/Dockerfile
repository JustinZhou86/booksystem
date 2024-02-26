FROM nginx:latest

LABEL maintainer="Justin Zhou <zhum11@126.com>"

WORKDIR application

ARG APP_FILES=build/

#copy the static compiled react app js, html to nginx static file folder.
COPY ${APP_FILES} /usr/share/nginx/html