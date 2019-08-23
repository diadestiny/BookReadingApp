package com.example.bottomtabber.Data;


import org.litepal.crud.LitePalSupport;

public class Book extends LitePalSupport {

    private String imageUrl="noImageUri";
    private int listNum;//网络书籍保存序号
    private int bitmapId;//(默认无图)
    private String name;//书名
    private String user_name;//用户名字
    private int sum;//阅读次数
    private String path = "noPath";//路径
    private String bookContent="NoContent";//内容

    public Book(int bitmapId, String name, String user_name) {
        this.bitmapId = bitmapId;
        this.name = name;
        this.user_name = user_name;
        this.sum=0;
    }
    public Book(String imageUrl, String name, String user_name) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.user_name = user_name;
        this.sum=0;
    }

    public Book(int bitmapId, String name) {
        this.bitmapId = bitmapId;
        this.name = name;
    }

    public Book() { }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getBitmapId() {return bitmapId; }

    public void setBitmapId(int bitmapId) {
        this.bitmapId = bitmapId;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getBookContent() {
        return bookContent;
    }

    public void setBookContent(String bookContent) {
        this.bookContent = bookContent;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getListNum() {
        return listNum;
    }

    public void setListNum(int listNum) {
        this.listNum = listNum;
    }

    @Override
    public boolean equals( Object obj) {
        if(obj instanceof Book){
            Book u= (Book) obj;
            return this.name.equals(u.name);
        }
        return super.equals(obj);
    }


}
