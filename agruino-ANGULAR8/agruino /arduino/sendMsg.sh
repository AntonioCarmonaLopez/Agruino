#!/bin/bash

TOKEN="your telegram token"
ID="your id"
URL="https://api.telegram.org/bot$TOKEN/sendMessage"

curl -s -X POST $URL -d chat_id=$ID -d text=$1
