package model;


import java.util.ArrayList;

public interface UserManage {

    //检测用户是否重名
    public boolean checkName(String userName);
    //加入一个新用户
    public boolean add(User u);
    //检测登陆
    public boolean checkLogin(String name, String password);
    //传输用户的信息
    public User userMessage(String userName);
    //删除一个用户
    public boolean delect(String userName);
    //用户关注数+1
    public  boolean likeUserAdd(String userName);
    //用户关注数-1
    public  boolean likeUserDelect(String userName);
    //搜索相关用户
    public ArrayList<User> userFind(String userNameFind);



}