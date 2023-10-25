#!/bin/bash

if ! type java > /dev/null 2>&1; then
    sudo amazon-linux-extras enable corretto17
    sudo yum install -y java-17-amazon-corretto
else
    JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}')
    if [[ "$JAVA_VERSION" < "17" ]]; then
        sudo amazon-linux-extras enable corretto17
        sudo yum install -y java-17-amazon-corretto
    fi
fi