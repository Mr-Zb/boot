package com.base.boot.common.utils;

import lombok.Data;

/**
 * 异常处理实体包装类
 *
 * @author: wujian
 * @time: 2019/11/9
 */
@Data
public class Result<T> {
    /**
     * 返回码
     */
    private Integer code;
    /**
     * 返回消息
     */
    private String msg;
    /**
     * 返回数据
     */
    private T data;
}
