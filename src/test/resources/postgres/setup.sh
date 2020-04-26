#!/bin/bash

set -e

psql -c 'CREATE DATABASE autogeneral;'
psql -d autogeneral -c 'CREATE SCHEMA autogeneral;'
psql -c 'GRANT ALL PRIVILEGES ON DATABASE autogeneral TO postgres;'