package com.system.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 类描述：
 *      自定义异常处理
 * @ClassName RException
 * @Description TODO
 * @Author Ming
 * @Date 2022/10/29 14:35
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RException extends RuntimeException{
    private Integer code;
    private String msg;

}
