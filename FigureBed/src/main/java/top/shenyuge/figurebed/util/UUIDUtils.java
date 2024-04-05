package top.shenyuge.figurebed.util;

import java.util.UUID;

/**
 * @author 制冷
 * @date 2024/3/25 13:42
 * @description UUIDUtils
 */
public class UUIDUtils {

    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }

}
