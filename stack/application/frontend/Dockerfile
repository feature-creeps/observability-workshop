FROM node:18 AS builder

# set working directory
RUN mkdir /usr/src/app
WORKDIR /usr/src/app

# add `/usr/src/app/node_modules/.bin` to $PATH
ENV PATH /usr/src/app/node_modules/.bin:$PATH

# install and cache app dependencies
COPY package.json /usr/src/app/package.json
RUN npm install
RUN npm install -g @angular/cli@15

# add app
COPY src /usr/src/app/src
COPY angular.json /usr/src/app/
COPY tsconfig.json /usr/src/app/

RUN ng build --configuration production


FROM opentracing/nginx-opentracing

## Remove default nginx website
RUN rm -rf /usr/share/nginx/html/*
RUN rm /etc/nginx/conf.d/default.conf

## From ‘builder’ stage copy over the artifacts in dist folder to default nginx public folder
COPY --from=builder /usr/src/app/dist/frontend /usr/share/nginx/html

COPY nginx/nginx.conf /etc/nginx/nginx.conf
COPY nginx/dima.conf.template /etc/nginx/templates/dima.conf.template
COPY nginx/zipkin-config.json /etc/

CMD ["nginx", "-g", "daemon off;"]
