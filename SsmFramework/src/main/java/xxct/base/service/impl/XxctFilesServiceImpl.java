package xxct.base.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xxct.base.dao.XxctFilesMapper;
import xxct.base.domain.XxctFiles;
import xxct.base.service.XxctFilesService;
import xxct.utils.FileUtils;
import xxct.utils.ResponseData;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("xxctFilesService")
public class XxctFilesServiceImpl implements XxctFilesService {

    @Resource
    private XxctFilesMapper filesMapper;

    public List<XxctFiles> selectFiles(XxctFiles dto) {
        return filesMapper.selectFiles(dto);
    }

    public int updateFile(XxctFiles dto) {
        return filesMapper.updateFile(dto);
    }

    public ResponseData addFile(Map<String, String> descMap, Map<String, MultipartFile> fileMap,
                                String fromLocation, String path) {
        ResponseData responseData = new ResponseData();
        XxctFiles files;
        MultipartFile multipartFile;
        int cnt = 0;
        String title;
        int fileCreate;
        for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
            // 对文件进处理
            multipartFile = entry.getValue();
            if (!multipartFile.isEmpty()) {

                fileCreate = FileUtils.saveFileToRoot(multipartFile, path);
                if (fileCreate == 0) {
                    title = multipartFile.getOriginalFilename();
                    files = new XxctFiles();
                    files.setCreatedBy("-1");
                    files.setFileName(title.substring(0, title.indexOf(".")));
                    files.setFileTitle(title);
                    files.setFileType(multipartFile.getContentType());
                    files.setFileDescription(descMap.get(entry.getKey()));
                    files.setFileLocation("images/" + title);
                    files.setFileFrom(fromLocation);
                    System.out.println(files);
                    try {
                        filesMapper.addFile(files);
                        cnt = cnt + 1;
                    } catch (Exception e) {
                        responseData.setSuccess(false);
                        responseData.setMessage(e.getLocalizedMessage());
                    }
                } else {
                    responseData.setSuccess(false);
                    responseData.setMessage("Create File error.");
                }
            }
        }

        return responseData;
    }

    public int deleteFile(Integer id) {
        return filesMapper.deleteFile(id);
    }

}
