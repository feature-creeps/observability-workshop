FROM docker.elastic.co/logstash/logstash-oss:6.1.1

COPY ./conf/logstash.yml /usr/share/logstash/config/logstash.yml
COPY ./pipeline/logstash.conf /usr/share/logstash/pipeline/logstash.conf