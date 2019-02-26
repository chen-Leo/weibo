package model.toJson;

public class UserJson {

    private String photo;
    private String name;
    private int attentions;
    private int fansNumber;
    private int weiboNumber;

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
}

