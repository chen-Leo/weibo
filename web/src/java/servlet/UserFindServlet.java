package servlet;

import dao.UserImpl;
import model.User;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

import java.util.ArrayList;

@WebServlet("/weibo/UserFindServelt")
public class UserFindServlet extends HttpServlet {

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

        String userNameFind = request.getParameter("usernameFind");

        ArrayList<User> users = new ArrayList<>(userImpl.userFind(userNameFind));
        JSONArray returnJsons = JSONArray.fromObject(users);


        try {
            response.getWriter().write(returnJsons.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
