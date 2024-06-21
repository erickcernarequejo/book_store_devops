#!/bin/bash

find /opt/libros -name k8s-cluster.yml
ls -l /opt/libros

eksctl create cluster -f k8s-cluster.yml

# Delete existing pods
kubectl apply -f deployment.yaml