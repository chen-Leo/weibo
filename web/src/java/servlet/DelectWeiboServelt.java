package servlet;

import dao.WeiboContentImpl;
import model.Encrypt;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
/**
 * 该接口实现删微博功能
 *
 *
 */
@WebServlet("/weibo/DelectWeiboServlet")
public class DelectWeiboServelt extends HttpServlet {

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


        if (AddAttentionsServlet.isUserNULL(response, user)) return;
        int weiboId = Integer.parseInt(request.getParameter("weiboId"));

        try {
            if(request.getParameter("weiboId") == null){
                response.getWriter().write("非法输入");
                return;
            }

            if (!Encrypt.isRight(request.getParameter("weiboId")) ) {
                response.getWriter().write("非法输入");
                return;
            }

            //判断是否删的是本人的微博
            if (!weiboContentImpl.isMaster(user.getName(), weiboId)) {
                response.getWriter().write("删除失败,你没有权限");
                return;
            }
            if (weiboContentImpl.delete(weiboId)) {
                response.getWriter().write("删除成功");
            } else {
                response.getWriter().write("删除失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
