#!/usr/bin/env bash

ls
mvn verify
mvn compile package
mvn install