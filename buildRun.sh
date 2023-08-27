#!/bin/sh

# compiling
javac -cp ./src/ -d ./build $(find . -name '*.java')

# running
mainClass=control.Main

if [ $# -ne 0 ]
  then
    mainClass=$1
fi
java -cp ./build/ $mainClass