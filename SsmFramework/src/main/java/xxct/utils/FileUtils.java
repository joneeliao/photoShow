package xxct.utils;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;


/**
 * Created by Leo on 18/4/1.
 */
public class FileUtils {
    static Logger logger = Logger.getLogger(FileUtils.class);

    public static int saveFileToRoot(MultipartFile file, String path) {
        String pic_path;
        if ("".equals(path)) {
            return -1;
        }

        pic_path = path + "images/";

        String fileName = file.getOriginalFilename();

        logger.info("上传图片的路径：" + pic_path + fileName);

        System.out.println("上传图片的路径：" + pic_path + fileName);

        File newFile = new File(pic_path + fileName);

        try {
            file.transferTo(newFile);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }
}
