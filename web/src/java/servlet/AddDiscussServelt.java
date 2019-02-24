package servlet;

import dao.DiscussImpl;
import model.Discuss;
import model.User;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

@WebServlet("/weibo/AddDiscussServlet")
public class AddDiscussServelt extends HttpServlet {

    DiscussImpl discussImpl;

    public void init() {
        discussImpl = new DiscussImpl();
    }

    @Override
    public void doGet(HttpServletRequest requset, HttpServletResponse response) throws ServletException, IOException {
        doPost(requset, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        int weiboId = Integer.parseInt(request.getParameter("weiboId"));
        String mainDiscuss = request.getParameter("mainDiscuss");
        response.setContentType("text/html;charset=UTF-8");

        if (user == null) {
            try {
                response.getWriter().print("您还未登陆，请<a href='/weibo/login.html'>登陆</a>");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Date date = new Date();//获取系统当前时间
        Discuss discuss = new Discuss(discussImpl.weiboIdMAx() + 1, weiboId, mainDiscuss, user.getName(), new java.sql.Date(date.getTime()));

        try {
            if (discussImpl.add(discuss)) {
                response.getWriter().write("添加成功");
            } else {
                response.getWriter().write("添加失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
