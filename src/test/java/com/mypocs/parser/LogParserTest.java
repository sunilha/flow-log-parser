package com.mypocs.parser;

import java.util.List;

import com.mypocs.model.LogLine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LogParserTest {
    @Test
    void testInitializeLookUp(){
        LogParser  logParser = new LogParser();
        Assertions.assertTrue(logParser.getPORT_PROTOCOL_TAG().isEmpty());
        logParser.initializeLookUp("src/main/resources/input/lookup.csv");
        Assertions.assertFalse(logParser.getPORT_PROTOCOL_TAG().isEmpty());
    }

    @Test
    void testReadAndBatchProcessLogs() {
        LogParser logParser = new LogParser();
        logParser.initializeLookUp("src/main/resources/input/lookup.csv");
        List<LogLine> logLines = logParser.readAndBatchProcessLogs("src/main/resources/input/flow-log.txt");
        Assertions.assertFalse(logLines.isEmpty());
    }
}
