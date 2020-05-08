package com.base.boot.common.exception;

import com.base.boot.common.enums.ResultEnum;
import lombok.Data;

/**
 * 自定义异常，为了区分系统异常和更方便系统的特定一些处理
 * 在系统中，存在着系统异常和我们人为的自定义异常，
 * 所以，为了能够有效的针对不同异常进行处理，那么拥有我们自定义的异常类是非常有必要的。
 *
 * @author:
 * @time: 2019/11/9
 */
@Data
public class MyException extends RuntimeException {
    /**
     * 错误码
     */
    private Integer code;

    public MyException(String message) {
        super(message);
    }

    /**
     * 构造器重载，主要是自己考虑某些异常自定义一些返回码
     *
     * @param code
     * @param message
     */
    public MyException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 构造器重载
     *
     * @param resultEnum
     */
    public MyException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
