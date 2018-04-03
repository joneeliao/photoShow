package xxct.base.controllers;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import xxct.base.domain.PageBean;
import xxct.base.domain.User;
import xxct.base.domain.XxctFiles;
import xxct.base.service.UserService;
import xxct.base.service.XxctFilesService;
import xxct.utils.MD5Util;
import xxct.utils.ResponseData;
import xxct.utils.ResponseUtil;
import xxct.utils.StringUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @project_name ssm-maven
 * @date 2017-3-1
 */
@Controller
public class XxctFilesController {

    @Resource
    private XxctFilesService filesService;
    private static final Logger log = Logger.getLogger(XxctFilesController.class);// 日志文件

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
    @RequestMapping("/files/home")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (!validateSession(request, response)) {
            return "redirect:/login.html";
        }
        log.info("request: uploadFile");
        return "uploadFile/fileList";
    }

    @RequestMapping("/files/index")
    public String intoIndex(HttpServletRequest request,
                            HttpServletResponse response) throws Exception {

        if (!validateSession(request, response)) {
            return "redirect:/login.html";
        }

        XxctFiles dto = new XxctFiles();
        List<XxctFiles> xxctFilesList = filesService.selectFiles(dto);
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(xxctFilesList);
        result.put("rows", jsonArray);
        result.put("total", xxctFilesList.size());
        log.info("request: files/list , map: " + result.toString());

        request.getSession().setAttribute("result", xxctFilesList);
        return "redirect:/index.html";
    }

    @RequestMapping("/files/list")
    public void list(@RequestParam(value = "page", required = false) String page,
                     @RequestParam(value = "rows", required = false) String rows,
                     XxctFiles dto, HttpServletRequest request,
                     HttpServletResponse response) throws Exception {
        List<XxctFiles> filesList = filesService.selectFiles(dto);
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(filesList);
        result.put("rows", jsonArray);
        result.put("total", filesList.size());

        request.setAttribute("result", result);
        ResponseUtil.write(response, result);
    }


    @RequestMapping("/files/save")
    public void save(@RequestParam("desc1") String desc1, @RequestParam("desc2") String desc2,
                     @RequestParam("desc3") String desc3,
                     HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fromLocation;
        //request.getHeader("user-agent");
        if (request.getHeader("x-forwarded-for") == null)
            fromLocation = request.getRemoteAddr();
        else
            fromLocation = request.getHeader("x-forwarded-for");

        fromLocation = fromLocation + request.getHeader("user-agent");

        String path = request.getSession().getServletContext().getRealPath("");
        System.out.println(path);

        Map<String, String> descMap = new HashMap<String, String>();
        descMap.put("image1", desc1);
        descMap.put("image2", desc2);
        descMap.put("image3", desc3);

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
            // 对文件进处理
            System.out.println(entry.getKey() + ":" + entry.getValue().getOriginalFilename());
        }
        JSONObject result = new JSONObject();
        ResponseData responseData = filesService.addFile(descMap, fileMap, fromLocation, path);
        result.put("success", responseData.isSuccess());
        result.put("message", responseData.getMessage());
        ResponseUtil.write(response, result);
    }

    @RequestMapping("/files/update")
    public void updateFile(XxctFiles xxctFiles, HttpServletResponse response) throws Exception {
        System.out.println(xxctFiles.toString());
        JSONObject result = new JSONObject();
        try {
            filesService.updateFile(xxctFiles);
            result.put("success", true);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        ResponseUtil.write(response, result);
    }

    /**
     * @param ids
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/files/delete")
    public String delete(@RequestParam(value = "ids") String ids, HttpServletResponse response) throws Exception {
        JSONObject result = new JSONObject();
        String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; i++) {
            filesService.deleteFile(Integer.parseInt(idsStr[i]));
        }
        result.put("success", true);
        log.info("request: files/delete , ids: " + ids);
        ResponseUtil.write(response, result);
        return null;
    }

}
