package xxct.base.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xxct.base.domain.PageBean;
import xxct.base.domain.User;
import xxct.base.service.UserService;
import xxct.utils.MD5Util;
import xxct.utils.ResponseUtil;
import xxct.utils.StringUtil;

/**
 * @author
 * @project_name ssm-maven
 * @date 2017-3-1
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;
    private static final Logger log = Logger.getLogger(UserController.class);// 日志文件

    /**
     * 登录
     *
     * @param user
     * @param request
     * @return
     */
    @RequestMapping("/login")
    public String login(User user, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println(user.toString());
        JSONObject result = new JSONObject();
        User userSession = (User) request.getSession().getAttribute("user");
        Object object = request.getSession().getAttribute("isLogin");
        if (userSession != null && (object != null)) {
            //return "redirect:/login.html";
            result.put("success", true);
            ResponseUtil.write(response, result);
            return result.toString();
        }

        try {
            String MD5pwd = MD5Util.MD5Encode(user.getPassword(), "UTF-8");
            System.out.println("MD5pwd:" + MD5pwd);
            user.setPassword(MD5pwd);
        } catch (Exception e) {
            user.setPassword("");
        }
        User resultUser = userService.login(user);


        if (resultUser == null) {
            result.put("success", false);
            result.put("message", "请认真核对账号、密码！");
            ResponseUtil.write(response, result);
            return result.toString();
        } else {
            result.put("success", true);
            request.getSession().setAttribute("user", resultUser);
            request.getSession().setAttribute("isLogin", true);
            ResponseUtil.write(response, result);
            return result.toString();
        }
    }


    /**
     * 修改密码
     *
     * @param user
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/modifyPassword")
    public String modifyPassword(User user, HttpServletResponse response) throws Exception {
        String MD5pwd = MD5Util.MD5Encode(user.getPassword(), "UTF-8");
        user.setPassword(MD5pwd);
        int resultTotal = userService.updateUser(user);
        JSONObject result = new JSONObject();
        if (resultTotal > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        log.info("request: user/modifyPassword , user: " + user.toString());
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 退出系统
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session) throws Exception {
        session.invalidate();
        log.info("request: user/logout");
        return "redirect:/login.jsp";
    }

    /**
     * @param page
     * @param rows
     * @param s_user
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/list")
    public String list(@RequestParam(value = "page", required = false) String page, @RequestParam(value = "rows", required = false) String rows, User s_user, HttpServletResponse response) throws Exception {
        PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userName", StringUtil.formatLike(s_user.getUserName()));
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        List<User> userList = userService.findUser(map);
        Long total = userService.getTotalUser(map);
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(userList);
        result.put("rows", jsonArray);
        result.put("total", total);
        log.info("request: user/list , map: " + map.toString());
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 添加或修改管理员
     *
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    public String save(User user, HttpServletResponse response) throws Exception {
        int resultTotal = 0;
        String msg = "";
        System.out.println(user.toString());
        List<User> userList;
        Long total;
        JSONObject result = new JSONObject();
        try {
            String MD5pwd = MD5Util.MD5Encode(user.getPassword(), "UTF-8");
            user.setPassword(MD5pwd);
            resultTotal = userService.addUser(user);
        } catch (Exception e) {
            System.out.println(1111);
            msg = e.getMessage();
            e.printStackTrace();
        }

        if (resultTotal > 0) {
            result.put("success", true);
        } else {
            result.put("error", false);
            result.put("msg", msg);
        }
        log.info("request: user/save , user: " + user.toString());
        response.sendRedirect("/index");
        //ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 删除管理员
     *
     * @param ids
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    public String delete(@RequestParam(value = "ids") String ids, HttpServletResponse response) throws Exception {
        JSONObject result = new JSONObject();
        String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; i++) {
            userService.deleteUser(Integer.parseInt(idsStr[i]));
        }
        result.put("success", true);
        log.info("request: user/delete , ids: " + ids);
        ResponseUtil.write(response, result);
        return null;
    }

    @RequestMapping("/helloworld")
    public String hello(User user, HttpServletRequest request) {
        System.out.println("hello world");

        return "success";
    }
}
