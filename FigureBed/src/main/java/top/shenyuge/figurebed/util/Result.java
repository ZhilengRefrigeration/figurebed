package top.shenyuge.figurebed.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author 制冷
 * @date 2023/5/10 14:51
 * @description Result
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<E> {
    private Integer code;//响应码，1 代表成功; 0 代表失败
    private String msg;  //响应信息 描述字符串
    private E data; //返回的数据

    //增删改 成功响应
    public static Result<String> success() {
        return new Result<>(0, "success", null);
    }

    //查询 成功响应
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "success", data);
    }

    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(200, msg, data);
    }

    //失败响应
    public static <T> Result<T> error(int code, String msg) {
        return new Result<>(code, msg, null);
    }

    //http:ok
    public static ResponseEntity<InputStreamResource> requestFileSuccessful(InputStreamResource file) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(file, headers, HttpStatus.OK);
    }

    public static ResponseEntity<InputStreamResource> requestFileSuccessfulIsFile(InputStreamResource file, String filename) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        String encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8);
        headers.setContentDispositionFormData("attachment", encodedFilename);
        return new ResponseEntity<>(file, headers, HttpStatus.OK);
    }

    //http:没有文件
    public static ResponseEntity<InputStreamResource> requestNoFile() {
        return ResponseEntity.notFound().build();
    }

    // http:服务器错误
    public static ResponseEntity<InputStreamResource> requestServerError() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


}
