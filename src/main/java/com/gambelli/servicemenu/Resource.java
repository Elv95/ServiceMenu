package com.gambelli.servicemenu;

public class Resource {
    private Integer id;
    private String type;
    private String version;

    public Resource(){
        super();
    }

    public void setId(Integer id){
        this.id = id;
    }
    public Integer getId(){
        return id;
    }
    public void setType(String type){
        this.type = type;
    }
    public String getNodeType(){
        return type;
    }
    public void setVersion(String version){
        this.version = version;
    }
    public String getVersion() {
        return version;
    }
}
