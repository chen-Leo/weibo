package model;

import java.sql.Date;

/** 微博类  */

public class WeiboContent {
    private int weiboId;
    private int discussId;
    private String weiboPhoto;
    private String mainContent;
    private String weiboUserName;
    private Date weiboTime;


    public WeiboContent(){
    }

    public WeiboContent(int weiboId, int discussId, String weiboPhoto, String mainContent, String weiboUserName, Date weiboTime) {
        this.weiboId = weiboId;
        this.discussId = discussId;
        this.weiboPhoto = weiboPhoto;
        this.mainContent = mainContent;
        this.weiboUserName = weiboUserName;
        this.weiboTime = weiboTime;
    }

    public String getWeiboPhoto() {
        return weiboPhoto;
    }

    public void setWeiboPhoto(String weiboPhoto) {
        this.weiboPhoto = weiboPhoto;
    }

    public String getMainContent() {
        return mainContent;
    }

    public void setMainContent(String mainContent) {
        this.mainContent = mainContent;
    }

    public String getWeiboUserName() {
        return weiboUserName;
    }

    public void setWeiboUserName(String weiboUserName) {
        this.weiboUserName = weiboUserName;
    }

    public int getWeiboId() {
        return weiboId;
    }

    public void setWeiboId(int weiboId) {
        this.weiboId = weiboId;
    }

    public Date getWeiboTime() {
        return weiboTime;
    }

    public void setWeiboTime(Date weiboTime) {
        this.weiboTime = weiboTime;
    }

    public int getDiscussId() {
        return discussId;
    }

    public void setDiscussId(int discussId) {
        this.discussId = discussId;
    }
}
