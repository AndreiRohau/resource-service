package my.course.service;

import my.course.dto.ResourceCreateRequestDto;
import my.course.dto.ResourceCreateResponseDto;
import my.course.dto.ResourceDeleteResponseDto;
import my.course.dto.ResourceGetResponseDto;

public interface ResourceService {

    ResourceGetResponseDto getResourceById(Integer id);

    ResourceCreateResponseDto saveResource(ResourceCreateRequestDto resourceCreateRequestDto);

    ResourceDeleteResponseDto deleteResources(String csvId);
}
