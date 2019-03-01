package dao;
import model.Encrypt;
import model.User;
import model.UserManage;

import java.sql.*;
import java.util.ArrayList;

/**
 * 这是对用户相关的数据库操作的具体实现类
 */
public class UserImpl implements UserManage {

    //检测用户名是否重名
    @Override
    public boolean checkName(String userName) {
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM users WHERE userName = ?";
        conn = DataConner.getConnection();
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, userName);
            rs = pst.executeQuery();
            if (!rs.next()) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DataConner.close(rs, pst, conn);
        return flag;
    }


    //添加一个新用户(如果重名返回false)
    @Override
    public boolean add(User user) {
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        conn = DataConner.getConnection();
        Encrypt encrypt = new Encrypt();

        //插入一个新用户
        try {
            String sql = "INSERT INTO users (photo,userName,password,attentions,fansNumber,weiboNumbers,personalStatement) VALUES (?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, user.getPhoto());
            pst.setString(2, user.getName());
            pst.setString(3, encrypt.EncryptPassword(user.getPassword()));
            pst.setInt(4, user.getAttentions());
            pst.setInt(5, user.getFansNumber());
            pst.setInt(6, user.getWeiboNumber());
            pst.setString(7,user.getPersonalStatement());
            int resu = pst.executeUpdate();
            if (resu == 1) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        DataConner.close(rs, pst, conn);
        return flag;
    }


    //登陆验证
    @Override
    public boolean checkLogin(String name, String password) {
        boolean flag = false;
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        String sql = "SELECT * FROM users WHERE userName = ?";
        Encrypt encrypt = new Encrypt();
        conn = DataConner.getConnection();

        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            rs = pst.executeQuery();
            while (rs.next()) {
                if (encrypt.EncryptPassword(password).equals(rs.getString("password"))) {
                    flag = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        } finally {
            DataConner.close(rs, pst, conn);
        }
        return flag;
    }


    //用户信息回传
    @Override
    public User userMessage(String userName) {

        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        String sql = "SELECT * FROM users WHERE userName = ?";
        conn = DataConner.getConnection();
        User user = new User();
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, userName);
            rs = pst.executeQuery();
            while (rs.next()) {

                user.setPhoto(rs.getString(1));
                user.setName(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setAttentions(rs.getInt(4));
                user.setFansNumber(rs.getInt(5));
                user.setWeiboNumber(rs.getInt(6));
                user.setPersonalStatement(rs.getString(7));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DataConner.close(rs, pst, conn);
        return user;
    }

    //删除一个用户
    @Override
    public boolean delect(String userName) {
        boolean flag = false;
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        conn = DataConner.getConnection();
        int res;

        try {
            String sql = "DELETE FROM users WHERE userName = ?";
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

    //用户关注数+1
    @Override
    public boolean likeUserAdd(String userName) {
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int res;
        conn = DataConner.getConnection();

        try {
            String sql = "UPDATE users SET attentions = attentions+1 WHERE userName  = ?";
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


    //用户关注数-1
    @Override
    public boolean likeUserDelect(String userName) {
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int res;
        conn = DataConner.getConnection();

        try {
            String sql = "UPDATE users SET attentions = attentions-1 WHERE userName  = ?";
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

    //搜索相关用户
    @Override

    public ArrayList<User> userFind(String userNameFind) {
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;


        ArrayList<User> users = new ArrayList<>();
        conn = DataConner.getConnection();

        try {
            String sql = "SELECT * FROM users WHERE userName LIKE  \"%\"?\"%\"  ORDER BY LENGTH(userName)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, userNameFind);
            rs = pst.executeQuery();
            while (rs.next()) {
                User userLike = new User();
                userLike.setPhoto(rs.getString(1));
                userLike.setName(rs.getString(2));
                userLike.setPassword(rs.getString(3));
                userLike.setAttentions(rs.getInt(4));
                userLike.setFansNumber(rs.getInt(5));
                userLike.setWeiboNumber(rs.getInt(6));
                userLike.setPersonalStatement(rs.getString(7));
                users.add(userLike);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DataConner.close(rs, pst, conn);
        return users;
    }


    //添加用户的头像
    public boolean userPhotoAdd(String filePath,String userName){
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int res = -1;
        conn = DataConner.getConnection();
        if ("".equals(filePath)) return flag;
        try {
            String sql = "UPDATE users SET photo = ? WHERE userName = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, filePath);
            pst.setString(2, userName);
            res = pst.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        if ( res == 1 ) flag = true;
        DataConner.close(rs, pst, conn);
        return  flag;
    }

    //更改用户姓名
    public  boolean userNameChange(String userNewName,String userOriginName){
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int res = -1;
        conn = DataConner.getConnection();

        try {
            String sql = "UPDATE users SET userName = ? WHERE userName = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, userNewName);
            pst.setString(2, userOriginName);
            res = pst.executeUpdate();
            if (res == 1) flag =  true;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        DataConner.close(rs, pst, conn);
         return  flag;
    }


    //更改用户的个人简介
    public boolean perStatementUpdate(String userName,String perStatement){
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int res = -1;
        conn = DataConner.getConnection();

        try {
            String sql = "UPDATE users SET personalStatement = ? WHERE userName = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, perStatement);
            pst.setString(2, userName);

            res = pst.executeUpdate();
            if (res == 1) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DataConner.close(rs, pst, conn);
        return  flag;

    }
}




