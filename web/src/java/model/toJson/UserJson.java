package model.toJson;

import model.User;

public class UserJson {

    private String photo;
    private String name;
    private int attentions;
    private int fansNumber;
    private int weiboNumber;
    private String personalStatement;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPersonalStatement() {
        return personalStatement;
    }

    public void setPersonalStatement(String personalStatement) {
        this.personalStatement = personalStatement;
    }

    //将一个数据用户信息封装成需要返回的用户json信息
    public void ToUserJson(User user,UserJson userJson){
        userJson.setPhoto(user.getPhoto());
        userJson.setName(user.getName());
        userJson.setAttentions(user.getAttentions());
        userJson.setFansNumber(user.getFansNumber());
        userJson.setWeiboNumber(user.getWeiboNumber());
        userJson.setPersonalStatement(user.getPersonalStatement());
    }
}

