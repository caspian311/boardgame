@echo off
setlocal ENABLEDELAYEDEXPANSION

set JAVA_HOME=%cd%\jre
set JAVA_EXEC="%JAVA_HOME%\bin\java.exe"

rem set JAVA_OPTS=-Xms128m -Xmx256m

FOR /R .\lib %%G IN (*.jar) DO set CP=!CP!;"%%G"

set MAINCLASS=net.todd.games.boardgame.Main

set EXEC=%JAVA_EXEC% %JAVA_OPTS% -cp %CP% %MAINCLASS% %*

%EXEC%
