package servlet;


import dao.LikeWeiboImpl;

import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
/**
 * 该接口实现点赞功能
 *
 *
 */

@WebServlet("/weibo/AddLikeServlet")
public class AddLikeServlet extends HttpServlet {

    LikeWeiboImpl likeWeiboImpl;

    public void init() {
        likeWeiboImpl = new LikeWeiboImpl();
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

        if (AddAttentionsServlet.isUserNULL(response, user)) return;

        int weiboId = Integer.parseInt(request.getParameter("weiboId"));

        try {
            if(request.getParameter("weiboId") ==null) return;

            if (likeWeiboImpl.addLike(user.getName(), weiboId)) {
                response.getWriter().write("成功");
            } else {
                response.getWriter().write("失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
