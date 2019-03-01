package model;

public interface LikeUserManage {
    //关注（如果已经关注就改为取消点赞)
    public boolean addLike(String userName,String likeUserName);
    //删除关注表
    public boolean delectUser(String userName);
    //删除关注表
    public boolean delectLikeUser(String likeUserName);
    //查询是否关注过
    public int findIsLike(String userName,String likeUserName);
    //添加一个关注表
    public boolean  addAttentionTable(String userName,String likeUserName);
    //判断用户表中是否有该用户关注了他人
    public boolean isUserExist(String userName);
    //判断用户表中是否有该用户被关注
    public boolean isLikeUserExist(String userLikeName);
    //更改关注表中用户的姓名
    public boolean userNameChange(String userNewName, String userOriginName);
    //更改关注表中被关注用户的姓名
    public boolean likeUserNameChange(String userNewName ,String userOriginName);
}
