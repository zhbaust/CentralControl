package com.aust.bean;

import java.util.*;
public class Supplier{
     private int id;//编号
     private String name;//名称
     private String address;//地址
     private String tel;//电话
     private String email;//邮箱
     private String qq;//QQ
     private String wechat;//微信
     private String linkman;//联系人
     private String remark;//备注
     public int getId(){
         return id;
     }
     public void setId(int id){
         this.id = id;
     }
     public String getName(){
         return name;
     }
     public void setName(String name){
         this.name = name;
     }
     public String getAddress(){
         return address;
     }
     public void setAddress(String address){
         this.address = address;
     }
     public String getTel(){
         return tel;
     }
     public void setTel(String tel){
         this.tel = tel;
     }
     public String getEmail(){
         return email;
     }
     public void setEmail(String email){
         this.email = email;
     }
     public String getQq(){
         return qq;
     }
     public void setQq(String qq){
         this.qq = qq;
     }
     public String getWechat(){
         return wechat;
     }
     public void setWechat(String wechat){
         this.wechat = wechat;
     }
     public String getLinkman(){
         return linkman;
     }
     public void setLinkman(String linkman){
         this.linkman = linkman;
     }
     public String getRemark(){
         return remark;
     }
     public void setRemark(String remark){
         this.remark = remark;
     }
}