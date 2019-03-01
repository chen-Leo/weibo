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

import java.util.Date;
/**
 * 该接口实现评论功能
 *
 *
 */
@WebServlet("/weibo/AddDiscussServlet")
public class AddDiscussServlet extends HttpServlet {

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
        if (AddAttentionsServlet.isUserNULL(response, user)) return;

        if (request.getParameter("mainDiscuss") == null){
            return;
        }


        int weiboId = Integer.parseInt(request.getParameter("weiboId"));
        String mainDiscuss = request.getParameter("mainDiscuss");
        response.setContentType("text/html;charset=UTF-8");



        Date date = new Date();//获取系统当前时间
        Discuss discuss = new Discuss(discussImpl.weiboIdMAx() + 1, weiboId, mainDiscuss, user.getName(), new java.sql.Date(date.getTime()));

        try {
            if (discussImpl.add(discuss)) {
                response.getWriter().write("评论成功");
            } else {
                response.getWriter().write("评论失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
