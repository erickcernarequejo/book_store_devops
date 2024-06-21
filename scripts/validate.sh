#!/bin/bash

# Check if pods are running
kubectl get pods -l app=book-store

# Add custom validation logic if necessary
POD_STATUS=$(kubectl get pods -l app=book-store -o jsonpath='{.items[*].status.phase}')
if [[ "$POD_STATUS" != *"Running"* ]]; then
  echo "Some pods are not running"
  exit 1
fi

echo "All pods are running"