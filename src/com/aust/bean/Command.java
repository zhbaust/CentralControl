package com.aust.bean;

import java.util.*;
public class Command{
     private int id;//编号
     private String title;//标题
     private String content;//内容
     private String publishtime;//发布时间
     private String deadline;//截止时间
     private int publisherid;//领取人员
     private int categoryid;//指令类别
     private String remark;//备注
     
     private Employee publisher;
     
     public Employee getPublisher() {
		return publisher;
	}
	public void setPublisher(Employee publisher) {
		this.publisher = publisher;
	}
	
	public int getId(){
         return id;
     }
     public void setId(int id){
         this.id = id;
     }
     public String getTitle(){
         return title;
     }
     public void setTitle(String title){
         this.title = title;
     }
     public String getContent(){
         return content;
     }
     public void setContent(String content){
         this.content = content;
     }
     public String getPublishtime(){
         return publishtime;
     }
     public void setPublishtime(String publishtime){
         this.publishtime = publishtime;
     }
     public String getDeadline(){
         return deadline;
     }
     public void setDeadline(String deadline){
         this.deadline = deadline;
     }
     public int getPublisherid(){
         return publisherid;
     }
     public void setPublisherid(int publisherid){
    	 Employee publisher = new Employee();
    	 publisher.setId(publisherid);
    	 this.publisher=publisher;
         this.publisherid = publisherid;
     }
     public int getCategoryid(){
         return categoryid;
     }
     public void setCategoryid(int categoryid){
         this.categoryid = categoryid;
     }
     public String getRemark(){
         return remark;
     }
     public void setRemark(String remark){
         this.remark = remark;
     }
}