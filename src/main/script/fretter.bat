@echo off

for /f "delims=" %%i in ('dir fretter-*-jar-with-dependencies.jar /B 2^>nul') do (set "JAR=%%i")
java -jar "%JAR%" %*
