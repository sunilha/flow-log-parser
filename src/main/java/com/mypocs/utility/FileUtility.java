package com.mypocs.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.mypocs.model.LogLine;

public final class FileUtility {
    private FileUtility(){}
    private static final String TAB_DELIMITER ="\t";

    public static void writePortProtocolCountOutputFile(List<LogLine> logLines, String outputFile) {
        File ppCountFile = new File(outputFile);

        Map<String, Long> portProtocolCount = logLines.stream()
                .collect(Collectors.groupingBy((logLine -> logLine.port() + "_ " + logLine.protocol()),
                        Collectors.counting()));

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(ppCountFile))) {
            bw.write("Port"+TAB_DELIMITER +"Protocol" +TAB_DELIMITER+"Count");
            bw.newLine();
            List<Map.Entry<String, Long>> sortedEntries = portProtocolCount.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey()).toList();
            for (Map.Entry<String, Long> entrySet : sortedEntries) {
                String[] split = entrySet.getKey().split("_");
                bw.write(split[0] + TAB_DELIMITER + split[1] + TAB_DELIMITER  +entrySet.getValue());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error while write Port Protocol Count output file." + e.getMessage());
        }
    }

    public static void writeTagCountOutputFile(List<LogLine> logLines, String outputFile) {
        File tagCountFile = new File(outputFile);
        Map<String, Long> tagCount = logLines.stream()
                .collect(Collectors.groupingBy(LogLine::tag,
                        Collectors.counting()));
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(tagCountFile))) {
            bw.write("Tag" +TAB_DELIMITER+"Count");
            bw.newLine();
            List<Map.Entry<String, Long>> sortedEntries = tagCount.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey()).toList();
            for (Map.Entry<String, Long> entrySet : sortedEntries) {
                bw.write(entrySet.getKey() + TAB_DELIMITER + entrySet.getValue());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error while write tag count output file." + e.getMessage());
        }
    }
}
