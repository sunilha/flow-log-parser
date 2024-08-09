package com.mypocs;

import java.util.List;

import com.mypocs.utility.FileUtility;
import com.mypocs.model.LogLine;
import com.mypocs.parser.LogParser;

public class LogParserApplication {

    private static final String INPUT_FOLDER = "src/main/resources/input/";
    private static final String OUTPUT_FOLDER = "src/main/resources/output/";
    public static void main(String[] args) {
        LogParser logParser = new LogParser();

        logParser.initializeLookUp(INPUT_FOLDER + "lookup.csv");
        List<LogLine> logLines = logParser.readAndBatchProcessLogs(INPUT_FOLDER + "flow-log.txt");

        FileUtility.writeTagCountOutputFile(logLines, OUTPUT_FOLDER+"TagCount.txt");
        FileUtility.writePortProtocolCountOutputFile(logLines, OUTPUT_FOLDER+"PortProtocolCount.txt");
    }
}