package com.cml.spms.domain;

public class Asset {
    @Override
    public String toString() {
        return "Asset{" +
                "index=" + index +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", image='" + image + '\'' +
                ", price='" + price + '\'' +
                ", count=" + count +
                ", category='" + category + '\'' +
                '}';
    }

    private Integer index;//数据库中的物品编号
    private String name;//物品名称
    private String type;//物品类别
    private String image;//图片路径
    private String price;//物品价格
    private Integer count;//在库数量
    private String category;//与后端数据库中的字段有所不同，要求连表查询

    public Asset() {
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
