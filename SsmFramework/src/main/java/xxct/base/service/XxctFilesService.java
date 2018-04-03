package xxct.base.service;

import org.springframework.web.multipart.MultipartFile;
import xxct.base.domain.User;
import xxct.base.domain.XxctFiles;
import xxct.utils.ResponseData;

import java.util.List;
import java.util.Map;

/**
 * @author 1034683568@qq.com
 * @project_name ssm-maven
 * @date 2017-3-1
 */
public interface XxctFilesService {

    public List<XxctFiles> selectFiles(XxctFiles dto);

    public int updateFile(XxctFiles dto);

    public ResponseData addFile(Map<String, String> descMap, Map<String, MultipartFile> fileMap,
                                String fromLocation, String path);

    public int deleteFile(Integer id);
}
