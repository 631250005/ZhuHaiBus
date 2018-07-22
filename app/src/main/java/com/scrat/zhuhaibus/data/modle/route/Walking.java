package com.scrat.zhuhaibus.data.modle.route;

public class Walking {
    private String origin;
    private String destination;
    private String distance;
    private String duration;

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getDistance() {
        return distance;
    }

    public String getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "Walking{" +
                "origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", distance='" + distance + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}
