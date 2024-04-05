package top.shenyuge.figurebed.util;

import top.shenyuge.figurebed.bean.PageBean;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author 制冷
 * @date 2024/3/25 13:42
 * @description FileNameUtils
 */
public class FileNameUtils {

    /**
     * 获取文件后缀
     * @param fileName
     * @return
     */
    public static String getSuffix(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成新的文件名
     * @param fileOriginName 源文件名
     * @return
     */
    public static String getFileName(String fileOriginName){
        return UUIDUtils.getUUID() + FileNameUtils.getSuffix(fileOriginName);
    }

    /**
     * 图片分页查询方法
     * @param imgList 包含要被查询的图片集合
     * @param page 分页和搜索信息
     * @param url 要拼接的地址
     * @return 拼接后的查询结果
     */
    public static Set<String> searchImg(TreeSet<String> imgList, PageBean page, String url){
        //没有搜索
        if(null == page.getSearch() || page.getSearch().isEmpty()){
            return imgList.stream()
                    .skip(((long) page.getPageNum() *page.getPageSize()-page.getPageSize()))
                    .limit(page.getPageSize())
                    .map(name->url+name)
                    .collect(Collectors.toSet());
        }else{
            //根据图片名模糊搜索
            return imgList.stream()
                    .filter(name-> name.contains(page.getSearch()))
                    .skip(((long) page.getPageNum() *page.getPageSize()-page.getPageSize()))
                    .map(name->url+name)
                    .collect(Collectors.toSet());
        }
    }

}