@echo off

REM Wrapper script for Gradle (Windows)
IF EXIST gradlew.bat (
  gradlew.bat %*
) ELSE (
  ECHO gradlew.bat not found. Please run 'gradle wrapper' from Android Studio.
  EXIT /B 1
)
