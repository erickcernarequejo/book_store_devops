#!/bin/bash

cd /opt/libros

eksctl create cluster -f k8s-cluster.yml

# Delete existing pods
kubectl apply -f deployment.yaml