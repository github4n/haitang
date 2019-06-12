#!/bin/bash  

for i in `ls ./proto/*.proto`
    do    
      protoc  --proto_path=proto/ --java_out=./server $i
      echo gen server success $i
    done  


for i in `ls ./proto/*.proto`
    do    
      protoc  --proto_path=proto/ --descriptor_set_out=client/$(basename $i .proto).pb --include_imports $i
      echo gen client success $i
    done  

echo gen finish