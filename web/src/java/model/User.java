package model;

/**
 * 用户类 /json用户类
 */

public class User {
    private String photo;
    private String name;
    private String password;
    private int attentions;
    private int fansNumber;
    private int weiboNumber;
    public User(){};

    public User( String photo, String name, String password, int attentions, int fansNumber,int weiboNumber){
      this.photo = photo;
      this.name = name;
      this.password = password;
      this.attentions = attentions;
      this.fansNumber = fansNumber;
      this.weiboNumber = weiboNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getAttentions() {
        return attentions;
    }

    public void setAttentions(int attentions) {
        this.attentions = attentions;
    }

    public int getFansNumber() {
        return fansNumber;
    }

    public void setFansNumber(int fansNumber) {
        this.fansNumber = fansNumber;
    }

    public int getWeiboNumber() {
        return weiboNumber;
    }

    public void setWeiboNumber(int weiboNumber) {
        this.weiboNumber = weiboNumber;
    }
}
