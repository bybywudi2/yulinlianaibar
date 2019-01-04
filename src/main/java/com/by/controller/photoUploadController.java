package com.by.controller;

import Utils.CommonUtils;
import com.by.bean.User;
import com.by.bean.WxUser;
import com.by.dao.UserMapper;
import com.by.dao.WxUserLoginMapper;
import com.by.dao.WxUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/regist")
public class photoUploadController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WxUserLoginMapper wxUserLoginMapper;
    @Autowired
    private WxUserMapper wxUserMapper;
    /**
     * 图片文件上传
     */
    @ResponseBody
    @RequestMapping(value = "/photoupload",method = RequestMethod.POST)
    public void photoUpload(MultipartFile file, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IllegalStateException, IOException {
        //Map resultData = new HashMap();
        String openid = request.getParameter("openid");
        String photoType = request.getParameter("type");
        System.out.println("type====================="+photoType);
        if (file!=null) {// 判断上传的文件是否为空
            String path=null;// 文件路径
            String type=null;// 文件类型
            String fileName=file.getOriginalFilename();// 文件原名称
            System.out.println("上传的文件原名称:"+fileName);
            // 判断文件类型
            type=fileName.indexOf(".")!=-1?fileName.substring(fileName.lastIndexOf(".")+1, fileName.length()):null;
            if (type!=null) {// 判断文件类型是否为空
                if ("GIF".equals(type.toUpperCase())||"PNG".equals(type.toUpperCase())||"JPG".equals(type.toUpperCase())) {
                    // 项目在容器中实际发布运行的根路径
                    String realPath = CommonUtils.getSavePath(openid);
                    // 自定义的文件名称
                    //String trueFileName = String.valueOf(System.currentTimeMillis())+fileName;
                    //String trueFileName = fileName;
                    // 设置存放图片文件的路径
                    path = realPath + fileName;
                    System.out.println("存放图片文件的路径:"+path);
                    // 转存文件到指定的路径
                    file.transferTo(new File(path));

                    WxUser wxUser = wxUserLoginMapper.selectByOpenid(openid);
                    Long userId = wxUser.getUserId();
                    System.out.println("userID==========================="+userId);
                    if(userId != null){
                        User user = userMapper.selectByPrimaryKey(userId);
                        if(photoType.equals("photo_others")){
                            user.setPhotoOthers(path);
                        }else{
                            user.setPhotoSelf(path);
                        }
                        user.setModifyTime(new Date().getTime());
                        userMapper.updateByPrimaryKeySelective(user);
                    }

                }else {
                    System.out.println("不是我们想要的文件类型,请按要求重新上传");
                    return;
                }
            }else {
                System.out.println("文件类型为空");
                return;
            }
        }else {
            System.out.println("没有找到相对应的文件");
            return;
        }
        return;
    }
}