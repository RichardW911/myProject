package models;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Image {
    private Integer imageId;
    private Integer u_id;
    private String image_name;
    private Long size;
    private String upload_time;
    private String md5;
    private String content_type;
    private String path;
}
