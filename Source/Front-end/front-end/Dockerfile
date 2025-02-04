
FROM node:latest as build
LABEL authors="nicol"

WORKDIR /usr/local/app
COPY ./ /usr/local/app/
RUN npm install
RUN npm run build

FROM nginx:latest
COPY --from=build /usr/local/app/dist/front-end /usr/share/nginx/html
COPY /nginx-custom.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
