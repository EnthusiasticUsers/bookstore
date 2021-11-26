package cn.itcast.com.domain;

public class Hobby {
    private Integer id;
    private String hobby;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    @Override
    public String toString() {
        return "Hobby{" +
                "id=" + id +
                ", hobby='" + hobby + '\'' +
                '}';
    }
}
