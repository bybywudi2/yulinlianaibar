package com.by.cronjobs;

import com.by.bean.WxUser;
import com.by.dao.MatchingMapper;
import com.by.dao.UserMapper;
import com.by.dao.WxUserLoginMapper;
import com.by.dao.WxUserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class DoMatch {
    @Autowired
    private WxUserLoginMapper wxUserLoginMapper;
    @Autowired
    private WxUserMapper wxUserMapper;
    @Autowired
    private MatchingMapper matchingMapper;
    @Autowired
    private UserMapper userMapper;

    public void doMatch(){
        List<WxUser> maleList = matchingMapper.selectMatchReadyMaleUsers();
        List<WxUser> femaleList = matchingMapper.selectMatchReadyFemaleUsers();
        WxUser u1;
        WxUser u2;
        if(maleList.size() > 0 && femaleList.size() > 0){
            if(maleList.size() > femaleList.size()){
                int max = maleList.size();
                int[] arr = new int[max];
                for(int i=0;i<max;i++){
                    arr[i] = i;
                }
                for(int i=0;i<max/2;i++){
                    int randomNumber = (int) Math.round(Math.random()*max);
                    swap(arr,i,randomNumber);
                }
                for(int i=0;i<femaleList.size();i++){
                    u1 = femaleList.get(i);
                    u2 = maleList.get(arr[i]);
                    u1.setMatchingUserId(u2.getId());
                    u2.setMatchingUserId(u1.getId());
                    u1.setShouldInitTime(new Date().getTime() + 24*60*60*1000);
                    u2.setShouldInitTime(new Date().getTime() + 24*60*60*1000);
                    wxUserMapper.updateByPrimaryKeySelective(u1);
                    wxUserMapper.updateByPrimaryKeySelective(u2);
                    System.out.println("match"+"u1="+u1.getId()+"u2="+u2.getId());
                }

            }else{
                int max = femaleList.size();
                int[] arr = new int[max];
                for(int i=0;i<max;i++){
                    arr[i] = i;
                }
                for(int i=0;i<max/2;i++){
                    int randomNumber = (int) Math.round(Math.random()*max);
                    swap(arr,i,randomNumber);
                }
                for(int i=0;i<maleList.size();i++){
                    u1 = maleList.get(i);
                    u2 = femaleList.get(arr[i]);
                    u1.setMatchingUserId(u2.getId());
                    u2.setMatchingUserId(u1.getId());
                    u1.setShouldInitTime(new Date().getTime() + 24*60*60*1000);
                    u2.setShouldInitTime(new Date().getTime() + 24*60*60*1000);
                    wxUserMapper.updateByPrimaryKeySelective(u1);
                    wxUserMapper.updateByPrimaryKeySelective(u2);
                    System.out.println("match"+"u1="+u1.getId()+"u2="+u2.getId());
                }
            }
        }else{
            System.out.println("no more user");
        }

    }

    public void swap(int[] arr,int i,int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}