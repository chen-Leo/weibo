package model;

import java.util.ArrayList;

public interface DiscussManage {

    //加入一个新的评论
    public boolean add(Discuss dis);

    //返回按时间的顺序排序某个微博的所有评论
    public ArrayList<Discuss> discussAll(int weiboid);

    //删除一个评论
    public boolean delete(int discussId);

    //将评论数组的主要内容(MainDiscuss)转化为String[]格式
    public String[] mainToString(ArrayList<Discuss> discusses);

    //返回数据库中评论的最大id
    public int weiboIdMAx();
}
