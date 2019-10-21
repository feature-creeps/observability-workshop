FROM fluent/fluentd:v1.7.3-debian-1.0

USER root

RUN fluent-gem install fluent-plugin-elasticsearch
RUN fluent-gem install fluent-plugin-prometheus
RUN buildDeps="sudo make gcc g++ libc-dev ruby-dev" \
 && apt-get update \
 && apt-get install -y --no-install-recommends $buildDeps \
 && sudo gem install \
        fluent-plugin-honeycomb \
 && sudo gem sources --clear-all \
 && SUDO_FORCE_REMOVE=yes \
    apt-get purge -y --auto-remove \
                  -o APT::AutoRemove::RecommendsImportant=false \
                  $buildDeps \
 && rm -rf /var/lib/apt/lists/* \
 && rm -rf /tmp/* /var/tmp/* /usr/lib/ruby/gems/*/cache/*.gem
COPY ./fluent.conf /fluentd/etc/fluent.conf

USER fluent