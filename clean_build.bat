@echo off
echo Cleaning Android build files...

REM Gradle 데몬 중지
call gradlew.bat --stop

REM Java 프로세스 종료
taskkill /f /im java.exe 2>nul

REM 잠시 대기
timeout /t 2 /nobreak >nul

REM 빌드 디렉토리 삭제
if exist "build" rmdir /s /q "build" 2>nul
if exist "build_new" rmdir /s /q "build_new" 2>nul
if exist "build_output" rmdir /s /q "build_output" 2>nul
if exist "build_final" rmdir /s /q "build_final" 2>nul
if exist "app\build" rmdir /s /q "app\build" 2>nul

REM Gradle 캐시 정리
if exist ".gradle" rmdir /s /q ".gradle" 2>nul

echo Build cleanup completed!
echo You can now run your app in Android Studio.
pause
