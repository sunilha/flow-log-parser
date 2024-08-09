package com.mypocs.model;

import java.util.Objects;

public record LogLine(int port, String protocol, String tag) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogLine logLine = (LogLine) o;
        return port == logLine.port && Objects.equals(protocol, logLine.protocol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(port, protocol);
    }
}
