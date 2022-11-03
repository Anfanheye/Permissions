package com.system.exception;

import com.common.result.Result;
import com.common.result.ResultCodeEnum;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 类描述：
 *       全局异常处理器
 * @ClassName GlobalExceptionHandler
 * @Description TODO
 * @Author Ming
 * @Date 2022/10/29 14:26
 * @Version 1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    //1.全局异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        return Result.fail().message("执行了全局处理异常");
    }

    //2.特定异常处理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error(ArithmeticException e){
        return Result.fail().message("执行了特定异常处理");
    }

    //2.自定义异常处理
    @ExceptionHandler(RException.class)
    @ResponseBody
    public Result error(RException e){
        return Result.fail().code(e.getCode()).message(e.getMsg());
    }

    /**
     * spring security异常
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public Result error(AccessDeniedException e) throws AccessDeniedException {
        return Result.fail().code(ResultCodeEnum.PERMISSION.getCode()).message("没有当前功能操作权限");
    }
}
