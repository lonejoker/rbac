package com.xiaobai.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaobai.entity.LoginUser;
import com.xiaobai.entity.SysUser;
import com.xiaobai.enumeration.RInfo;
import com.xiaobai.mapper.SysFilesMapper;
import com.xiaobai.entity.SysFiles;
import com.xiaobai.mapper.SysUserMapper;
import com.xiaobai.service.SysFilesService;
import com.xiaobai.utils.JwtUtil;
import com.xiaobai.utils.R;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.*;

/**
 * @author 终于白发始于青丝
 * @create 2022-03-23 13:43:12
 * @Version 1.0
 * @ClassName SysFiles
 * @Description 类方法说明：
 */
@Service
public class SysFilesServiceImpl extends ServiceImpl<SysFilesMapper, SysFiles> implements SysFilesService {

    @Autowired
    private SysFilesMapper sysFilesMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Value("${files.upload.path}")
    private String fileUploadPath;
    @Value("${server.url}")
    private String myurl;
    @Value("${server.port}")
    private Integer port;

    @Override
    public Object uploadFile(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        String type = FileUtil.extName(filename);
        long size = file.getSize();
        // 定义一个文件唯一的标识码
        String uuid = IdUtil.fastSimpleUUID();
        String fileUUID = uuid + StrUtil.DOT + type;
        File uploadFile = new File(fileUploadPath + fileUUID);
        // 判断配置的文件目录是否存在，若不存在则创建一个新的文件目录
        File parentFile = uploadFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        String url;
        // 获取文件的md5
        String md5 = SecureUtil.md5(file.getInputStream());
        // 从数据库查询是否存在相同的记录
        SysFiles files = getFileMd5(md5);
        // 文件已存在
        if (files != null) {
            url = files.getUrl();
            uploadFile.delete();
            return R.error(RInfo.ERROR412.getCode(), RInfo.ERROR412.getMsg());
        } else {
            // 上传文件到磁盘
            file.transferTo(uploadFile);
            // 数据库若不存在重复文件，则不删除刚才上传的文件
            url = myurl + port + "/sysFiles/download/" + fileUUID;
        }
        // 存储数据库
        SysFiles sysFiles = new SysFiles();
        sysFiles.setName(FileUtil.getPrefix(filename));
        sysFiles.setType(type);
        sysFiles.setSize(size / 1024);
        sysFiles.setUrl(url);
        sysFiles.setMd5(md5);
        sysFiles.setCreateTime(DateUtil.date());
        sysFilesMapper.insert(sysFiles);
        return url;
    }

    @Override
    public void downloadFile(String fileUUID, HttpServletResponse response) throws IOException {
        // 根据文件的唯一标识码获取文件
        File uploadFile = new File(fileUploadPath + fileUUID);
        // 设置输出流的格式
        ServletOutputStream outputStream = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUUID, "UTF-8"));
        response.setContentType("application/octet-stream");
        // 读取文件的字节流
        outputStream.write(FileUtil.readBytes(uploadFile));
        outputStream.flush();
        outputStream.close();
    }

    @Override
    public R sysFilesPage(Integer pageNum, Integer pageSize, String name) {
        LambdaQueryWrapper<SysFiles> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysFiles::getDelFlag, 0);
        Page<SysFiles> page = new Page<>(pageNum, pageSize);
        page(page, wrapper);
        return R.successCmd(page);
    }

    @Override
    public R getPages() {
        LambdaQueryWrapper<SysFiles> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysFiles::getDelFlag, 0);
        return R.successCmd(sysFilesMapper.selectList(wrapper));
    }

    @Override
    public R delSysFiles(Long id) {
        SysFiles sysFiles = sysFilesMapper.selectById(id);
        sysFiles.setDelFlag(1);
        return R.successCmd(sysFilesMapper.updateById(sysFiles));
    }

    @Override
    public R delSysFilesAll(List<Long> ids) {
        QueryWrapper<SysFiles> wrapper = new QueryWrapper<>();
        wrapper.in("id", ids);
        for (SysFiles sysFiles : sysFilesMapper.selectList(wrapper)) {
            sysFiles.setDelFlag(1);
            sysFilesMapper.updateById(sysFiles);
        }
        return R.successCm();
    }

    @Override
    public R getFilesByType(Integer pageNum, Integer pageSize, String type, String name) {
        LambdaQueryWrapper<SysFiles> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysFiles::getDelFlag, 0)
                .eq(SysFiles::getType, type)
                .like(SysFiles::getName, name);
        Page<SysFiles> page = new Page<>(pageNum, pageSize);
        page(page, wrapper);
        return R.successCmd(page);
    }

    @Override
    public R getTypeList() {
        QueryWrapper<SysFiles> wrapper = new QueryWrapper<>();
        wrapper.select("DISTINCT type");
        List<SysFiles> sysFiles1 = sysFilesMapper.selectList(wrapper);
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < sysFiles1.size(); i++) {
            Map<Object, Object> map = new HashMap<>();
            map.put("label", sysFiles1.get(i).getType());
            map.put("name", sysFiles1.get(i).getType());
            list.add(map);
        }
        return R.successCmd(list);
    }

    @Override
    public R updateFileStatus(Long id, String enable) {
        SysFiles sysFiles = new SysFiles();
        sysFiles.setId(id);
        sysFiles.setEnable(enable);
        return R.successCmd(sysFilesMapper.updateById(sysFiles));
    }


    private SysFiles getFileMd5(String md5) {
        // 查询文件的md5是否存在
        QueryWrapper<SysFiles> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5", md5);
        List<SysFiles> sysFiles = sysFilesMapper.selectList(queryWrapper);
        return sysFiles.size() == 0 ? null : sysFiles.get(0);
    }
}
