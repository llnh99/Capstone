package com.example.capstone;

public class MemberInfo {
    private String uid;
    private String name;
    private String address_gu;
    private String address_dong;
    private String photo_url;

    public MemberInfo(String uid, String name, String address_gu, String address_dong, String photo_url) {
        this.uid = uid;
        this.name = name;
        this.address_gu = address_gu;
        this.address_dong = address_dong;
        this.photo_url = photo_url;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress_gu() {
        return address_gu;
    }

    public void setAddress_gu(String address_gu) {
        this.address_gu = address_gu;
    }

    public String getAddress_dong() {
        return address_dong;
    }

    public void setAddress_dong(String address_dong) {
        this.address_dong = address_dong;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }
}
