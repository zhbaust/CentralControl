package com.aust.bean;

import java.util.*;
public class Employee{
     private int id;//人员编号
     private String name;//员工姓名
     private String loginname;//登录名
     private String password;//登录密码
     private String sex;//1：男，0：女
     private String birthday;//出生日期
     private String tel;//电话号码
     private String address;//家庭住址
     private int departmentid;//所属部门
     private int professionid;//工种
     private int authorityid;//权限
     private String jobstate;//在职情况
     private String cardnumber;//卡号
     private String picture;//照片
     private String remark;//备注
     private Department department;//部门
     private Profession profession; //工种
     private Authority authority;//权限
     
     public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public Profession getProfession() {
		return profession;
	}
	public void setProfession(Profession profession) {
		this.profession = profession;
	}
	public Authority getAuthority() {
		return authority;
	}
	public void setAuthority(Authority authority) {
		this.authority = authority;
	}
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
     public String getLoginname(){
         return loginname;
     }
     public void setLoginname(String loginname){
         this.loginname = loginname;
     }
     public String getPassword(){
         return password;
     }
     public void setPassword(String password){
         this.password = password;
     }
     public String getSex(){
         return sex;
     }
     public void setSex(String sex){
         this.sex = sex;
     }
     public String getBirthday(){
         return birthday;
     }
     public void setBirthday(String birthday){
         this.birthday = birthday;
     }
     public String getTel(){
         return tel;
     }
     public void setTel(String tel){
         this.tel = tel;
     }
     public String getAddress(){
         return address;
     }
     public void setAddress(String address){
         this.address = address;
     }
     public int getDepartmentid(){
    	 
         return departmentid;
     }
     public void setDepartmentid(int departmentid){

    	 Department department = new Department();
    	 department.setId(departmentid);
    	 this.department=department;
         this.departmentid = departmentid;
     }
     public int getProfessionid(){
         return professionid;
     }
     public void setProfessionid(int professionid){
    	 Profession profession = new Profession();
    	 profession.setId(professionid);
    	 this.profession=profession;
         this.professionid = professionid;
     }
     public int getAuthorityid(){
         return authorityid;
     }
     public void setAuthorityid(int authorityid){
    	 Authority authority = new Authority();
    	 authority.setId(authorityid);
    	 this.authority=authority;
         this.authorityid = authorityid;
     }
     public String getJobstate(){
         return jobstate;
     }
     public void setJobstate(String jobstate){
         this.jobstate = jobstate;
     }
     public String getCardnumber(){
         return cardnumber;
     }
     public void setCardnumber(String cardnumber){
         this.cardnumber = cardnumber;
     }
     public String getPicture(){
         return picture;
     }
     public void setPicture(String picture){
         this.picture = picture;
     }
     public String getRemark(){
         return remark;
     }
     public void setRemark(String remark){
         this.remark = remark;
     }
}