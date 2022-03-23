package com.xiaobai.controller;

import com.xiaobai.entity.SysFiles;
import com.xiaobai.service.SysFilesService;
import com.xiaobai.utils.R;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
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
@RestController
@RequestMapping("/sysFiles")
public class SysFilesController {
    @Autowired
    private SysFilesService sysFilesService;

    @PostMapping("/uploadFile")
    public Object uploadFile(@RequestParam MultipartFile file) throws IOException {
        return sysFilesService.uploadFile(file);
    }

    @GetMapping(value = "/download/{fileUUID}", produces = "application/octet-stream")
    public void downloadFile(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        sysFilesService.downloadFile(fileUUID, response);
    }

    @GetMapping("/page")
    public R sysFilesPage(@RequestParam(required = false) Integer pageNum, @RequestParam(required = false) Integer pageSize,
                          @RequestParam(required = false, defaultValue = "") String name) {
        return sysFilesService.sysFilesPage(pageNum, pageSize, name);
    }

    @GetMapping("/getSysFilesInfo")
    public R getPages() {
        return sysFilesService.getPages();
    }

    @DeleteMapping("/delSysFiles/{id}")
    public R delSysFiles(@PathVariable Long id) {
        return sysFilesService.delSysFiles(id);
    }

    @PostMapping("/delSysFilesAll/{ids}")
    public R delSysFilesAll(@PathVariable List<Long> ids) {
        return sysFilesService.delSysFilesAll(ids);
    }

    @GetMapping("/getFilesByType")
    public R getFilesByType(@RequestParam(required = false) Integer pageNum, @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) String type,
                             @RequestParam(required = false, defaultValue = "") String name) {
        return sysFilesService.getFilesByType(pageNum, pageSize, type, name);
    }

    @GetMapping("/getTypeList")
    public R getTypeList() {
        return sysFilesService.getTypeList();
    }

    @PutMapping("/updateStatus")
    public R updateFileStatus(@RequestParam Long id, @RequestParam String enable) {
        return sysFilesService.updateFileStatus(id, enable);
    }

}
