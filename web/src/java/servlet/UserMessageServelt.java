package servlet;

import dao.UserImpl;
import model.User;
import model.toJson.UserJson;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/weibo/UserMessageServelt")
public class UserMessageServelt extends HttpServlet {

    UserImpl userImpl;
    public void init() {
        userImpl = new UserImpl();
    }

    @Override
    public void doGet(HttpServletRequest requset, HttpServletResponse response) throws ServletException, IOException {
        doPost(requset, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        String userName = request.getParameter("username");
        response.setContentType("text/html;charset=UTF-8");
        //构建一个返回的用户json格式

        UserJson userJson =  new UserJson();
        userJson.setPhoto(userImpl.userMessage(userName).getPhoto());
        userJson.setName(userImpl.userMessage(userName).getName());
        userJson.setAttentions(userImpl.userMessage(userName).getAttentions());
        userJson.setFansNumber(userImpl.userMessage(userName).getFansNumber());
        userJson.setWeiboNumber(userImpl.userMessage(userName).getWeiboNumber());

        JSONObject returnUser = JSONObject.fromObject(userJson);

        try {
            response.getWriter().write(returnUser.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


