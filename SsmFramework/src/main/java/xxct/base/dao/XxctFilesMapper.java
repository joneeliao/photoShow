package xxct.base.dao;

import org.springframework.stereotype.Repository;
import xxct.base.domain.XxctFiles;
import java.util.List;

/**
 * @author
 * @project_name ssm-maven
 * @date 2017-3-1
 */
@Repository
public interface XxctFilesMapper {

    public List<XxctFiles> selectFiles(XxctFiles dto);

    public int updateFile(XxctFiles dto);

    public int addFile(XxctFiles dto);

    public int deleteFile(Integer id);
}
