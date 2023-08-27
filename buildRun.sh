#!/bin/sh

# compiling
javac -cp ./src/ -d ./build **/*.java

# running
mainClass=control.Main

if [ $# -ne 0 ]
  then
    mainClass=$1
fi
java -cp ./build/ $mainClass