package model;

public interface LikeWeiboManage {
    //点赞（如果已经点赞就改为取消点赞)
    public boolean addLike(String userName ,int weiboId);
    //删除点赞(用户)
    public boolean delectUser(String userName);
    //删除点赞(微博)
    public boolean delectWeibo(int weiboId);
    //查询是否点赞
    public int FindIsLike(String userName,int weiboId);
}