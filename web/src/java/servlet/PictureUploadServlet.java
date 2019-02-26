package servlet;

        import dao.UserImpl;
        import model.User;
        import org.apache.commons.fileupload.FileItem;
        import org.apache.commons.fileupload.FileUploadException;
        import org.apache.commons.fileupload.disk.DiskFileItemFactory;
        import org.apache.commons.fileupload.servlet.ServletFileUpload;

        import java.util.UUID;
        import javax.servlet.ServletException;
        import javax.servlet.annotation.WebServlet;
        import javax.servlet.http.HttpServlet;
        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;
        import javax.servlet.http.HttpSession;
        import java.io.File;
        import java.io.IOException;

        import java.util.List;

@WebServlet("/weibo/PictureUploadServlet")
public class PictureUploadServlet extends HttpServlet {

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



        // 判断是普通表单，还是文件上传表单
        if (!ServletFileUpload.isMultipartContent(request)) {
            throw new RuntimeException("不是文件上传表单！");
        }

        // 创建上传所需要的两个对象
        DiskFileItemFactory factory = new DiskFileItemFactory();  // 磁盘文件对象
        ServletFileUpload sfu = new ServletFileUpload(factory);   // 文件上传对象



        // 设置解析文件上传中的文件名的编码格式
        sfu.setHeaderEncoding("utf-8");
        // 创建 list容器用来保存 表单中的所有数据信息
        List<FileItem> items = null;
        try {
            items = sfu.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        System.out.println(items.size());
        try {
            for (FileItem item : items) {
                // 处理 文件数据项 信息
                if (handleFileField(item, user.getName())) {
                    response.getWriter().write("上传成功");
                } else response.getWriter().write("上传失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 处理 文件数据项
     *
     * @param item
     */
    private boolean handleFileField(FileItem item, String userName) {
        // 获取 文件数据项中的 文件名
        String fileName = item.getName();
        String filePath = null;
        // 判断 此文件的文件名是否合法
        if (fileName == null || "".equals(fileName)) {
            return false;
        }

        // 控制只能上传图片
        if (!item.getContentType().startsWith("image")) {
            return false;
        }

        // 获取 当前项目下的 /files 目录的绝对位置
        String path = this.getServletContext().getRealPath("/files");
        File file = new File(path);   // 创建 file对象

        // 创建 /files 目录
        if (!file.exists()) {
            file.mkdir();
        }

        // 将文件保存到服务器上（UUID是通用唯一标识码，不用担心会有重复的名字出现）
        try {
            fileName = UUID.randomUUID() + "_" + fileName;
            filePath = "/files/" + fileName;
            //将用户的图片地址保存到数据库
            item.write(new File(file.toString(), fileName));


        } catch (Exception e) {
            e.printStackTrace();
        }
        if (userImpl.userPhotoAdd(filePath, userName)) return true;
        else return false;
    }

}