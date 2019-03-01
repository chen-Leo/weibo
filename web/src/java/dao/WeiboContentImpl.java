package dao;

import model.User;
import model.WeiboContent;
import model.WeiboContentManage;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * 这是对微博相关的数据库操作的具体实现类
 */
public class WeiboContentImpl implements WeiboContentManage {

    //添加一个微博
    @Override
    public boolean add(WeiboContent weibo) {
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        conn = DataConner.getConnection();

        //对时间进行格式转换以便存入数据库
        DateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String weiboTime = time.format(weibo.getWeiboTime());

        try {
            String sql = "INSERT INTO weibo_content(weiboId,discussId,weiboPhoto,mainContent,weiboUsername,weiboTime)VALUES (?,null,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, weibo.getWeiboId());
            pst.setString(2, weibo.getWeiboPhoto());
            pst.setString(3, weibo.getMainContent());
            pst.setString(4, weibo.getWeiboUserName());
            pst.setString(5, weiboTime);
            int result = pst.executeUpdate();
            if (result == 1) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DataConner.close(rs, pst, conn);
        return flag;
    }

    //基于某一用户所发的所有微博基于时间的顺序排序
    @Override
    public ArrayList<WeiboContent> weiboAll(String userName) {
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;


        ArrayList<WeiboContent> WeiboContents = new ArrayList<>();
        conn = DataConner.getConnection();

        try {
            String sql = "SELECT * FROM weibo_content WHERE weiboUserName = ? ORDER BY weiboTime";
            pst = conn.prepareStatement(sql);
            pst.setString(1, userName);
            rs = pst.executeQuery();
            while (rs.next()) {
                WeiboContent wbCon = new WeiboContent();
                wbCon.setWeiboId(rs.getInt(1));
                wbCon.setDiscussId(rs.getInt(2));
                wbCon.setWeiboPhoto(rs.getString(3));
                wbCon.setMainContent(rs.getString(4));
                wbCon.setWeiboUserName(rs.getString(5));
                wbCon.setWeiboTime(rs.getDate(6));
                WeiboContents.add(wbCon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DataConner.close(rs, pst, conn);
        return WeiboContents;


    }

    //实现微博的搜索功能并基于时间的顺序排序
    @Override
    public ArrayList<WeiboContent> weiboSearch(String keywords) {
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        ArrayList<WeiboContent> weibos = new ArrayList<>();
        conn = DataConner.getConnection();
        try {
            String sql = "SELECT * FROM weibo_content WHERE mainContent LIKE \"%\"?\"%\" ORDER BY LENGTH(weiboTime)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, keywords);
            rs = pst.executeQuery();
            while (rs.next()) {
                WeiboContent wbLike = new WeiboContent();
                wbLike.setWeiboId(rs.getInt(1));
                wbLike.setDiscussId(rs.getInt(2));
                wbLike.setWeiboPhoto(rs.getString(3));
                wbLike.setMainContent(rs.getString(4));
                wbLike.setWeiboUserName(rs.getString(5));
                wbLike.setWeiboTime(rs.getDate(6));
                weibos.add(wbLike);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DataConner.close(rs, pst, conn);
        return weibos;
    }

    //删除一条微博
    @Override
    public boolean delete(int weiboId) {
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int res;
        conn = DataConner.getConnection();
        LikeWeiboImpl likeWeibo = new LikeWeiboImpl();

        try {
            String sql = "DELETE FROM weibo_content WHERE weiboId = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, weiboId);
            res = pst.executeUpdate();
            //删除与之关联的点赞表
            likeWeibo.delectWeibo(weiboId);
            if (res == 1) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DataConner.close(rs, pst, conn);
        return flag;
    }

    //返回数据库中微博的最大id
    public int weiboIdMAx() {
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int res = -1;
        conn = DataConner.getConnection();

        try {
            String sql = "SELECT MAX(weiboId) from weibo_content";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if ( rs.next()) {
                res = rs.getInt(1);
            } else res = 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DataConner.close(rs, pst, conn);
        return res;
    }


    //返回最近5条的微博
    public ArrayList<WeiboContent> weiboAllRecent() {
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        conn = DataConner.getConnection();

        ArrayList<WeiboContent> weibos = new ArrayList<>();

        try {
            String sql = "SELECT * FROM weibo_content ORDER BY weiboTime LIMIT 5";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                WeiboContent wbLike = new WeiboContent();
                wbLike.setWeiboPhoto(rs.getString(3));
                wbLike.setWeiboId(rs.getInt(1));
                wbLike.setDiscussId(rs.getInt(2));
                wbLike.setMainContent(rs.getString(4));
                wbLike.setWeiboUserName(rs.getString(5));
                wbLike.setWeiboTime(rs.getDate(6));
                weibos.add(wbLike);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DataConner.close(rs, pst, conn);
        return weibos;
    }

    //判断该微博是不是用户本人所属
    public boolean isMaster(String userName, int weiboId) {
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        conn = DataConner.getConnection();
        int res;
        try {
            String sql = "SELECT weiboUserName FROM weibo_content WHERE weiboId = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, weiboId);
            rs = pst.executeQuery();
            if (rs.next()) {
                if (userName.equals(rs.getString(1))) {
                    flag = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DataConner.close(rs, pst, conn);
        return flag;
    }
}
