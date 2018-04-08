package com.scrat.zhuhaibus.data.modle;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class News implements Serializable {
    @SerializedName("news_id")
    private String newsId;
    private String dt;
    private String pt;
    private String title;
    private String cover;
    @SerializedName("news_ts")
    private long newsTs;
    private String detail;
    private String tp;

    public String getNewsId() {
        return newsId;
    }

    public String getDt() {
        return dt;
    }

    public String getPt() {
        return pt;
    }

    public String getTitle() {
        return title;
    }

    public String getCover() {
        return cover;
    }

    public long getNewsTs() {
        return newsTs;
    }

    public String getDetail() {
        return detail;
    }

    public String getTp() {
        return tp;
    }

    @Override
    public String toString() {
        return "News{" +
                "newsId='" + newsId + '\'' +
                ", dt='" + dt + '\'' +
                ", pt='" + pt + '\'' +
                ", title='" + title + '\'' +
                ", cover='" + cover + '\'' +
                ", newsTs=" + newsTs +
                ", detail='" + detail + '\'' +
                ", tp='" + tp + '\'' +
                '}';
    }
}
