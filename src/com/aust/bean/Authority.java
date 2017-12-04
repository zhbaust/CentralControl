package com.aust.bean;

public class Authority {
	private int id;//权限编号
    private String name;//权限名称
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
    public String getRemark(){
        return remark;
    }
    public void setRemark(String remark){
        this.remark = remark;
    }
}
