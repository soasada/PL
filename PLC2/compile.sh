#!/bin/bash
 
cd src
cup PLC.cup
jflex -d . PLC.flex
javac -cp .:../../lib/java-cup-11b-runtime.jar -d ../bin/ *.java
