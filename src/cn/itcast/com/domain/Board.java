package cn.itcast.com.domain;



import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;


public class Board {
    private Integer id;
    private String title;
    private String author;
    private String content;
    private Integer thumbs_up;
    private Integer thumbs_down;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getThumbs_up() {
        return thumbs_up;
    }

    public void setThumbs_up(Integer thumbs_up) {
        this.thumbs_up = thumbs_up;
    }

    public Integer getThumbs_down() {
        return thumbs_down;
    }

    public void setThumbs_down(Integer thumbs_down) {
        this.thumbs_down = thumbs_down;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", thumbs_up=" + thumbs_up +
                ", thumbs_down=" + thumbs_down +
                ", time=" + time +
                '}';
    }
}
