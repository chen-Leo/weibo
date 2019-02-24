package model;


/**
 * 用户关注（表）类
 */
public class LikeUser {
    private String userName;
    private String likeUserName;
    private int isLike;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLikeUserName() {
        return likeUserName;
    }

    public void setLikeUserName(String likeUserName) {
        this.likeUserName = likeUserName;
    }

    public int getIsLike() {
        return isLike;
    }

    public void setIsLike(int isLike) {
        this.isLike = isLike;
    }
}
