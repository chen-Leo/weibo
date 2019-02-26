package dao;

import model.LikeWeiboManage;

import java.sql.*;

public class LikeWeiboImpl implements LikeWeiboManage {

    //如果已经点赞就改为取消点赞
    @Override
    public boolean addLike(String userName, int weiboId) {
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        conn = DataConner.getConnection();
        int res;
        String sql = null;

        try {
            //查找是否点过赞
            res = findIsLike(userName, weiboId);
            if ( res == 1 ){
                if ( addAttentionTable(userName,weiboId) == -1) {
                    return flag ;
                }
                else return flag = true;
            }
            else if (res == 0) {
                sql = "UPDATE  like_weibo SET isLike = 1 WHERE userName = ? AND weiboId = ?";
            } else sql = "UPDATE  like_weibo SET isLike = 0 WHERE userName = ? AND weiboId = ?";

            //进行操作
            conn = DataConner.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, userName);
            pst.setInt(2, weiboId);
            res = pst.executeUpdate();
            if (res == 1) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DataConner.close(rs, pst, conn);
        return flag;
    }

    //删除点赞(用户)
    @Override
    public boolean delectUser(String userName) {
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        conn = DataConner.getConnection();
        int res;
        try {
            String sql = "DELETE FROM like_weibo WHERE userName = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, userName);
            res = pst.executeUpdate();
            if (res == 1) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DataConner.close(rs, pst, conn);
        return flag;
    }

    //删除点赞(微博)
    @Override
    public boolean delectWeibo(int weiboId) {
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        conn = DataConner.getConnection();
        int res;
        try {
            String sql = "DELETE FROM like_weibo WHERE weiboId = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, weiboId);
            res = pst.executeUpdate();
            if (res == 1) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DataConner.close(rs, pst, conn);
        return flag;
    }


    //查询是否点赞
    public int findIsLike(String userName, int weiboId) {

        boolean flag = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        conn = DataConner.getConnection();
        int res = -1;

        try {
            String sql = "SELECT isLike FROM like_weibo WHERE userName = ? AND weiboId = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, userName);
            pst.setInt(2, weiboId);
            rs = pst.executeQuery();
            if (rs.next()) res = rs.getInt(1);
            else res = 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DataConner.close(rs, pst, conn);
        return res;
    }
    public int  addAttentionTable(String userName,int weiboId){
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        conn = DataConner.getConnection();
        int res = -1;

        try {
            String sql = "INSERT INTO like_weibo (userName,weiboId) VALUES (?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1,userName);
            pst.setInt(1,weiboId);
            res = pst.executeUpdate();
        }catch (SQLException e)  {
            e.printStackTrace();
        }
        DataConner.close(rs, pst, conn);
        return res;
    }
}
