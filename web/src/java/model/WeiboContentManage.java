package model;

import java.util.ArrayList;

public interface WeiboContentManage {
    //加入一个新的微博
    public boolean add(WeiboContent weibo);

    //基于某一用户所发的所有微博的深度排序
    public ArrayList<WeiboContent> weiboAll(User user);

    //微博搜索
    public ArrayList<WeiboContent> weiboSearch(String keywords);

    //删除一个微博
    public boolean delete(int weiboId);

    //返回数据库中微博的最大id
    public int weiboIdMAx();

    //返回最近的微博
    public ArrayList<WeiboContent> weiboAllRecent();

}
