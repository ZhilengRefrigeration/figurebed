package top.shenyuge.figurebed.satoken;

import cn.dev33.satoken.exception.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;
import top.shenyuge.figurebed.util.Result;

/**
 * @author 制冷
 * @date 2024/3/31 17:38
 * @description SaTokenExceptionHandler
 */
@RestControllerAdvice
public class SaTokenExceptionHandler {

    @ExceptionHandler(NotLoginException.class)
    public Result<String> handlerNotLoginException(NotLoginException nle) {
        // 不同异常返回不同状态码
        String message = "";
        if (nle.getType().equals(NotLoginException.NOT_TOKEN)) {
            message = "未提供Token";
        } else if (nle.getType().equals(NotLoginException.INVALID_TOKEN)) {
            message = "未提供有效的Token";
        } else if (nle.getType().equals(NotLoginException.TOKEN_TIMEOUT)) {
            message = "登录信息已过期，请重新登录";
        } else if (nle.getType().equals(NotLoginException.BE_REPLACED)) {
            message = "您的账户已在另一台设备上登录，如非本人操作，请立即修改密码";
        } else if (nle.getType().equals(NotLoginException.KICK_OUT)) {
            message = "已被系统强制下线";
        } else {
            message = "当前会话未登录";
        }
        // 返回给前端
        return Result.error(401, message);
    }

    @ExceptionHandler
    public Result<String> handlerNotRoleException(NotRoleException e) {
        return Result.error(403, "无此角色：" + e.getRole());
    }

    @ExceptionHandler
    public Result<String> handlerNotSafeException(NotSafeException e) {
        return Result.error(401, "二级认证异常：" + e.getMessage());
    }

    @ExceptionHandler(MultipartException.class)
    public Result<String> handleMultipartException(MultipartException ex) {
        return Result.error(500, "你没有上传文件");
    }

}
