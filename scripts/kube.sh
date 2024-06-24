#!/bin/bash

cd /opt/libros

eks_check=$(aws eks describe-cluster --name "k8s-cluster" --region "us-east-1" 2>&1)

if [[ $eks_check == *"ResourceNotFoundException"* ]]; then
    echo "The cluster does not exist. You can create it."
    eksctl create cluster -f k8s-cluster.yml
else
    echo "The cluster already exists."
    eksctl create nodegroup -f k8s-cluster.yml
fi

kubectl apply -f deployment.yml


