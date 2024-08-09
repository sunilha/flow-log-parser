package com.mypocs;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import com.mypocs.model.LogLine;
import com.mypocs.parser.LogParser;
import com.mypocs.utility.FileUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IntegrationTest {

    @Test
    void processLogs() {
        LogParser logParser = new LogParser();
        logParser.initializeLookUp("src/main/resources/input/lookup.csv");
        List<LogLine> logLines = logParser.readAndBatchProcessLogs("src/main/resources/input/flow-log.txt");

        File tagCountFile = new File("src/test/resources/TagCount.txt");
        Assertions.assertFalse(tagCountFile.exists());
        File ppCountFile = new File("src/test/resources/PortProtocolCount.txt");
        Assertions.assertFalse(ppCountFile.exists());

        FileUtility.writeTagCountOutputFile(logLines, "src/test/resources/TagCount.txt");
        Assertions.assertTrue(tagCountFile.exists());

        FileUtility.writePortProtocolCountOutputFile(logLines, "src/test/resources/PortProtocolCount.txt");
        Assertions.assertTrue(ppCountFile.exists());

    }

    @BeforeEach
    void setup() {
        //delete output file from previous run
        new File("src/test/resources/TagCount.txt").deleteOnExit();
        new File("src/test/resources/PortProtocolCount.txt").deleteOnExit();
    }
}
