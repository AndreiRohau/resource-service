package my.course.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResourceGetResponseDto {
    private Integer resourceId;
    private byte[] resource; // Audio bytes
}
