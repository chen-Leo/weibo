package model;

public interface LikeUserManage {
    //关注（如果已经关注就改为取消点赞)
    public boolean addLike(String userName,String likeUserName);
    //删除关注表
    public boolean delectUser(String userName);
    //删除关注表
    public boolean delectLikeUser(String likeUserName);
    //查询是否关注过
    public int FindIsLike(String userName,String likeUserName);
}
