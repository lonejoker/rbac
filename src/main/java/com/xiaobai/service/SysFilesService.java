package com.xiaobai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaobai.entity.SysFiles;
import com.xiaobai.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author 终于白发始于青丝
 * @create 2022-03-23 13:43:12
 * @Version 1.0
 * @ClassName SysFiles
 * @Description 类方法说明：
 */
public interface SysFilesService extends IService<SysFiles> {

    Object uploadFile(MultipartFile file) throws IOException;

    void downloadFile(String fileUUID, HttpServletResponse response) throws IOException;

    R sysFilesPage(Integer pageNum, Integer pageSize, String name);

    R getPages();

    R delSysFiles(Long id);

    R delSysFilesAll(List<Long> ids);

    R getFilesByType(Integer pageNum, Integer pageSize, String type, String name);

    R getTypeList();

    R updateFileStatus(Long id, String enable);
}
