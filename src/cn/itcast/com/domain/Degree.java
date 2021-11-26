package cn.itcast.com.domain;

public class Degree {

    private Integer id;
    private String degree;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    @Override
    public String toString() {
        return "Degree{" +
                "id=" + id +
                ", degree='" + degree + '\'' +
                '}';
    }
}
