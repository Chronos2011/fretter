@echo off

for /f "delims=" %%i in ('dir target\fretter-*-jar-with-dependencies.jar /B 2^>nul') do (set "JAR=%%i")
java -jar target\\"%JAR%" %*
