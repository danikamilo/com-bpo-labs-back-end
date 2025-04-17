FROM nginx:alpine

COPY default.conf /etc/nginx/conf.d/default.conf
COPY proxy_params /etc/nginx/proxy_params
COPY .htpasswd /etc/nginx/.htpasswd
#Stelnet
RUN apk add --no-cache busybox-extras

EXPOSE 5050