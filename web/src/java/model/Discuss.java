package model;


import java.sql.Date;


public class Discuss {
  private int discusssId;
  private int weiboId;
  private String mainDiscuss;
  private String discussUserName;
  private Date   discussTime;

    public Discuss(){
    }

    public Discuss(int discussId, int weiboId, String mainDiscuss, String discussUserName, Date discussTime) {
        this.weiboId = weiboId;
        this.discusssId =discussId;
        this.mainDiscuss = mainDiscuss;
        this.discussUserName =discussUserName;
        this.discussTime =discussTime;
    }
    public int getDiscusssId() {
        return discusssId;
    }

    public void setDiscusssId(int discusssId) {
        this.discusssId = discusssId;
    }

    public int getWeiboId() {
        return weiboId;
    }

    public void setWeiboId(int weiboId) {
        this.weiboId = weiboId;
    }

    public String getMainDiscuss() {
        return mainDiscuss;
    }

    public void setMainDiscuss(String mainDiscuss) {
        this.mainDiscuss = mainDiscuss;
    }

    public String getDiscussUserName() {
        return discussUserName;
    }

    public void setDiscussUserName(String discussUserName) {
        this.discussUserName = discussUserName;
    }

    public Date getDiscussTime() {
        return discussTime;
    }

    public void setDiscussTime(Date discussTime) {
        this.discussTime = discussTime;
    }
}