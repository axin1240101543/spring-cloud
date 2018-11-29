package com.micro.darren.common.http;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class Stopwatch {

    long start;
    List<Stop> stops = new ArrayList<Stop>();
    String seq;

    public Stopwatch(String seq) {
        start = System.currentTimeMillis();
        this.seq = seq;
    }

    public Stopwatch() {
        start = System.currentTimeMillis();
    }

    public void reset() {
        start = System.currentTimeMillis();
        stops = new ArrayList<Stop>();
    }

    public void stop(String name) {
        stops.add(new Stop(name, System.currentTimeMillis()));
    }

    public String getIntervals(String allName, boolean useCurrentTime) {
        Stop allTime = null;
        if (useCurrentTime) {
            allTime = new Stop(allName, System.currentTimeMillis());
        }
        StringBuilder sbuf = new StringBuilder();
        long start = this.start;
        for (Stop s : stops) {
            s.toString(sbuf, start);
            sbuf.append('/');
            start = s.time;
        }
        if (stops.isEmpty()) {
            start = System.currentTimeMillis();
        }
        if (allTime != null) {
            allTime.toString(sbuf, this.start);
        } else {
            new Stop(allName, start).toString(sbuf, this.start);
        }
        return sbuf.toString();
    }

    public long getInterval() {
        int size = stops.size();
        if (size > 0) {
            return stops.get(size - 1).time - this.start;
        }
        return 0;
    }

    public void log(String name) {
        this.log(name, true);
    }

    public void log(String name, boolean useCurrentTime) {
        if (name != null) {
            if (StringUtils.isBlank(seq)) {
                log.info("{} TIME {}", name,
                        this.getIntervals("ALL", useCurrentTime));
            } else {
                log.info("{} TIME SEQ {} {}", name, seq,
                        this.getIntervals("ALL", useCurrentTime));
            }
        } else {
            if (StringUtils.isBlank(seq)) {
                log.info("TIME {}", this.getIntervals("ALL", useCurrentTime));
            } else {
                log.info("TIME SEQ{} {}", seq,
                        this.getIntervals("ALL", useCurrentTime));
            }
        }
    }

    public static class Stop {
        public String name;
        public long time;

        public Stop(String name, long time) {
            super();
            this.name = name;
            this.time = time;
        }

        public void toString(StringBuilder sbuf, long start) {
            if (name != null) {
                sbuf.append(name).append(':');
            }
            sbuf.append(time - start);
        }
    }
}
