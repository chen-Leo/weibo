package servlet;

import dao.WeiboContentImpl;
import model.User;
import model.WeiboContent;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
/**
 * 该接口实现发微博功能
 *
 *
 */



@WebServlet("/weibo/AddWeiboServlet")
public class AddWeiboServlet extends HttpServlet {

    WeiboContentImpl weiboContentImpl;

    public void init() {
        weiboContentImpl = new WeiboContentImpl();
    }

    @Override
    public void doGet(HttpServletRequest requset, HttpServletResponse response) throws ServletException, IOException {
        doPost(requset, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        response.setContentType("text/html;charset=UTF-8");
        String mainContent = request.getParameter("mainContent");
        if (AddAttentionsServlet.isUserNULL(response, user)) return;
        if (mainContent == null) return;

        try {
            mainContent = new String(mainContent.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        
        
        Date date = new Date();//获取系统当前时间
        WeiboContent weiboContent = new WeiboContent(weiboContentImpl.weiboIdMAx() + 1, 0, null, mainContent, user.getName(), new java.sql.Date(date.getTime()));

        try {

            if (weiboContentImpl.add(weiboContent))
                response.getWriter().write("加入成功");
            else
                response.getWriter().write("加入微博失败");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
