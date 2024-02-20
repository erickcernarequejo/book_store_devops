#!/bin/bash
cd /opt/libros
sudo nohup java -jar -Dserver.port=80 libros-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &