## Log Parser

Java program to parse flow log data and map each row to a tag based on lookup table. The lookup table is defined as a csv file,
and it has 3 columns, dstport,protocol,tag. The dstport and protocol combination decide what tag can be applied.

This is a simple Java program with gradle build tool and no usage of external libraries apart from junit for unit testing.

### Pre-requisites
Java 21

### Assumption
1. Flow Log contents will be in default format
2. Input and output folder are reference


### Build command
``` .\gradlew clean build ```

### Run Command
Below command run java program, pick input files present in ``` src/main/resources/input``` folder and generated output files will be placed in ``` src/main/resources/output ``` folder.

``` .\gradlew run```