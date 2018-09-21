echo off
REM If we're in build directory, switch to parent
set temp=%cd%
if "%temp:~-5%" == "build" (
	cd ..
	set changed=1
) else (
	set changed=0
)
REM make required directories
if not exist bin\ ( mkdir bin ) 
if not exist testbin\ ( mkdir testbin )
if not exist exbin\ ( mkdir exbin )
if not exist lib\ ( mkdir lib )

REM compile sourcecode of library
dir /s /B "src\*.java" > sources.txt
echo Compiling source of library
javac -d bin @sources.txt
if errorlevel 1 goto compileError
del sources.txt

REM compile source of JUnit tests
dir /s /B "tests\*.java" > sources.txt
echo Compiling JUnit tests
javac -d testbin -cp "build\junit-4.12.jar;bin;testbin" @sources.txt
if errorlevel 1 goto compileError
del sources.txt

REM Generate jar of library
cd bin
echo Generating jar of library
jar cf jpt1.jar org
if errorlevel 1 goto jarError
cd ..
move bin\jpt1.jar lib

REM Running JUnit tests
java -cp "testbin;lib\jpt1.jar;build\junit-4.12.jar;build\hamcrest-core-1.3.jar" org.junit.runner.JUnitCore org.cicirello.sequences.distance.PrimitiveValueTests org.cicirello.sequences.distance.SequenceTests org.cicirello.sequences.distance.SequenceDistanceTests org.cicirello.permutations.PermutationTestCases org.cicirello.permutations.distance.PermutationDistanceTests org.cicirello.math.stats.StatisticsTests
if errorlevel 1 goto testingError

REM compile source of examples and experiment replication programs
dir /s /B "examples\*.java" > sources.txt
dir /s /B "replication\*.java" >> sources.txt
echo Compiling example programs and experiment replication programs
javac -d exbin -cp "bin;exbin" @sources.txt
if errorlevel 1 goto compileError
del sources.txt


echo BUILD SUCCESSFUL
goto exit

:jarError
echo Error generating jar file
goto exit

:compileError
del sources.txt
echo Compile error
goto exit

:testingError
echo Error in Unit Testing
goto exit

:exit

REM Return to original directory
if %changed% == 1 (
	cd build
)


