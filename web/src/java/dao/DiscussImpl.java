package dao;

        import model.Discuss;
        import model.DiscussManage;

        import java.sql.*;
        import java.text.DateFormat;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.List;

public class DiscussImpl implements DiscussManage {
    //加入一个新的评论表
    @Override
    public boolean add(Discuss dis) {
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int res;
        conn = DataConner.getConnection();

        //对时间进行格式转换以便存入数据库
        DateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String discussTime = time.format(dis.getDiscussTime());

        try {
            String sql = "INSERT INTO discuss(discussId,weiboId,mainDiscuss,discussUserName,discussTime)VALUES (?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, dis.getDiscusssId());
            pst.setInt(2, dis.getWeiboId());
            pst.setString(3, dis.getMainDiscuss());
            pst.setString(4, dis.getDiscussUserName());
            pst.setString(5, discussTime);
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

    //返回所有微博的评论基于时间的顺序排序
    @Override
    public ArrayList<Discuss> discussAll(int weiboId) {
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Discuss dis = new Discuss();
        ArrayList<Discuss> discusses = new ArrayList<>();
        conn = DataConner.getConnection();

        try {
            String sql = "SELECT * FROM discuss WHERE weiboId = ? ORDER BY discussTime";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, weiboId);
            rs = pst.executeQuery();
            while (rs.next()) {
                dis.setDiscusssId(rs.getInt(1));
                dis.setWeiboId(rs.getInt(2));
                dis.setMainDiscuss(rs.getString(3));
                dis.setDiscussUserName(rs.getString(4));
                dis.setDiscussTime(rs.getDate(5));
                discusses.add(dis);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DataConner.close(rs, pst, conn);
        return discusses;
    }


    //删除一条评论
    @Override
    public boolean delete(int discussId) {
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int res;
        conn = DataConner.getConnection();
        try {
            String sql = "DELETE FROM discuss WHERE discussId = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, discussId);
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

    //将评论数组的主要内容(MainDiscuss)转化为String[]格式
    @Override

    public String[] mainToString(ArrayList<Discuss> discusses) {
        List<String> list = new ArrayList<>();
        for (Discuss discuss : discusses) {
            list.add(discuss.getMainDiscuss());
        }
        return list.toArray(new String[]{});
    }

    //返回数据库中评论的最大id
    public int weiboIdMAx() {
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int res = -1;
        conn = DataConner.getConnection();

        try {
            String sql = "SELECT MAX(discussId) from discuss";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                res = rs.getInt(1);
            } else res = 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}
