package com.mypocs.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mypocs.model.LogLine;


public class LogParser {

    //https://www.iana.org/assignments/protocol-numbers/protocol-numbers.xhtml
    private static final HashMap<Integer, String> PROTOCOL_NUMBER_NAME = new HashMap<>(
            Map.of(6, "TCP",
                    17, "UDP"));

    //key = port_protocol, value = tag
    private final HashMap<String, String> PORT_PROTOCOL_TAG = new HashMap<>();

    public HashMap<String, String> getPORT_PROTOCOL_TAG() {
        return PORT_PROTOCOL_TAG;
    }

    /**
     * Read flow log file, batch process log entries and convert to list of LogLine
     *
     * @param logFile
     * @return
     */
    public List<LogLine> readAndBatchProcessLogs(String logFile) {
        List<LogLine> logLines = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(logFile))) {
            String str;
            List<String> lines = new ArrayList<String>();
            int batchSize = 100;
            int count = 0;
            while ((str = in.readLine()) != null) {
                lines.add(str);
                count++;
                if (count == batchSize) {
                    parseLogLines(lines, logLines);
                    lines.clear();
                    count=0;
                }
            }
            //handle last chunk, which will be less than batch size
            if (!lines.isEmpty()) {
                parseLogLines(lines, logLines);
                lines.clear();
            }
        } catch (IOException e) {
            System.out.println("Could not load file." + e.getMessage());
        }
        return logLines;
    }

    //Assumption, flow log follows default log pattern
    //Only port and protocol fields are parsed and captured
    private void parseLogLines(List<String> lines, List<LogLine> logLines) {
        for (String line : lines) {
            String[] split = line.split(" ");
            if (split.length > 8) {
                try {
                    int port = Integer.parseInt(split[6]);
                    String protocol = PROTOCOL_NUMBER_NAME.getOrDefault(Integer.parseInt(split[7]), "Unknown");
                    String tag = PORT_PROTOCOL_TAG.getOrDefault((split[6] + "_" + protocol), "Untagged");
                    LogLine logLine = new LogLine(port, protocol, tag);
                    logLines.add(logLine);
                } catch (NumberFormatException e) {
                    //log and suppress the exception for now.
                    System.out.println("Error while parsing log, port = " + split[6] + " and protocol = " + split[7]);
                }
            }
        }
    }

    /**
     * Read lookup csv file and load entries to map
     *
     * @param lookupFile
     */
    public void initializeLookUp(String lookupFile) {
        try (BufferedReader in = new BufferedReader(new FileReader(lookupFile))) {
            String str;
            int count = 0;
            while ((str = in.readLine()) != null) {
                if (count > 0) { //skip processing of header row
                    String[] split = str.split(",");
                    PORT_PROTOCOL_TAG.put((split[0] + "_" + split[1].toUpperCase()), split[2].toUpperCase());
                }
                if (count == 0) //increase once is enough to skip the header row
                    count++;
            }
        } catch (IOException ioe) {
            System.out.println("Could not load file." + ioe.getMessage());
        }
    }
}
