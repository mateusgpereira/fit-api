#!/bin/bash
openssl genrsa -out rsaPrivateKey.pem 2048

openssl rsa -pubout -in rsaPrivateKey.pem -out publickey.pem

openssl pkcs8 -topk8 -nocrypt -inform pem -in rsaPrivateKey.pem -outform pem -out privatekey.pem

rm rsaPrivateKey.pem
