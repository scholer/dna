@echo off

echo Windows boostrapping script for DNA annealing simulation.

REM ## Settings: ##

SET THREADS=8
SET PATHS=20
REM If running on a workstation/laptop a lower number of threads is nicer so the computer is still usable during simulation.
SET THREADS=2
SET PATHS=8
REM For testing purposes just use RATE=2 and PATHS=2 (for some reason, PATHS=1 doesn't work...)


SET RATE=0.4
SET DESIGN23e="design23e"
SET DESIGN0="design23eAll"
SET DESIGN1="design23eAllEnergy"
SET DESIGN2="methodsPaperAllPlots"
SET DESIGN3="methodsPaperNoCooperativity"

REM ## Description of parameters: ##
REM THREADS: number of simulation threads
REM PATHS: number of folding pathways simulated PER THREAD
REM RATE: temp drop in celsius per minute (rate)
REM DESIGN0="design23eAll"          # runs 5 different designs
REM DESIGN1="design23eAllEnergy"        # runs same designs with sequence-specific domain stability
REM DESIGN2="methodsPaperAllPlots"      # methods paper plots and data
REM DESIGN3="methodsPaperNoCooperativity"   # special SI table in the methods paper


REM ## Run ##

echo Starting simulation with %THREADS% threads each producing %PATHS% folding paths using temperature ramp with slope -%RATE% Celcius/minute.

REM echo java -cp lib/jgrapht-core-0.9.1.jar;lib/commons-math3-3.5.jar -Xmx4000m folding.Control %DESIGN0% "distance" %THREADS% %PATHS% %RATE%
java -cp bin;lib/jgrapht-core-0.9.1.jar;lib/commons-math3-3.5.jar -Xmx2000m folding.Control %DESIGN0% "distance" %THREADS% %PATHS% %RATE%
REM Use -cp to specify class-paths to import;
REM Use -Xmx<size> to set maximum Java heap size (in this case 4000 MB)

REM For very quick execution (< 1 minute), use:
REM java -cp bin;lib/jgrapht-core-0.9.1.jar;lib/commons-math3-3.5.jar -Xmx2000m folding.Control design23e distance" 2 2 5

echo Simulation completed with exit status: %errorlevel%

