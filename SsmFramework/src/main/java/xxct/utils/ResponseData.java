package xxct.utils;

import java.util.List;

/**
 * Created by Leo on 18/4/1.
 */
public class ResponseData {
    private String message;
    private boolean success;
    private Long total;
    private List<?> list;
    private String code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ResponseData() {
        this.success = true;
    }

    public ResponseData(boolean success) {
        this.success = true;
        this.setSuccess(success);
    }

    public ResponseData(List<?> list) {
        this(true);
        this.setList(list);
    }
}
