package servlet;

import dao.LikeUserImpl;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/weibo/AddAttentionsServlet")
public class AddAttentionsServlet extends HttpServlet {
    LikeUserImpl likeUserImpl;

    public void init() {
        likeUserImpl = new LikeUserImpl();
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

        if (user == null) {
            try {
                response.getWriter().print("您还未登陆，请<a href='/weibo/login.html'>登陆</a>");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String userLikeName = request.getParameter("userLikeName");

        try {
            if (likeUserImpl.addLike("1", userLikeName)) {
                response.getWriter().write("成功");
            } else {
                response.getWriter().write("失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}


