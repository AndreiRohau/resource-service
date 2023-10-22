package my.course.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ResourceCreateRequestDto {
    private byte[] resource;
    private SongDto songDto = new SongDto();
}
