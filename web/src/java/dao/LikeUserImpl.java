package dao;

import model.LikeUserManage;

import java.sql.*;

public class LikeUserImpl implements LikeUserManage {

    //关注（如果已经关注就改为取消点赞)
    @Override
    public boolean addLike(String userName, String likeUserName) {
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        conn = DataConner.getConnection();
        int res;
        String sql = null;
        UserImpl user = new UserImpl();
        //判断是关注还是取消关注
        try {

            res = FindIsLike(userName, likeUserName);
            if ( res == -1 ){
                if ( AddLikeTable(userName,likeUserName) == -1) {
                    return flag ;
                }
                else return flag = true;
            }
            else if (res == 0) {
                sql = "UPDATE  like_user SET isLike = 1 WHERE userName = ? AND likeUserName = ?";
                //调用 UserImpl 使用户关注数 +1
                user.likeUserAdd(userName);
            } else {
                sql = "UPDATE  like_user SET isLike = 0 WHERE userName = ? AND likeUserName = ?";
                //调用 UserImpl 使用户关注数 -1
                user.likeUserDelect(userName);
            }

            conn = DataConner.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, userName);
            pst.setString(2, likeUserName);
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

    //删除关注表
    @Override
    public boolean delectUser(String userName) {
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        conn = DataConner.getConnection();
        int res;
        try {
            String sql = "DELETE FROM like_user WHERE userName = ?";
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

    //删除关注表
    @Override
    public boolean delectLikeUser(String likeUserName) {
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        conn = DataConner.getConnection();
        int res;
        try {
            String sql = "DELETE FROM like_user WHERE likeUserName = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, likeUserName);
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

    //查询是否关注过
    public int FindIsLike(String userName, String likeUserName) {

        boolean flag = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        conn = DataConner.getConnection();
        int res = -1;

        try {
            String sql = "SELECT isLike FROM like_user WHERE userName = ? AND likeUserName = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, userName);
            pst.setString(2, likeUserName);
            rs = pst.executeQuery();
            //如果结果不为空
            if (rs.next()) res = rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    //添加一个关注表
    public int  AddLikeTable(String userName,String likeUserName){
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        conn = DataConner.getConnection();
        int res = -1;

        try {
            String sql = "INSERT INTO like_user (userName,likeUserName) VALUES (?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1,userName);
            pst.setString(2,likeUserName);
            res = pst.executeUpdate();
        }catch (SQLException e)  {
            e.printStackTrace();
        }
       return res;
    }
}
