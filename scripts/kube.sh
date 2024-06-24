#!/bin/bash

cd /opt/libros

eks_check=$(aws eks describe-cluster --name "k8s-cluster" --region "us-east-1" 2>&1)

if [[ $eks_check == *"ResourceNotFoundException"* ]]; then
    echo "El clúster no existe. Puedes crearlo."
    eksctl create cluster -f k8s-cluster.yml
else
    echo "El clúster ya existe."
    eksctl create nodegroup -f k8s-cluster.yml
fi

kubectl apply -f deployment.yml


