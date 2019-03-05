FROM appropriate/curl as build


# https://raw.githubusercontent.com/xellsys/braindump/master/microservices/complex-systems.md
# https://raw.githubusercontent.com/xellsys/braindump/master/microservices/monolith-first.md
RUN curl -o text.md https://raw.githubusercontent.com/xellsys/braindump/master/microservices/monolith-first.md

RUN echo "stuff"

FROM busybox as final

RUN echo "is this cached?"

COPY --from=0 text.md .

RUN head -n 1 text.md

ENTRYPOINT head text.md