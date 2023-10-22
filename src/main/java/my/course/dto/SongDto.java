package my.course.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SongDto {
    private String name;
    private String artist;
    private String album;
    private String length;
    private String resourceId;
    private String year;
}
