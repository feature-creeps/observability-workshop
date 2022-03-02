FROM alpine:latest AS vegeta
WORKDIR /root/

RUN apk add wget tar \
    && wget -q https://github.com/tsenart/vegeta/releases/download/cli%2Fv12.4.0/vegeta-12.4.0-linux-amd64.tar.gz \
    && tar xzvf vegeta-12.4.0-linux-amd64.tar.gz \
    && chmod a+x vegeta \
    && mv vegeta /usr/local/bin

FROM alpine:latest AS upload_gen
WORKDIR /root/

RUN apk add wget tar  \
    && wget -q https://github.com/feature-creeps/upload-traffic-gen/releases/download/v0.4.0/upload-traffic-gen_0.4.0_Linux_x86_64.tar.gz \
    && tar xzvf upload-traffic-gen_0.4.0_Linux_x86_64.tar.gz \
    && chmod a+x upload-traffic-gen \
    && mv upload-traffic-gen /usr/local/bin

FROM alpine:latest AS transform_gen
WORKDIR /root/

RUN apk add wget tar  \
    && wget -q https://github.com/feature-creeps/transform-traffic-gen/releases/download/v0.2.0/transform-traffic-gen_0.2.0_Linux_x86_64.tar.gz \
    && tar xzvf transform-traffic-gen_0.2.0_Linux_x86_64.tar.gz \
    && chmod a+x transform-traffic-gen \
    && mv transform-traffic-gen /usr/local/bin


FROM alpine:latest

RUN apk add --no-cache curl

COPY --from=vegeta /usr/local/bin/vegeta /usr/local/bin/vegeta
COPY --from=upload_gen /usr/local/bin/upload-traffic-gen /usr/local/bin/upload-traffic-gen
COPY --from=transform_gen /usr/local/bin/transform-traffic-gen /usr/local/bin/transform-traffic-gen

COPY images /images

COPY docker-entrypoint.sh /usr/local/bin/
ENTRYPOINT ["/usr/local/bin/docker-entrypoint.sh"]
