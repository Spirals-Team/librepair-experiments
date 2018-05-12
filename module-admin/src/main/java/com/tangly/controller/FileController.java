package com.tangly.controller;

import com.tangly.bean.ResponseBean;
import com.tangly.util.WebUploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * date: 2018/1/17 16:37 <br/>
 *
 * @author tangly
 * @since JDK 1.7
 */
@Controller
@Slf4j
@Api(description = "文件模块")
@RequestMapping(value = "/file")
public class FileController {

    private String attachPath = "attach";

    @ApiOperation(value = "后台删除文件", notes = "传文件的相对路径")
    @PostMapping
    @ResponseBody
    public ResponseBean delete(@RequestParam("filename") String filename) throws IOException {
        return WebUploadUtil.deleteFile("." + filename);
    }

    /**
     * 后台上传
     *
     * @param file     上传的文件
     * @param path     文件上传指定根目录下的目录
     * @param response
     * @param request
     * @throws IOException
     */
    @ApiOperation(value = "后台上传文件", notes = "后台上传文件")
    @PostMapping(value = "/{type}")
    @ResponseBody
    public ResponseBean uploadFile(
            @PathVariable("type") String type,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "path", defaultValue = "/") String path,
            HttpServletResponse response, HttpServletRequest request) throws IOException {

        response.setContentType("text/html; charset=UTF-8");

        List<Map<String, String>> rt = new ArrayList<>();

        rt.add(upload(type, path, file));

        return ResponseBean.success(rt);
    }

    private Map upload(String type, String path, MultipartFile file) {
        //创建文件名称
        String uuid = WebUploadUtil.createFileName();
        //扩展名
        String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();

        // 格式化保存路径
        if (StringUtils.isNotBlank(path)) {
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            if(!path.endsWith("/")){
                path = path + "/";
            }
        }

        //附件路径+类型（头像、附件等）+名称+扩展名
        String savePath = attachPath + path + type + "/" + uuid + "." + fileExt;
        //保存到磁盘
        File localFile = WebUploadUtil.saveFileToDisk(file, savePath);

        //如果是图片类型，则创建缩略图
        String thumbnailName = "";
        if (WebUploadUtil.getImageFormat(fileExt)) {
            //创建缩略图
            thumbnailName = attachPath + path + "/" + type + "/s/" + uuid + "." + fileExt;
            WebUploadUtil.createThumbnail(localFile, thumbnailName);
        }

        Map<String, String> rt = new HashMap<>(5);
        rt.put("uuid", uuid);
        rt.put("path", attachPath);
        rt.put("ext", fileExt);
        rt.put("url", "/" + savePath);
        rt.put("s_url", "/" + thumbnailName);

        log.info("上传的文件地址为 fileName={}", savePath);
        return rt;
    }


}
