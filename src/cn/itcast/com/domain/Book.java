package cn.itcast.com.domain;

public class Book {
    private Integer id;
    private String name;
    private Double price;
    private String image;
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", type=" + type +
                '}';
    }
}
