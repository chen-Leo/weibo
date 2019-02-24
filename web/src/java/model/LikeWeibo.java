package model;

/**
 * 微博点赞（表）类
 */

public class LikeWeibo {
    private String userName;
    private int weiboId;
    private  int  isLike;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getWeiboId() {
        return weiboId;
    }

    public void setWeiboId(int weiboId) {
        this.weiboId = weiboId;
    }

    public int getIfLike() {
        return isLike;
    }

    public void setIfLike(int ifLike) {
        this.isLike = ifLike;
    }
}
