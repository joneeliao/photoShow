package xxct.base.domain;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Date;

/**
 * Created by Leo on 18/3/31.
 */
public class XxctFiles {
    private Long picId;
    private String fileName;
    private String fileType;
    private String fileTitle;
    private String fileDescription;
    private String fileLocation;
    private Date creationDate;
    private String createdBy;
    private String fileFrom;

    public String toString() {
        return "File: id[" + picId + "] fileName[" + fileName + "] fileType[" + fileType +
                "] fileTitle[" + fileTitle + "] fileDescription[" + fileDescription + "]";
    }

    public Long getPicId() {
        return picId;
    }

    public void setPicId(Long picId) {
        this.picId = picId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileTitle() {
        return fileTitle;
    }

    public void setFileTitle(String fileTitle) {
        this.fileTitle = fileTitle;
    }

    public String getFileDescription() {
        return fileDescription;
    }

    public void setFileDescription(String fileDescription) {
        this.fileDescription = fileDescription;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getFileFrom() {
        return fileFrom;
    }

    public void setFileFrom(String fileFrom) {
        this.fileFrom = fileFrom;
    }

}
