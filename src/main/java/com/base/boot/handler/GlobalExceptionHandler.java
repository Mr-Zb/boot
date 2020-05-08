package com.base.boot.handler;


import com.base.boot.common.enums.ResultEnum;
import com.base.boot.common.exception.JwtException;
import com.base.boot.common.exception.MyException;
import com.base.boot.common.utils.Result;
import com.base.boot.common.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.sql.SQLException;

/**
 * 统一异常处理类
 *
 * @author zbang
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 处理系统内部异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        logger.error(e.getMessage(), e);
        return ResultUtil.getError(ResultEnum.SYS_ERR_CODE.getCode(),
                ResultEnum.SYS_ERR_CODE.getMessage());
    }

    /**
     * 处理自定义异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MyException.class)
    public Result handleMyException(MyException e) {
        return ResultUtil.getError(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public Result handlerNoFoundException(Exception e) {
        logger.error(e.getMessage(), e);
        return ResultUtil.getError(ResultEnum.URL_NOT_EXIST.getCode(),
                ResultEnum.URL_NOT_EXIST.getMessage());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public Result handleDuplicateKeyException(DuplicateKeyException e) {
        logger.error(e.getMessage(), e);
        return ResultUtil.getError(ResultEnum.DATA_IS_EXIST.getCode(),
                ResultEnum.DATA_IS_EXIST.getMessage());
    }

    //	@ExceptionHandler(AuthorizationException.class)//AccessDeniedException
//	public R handleAuthorizationException(AuthorizationException e){
//		logger.error(e.getMessage(), e);
//		return R.error("没有权限，请联系管理员授权");
//	}
    @ExceptionHandler(JwtException.class)
    public Result handleAuthorizationException(JwtException e) {
        logger.error(e.getMessage(), e);
        return ResultUtil.getError(e.getCode(),
                e.getMsg());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Result handleAuthorizationException(AccessDeniedException e) {
        logger.error(e.getMessage(), e);
        return ResultUtil.getError(ResultEnum.USER_NO_ACCESS.getCode(),
                ResultEnum.USER_NO_ACCESS.getMessage());
    }

    //
    @ExceptionHandler(SQLException.class)
    public Result handleAuthorizationException(SQLException e) {
        logger.error(e.getMessage(), e);
        return ResultUtil.getError(ResultEnum.SQL_ERR_CODE.getCode(),
                ResultEnum.SQL_ERR_CODE.getMessage());
    }
}
