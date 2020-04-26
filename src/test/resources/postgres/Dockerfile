FROM postgres:11.4

RUN mkdir /sql

COPY ./setup.sh /docker-entrypoint-initdb.d

EXPOSE 5432
