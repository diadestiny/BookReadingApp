package com.example.bottomtabber.Data;


import org.litepal.crud.LitePalSupport;

public class Book extends LitePalSupport {

    private int bitmapId;//书图片编号
    private String name;//书名
    private String user_name;//用户名字
    private String category;//种类
    private int sum;//阅读次数
    private String path;//路径
    private String bookContent="NoContent";//内容

    public Book(int bitmapId, String name, String user_name) {
        this.bitmapId = bitmapId;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    @Override
    public boolean equals( Object obj) {
        if(obj instanceof Book){
            Book u= (Book) obj;
            return this.name.equals(u.name);
        }
        return super.equals(obj);
    }
}
