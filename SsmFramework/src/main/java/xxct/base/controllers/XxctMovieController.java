package xxct.base.controllers;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import xxct.base.domain.User;
import xxct.base.domain.XxctFiles;
import xxct.base.service.XxctFilesService;
import xxct.utils.ResponseData;
import xxct.utils.ResponseUtil;
import xxct.utils.XxctHttpRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @project_name ssm-maven
 * @date 2017-3-1
 */
@Controller
public class XxctMovieController {

    private static final Logger log = Logger.getLogger(XxctMovieController.class);// 日志文件

    private boolean validateSession(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        Object object = request.getSession().getAttribute("isLogin");
        if (user == null || (object == null)) {
            //return "redirect:/login.html";
            return false;
        }
        return true;
    }

    /**
     * @return
     * @throws Exception
     */
    @RequestMapping("/movies/douban")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String url = "https://api.douban.com/v2/movie/in_theaters?start=0&count=6";
        String result = XxctHttpRequest.sendGet(url, null, "utf-8");
        ResponseUtil.write(response, result);
    }


}
