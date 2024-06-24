#!/bin/bash

#Set up credentials for AWS EKS
aws eks --region us-east-1 update-kubeconfig --name k8s-cluster

# Wait 30 seconds for the pods to rise
echo "Waiting 30 seconds for pods to rise......"
sleep 30

# Check if pods are running
kubectl get pods -l app=book-store

# Add custom validation logic if necessary
POD_STATUS=$(kubectl get pods -l app=book-store -o jsonpath='{.items[*].status.phase}')
if [[ "$POD_STATUS" != *"Running"* ]]; then
  echo "Some pods are not running"
  exit 1
fi

echo "All pods are running"