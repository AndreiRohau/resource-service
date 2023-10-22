package my.course.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import my.course.dto.*;
import my.course.entity.Resource;
import my.course.exception.ResponseServiceException;
import my.course.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static my.course.exception.ResponseServiceException.isExceptionOfCode;

@Service
@RequiredArgsConstructor
@Transactional
@Log
public class ResourceServiceImpl implements ResourceService {

    @Value("${song.service.save.url}")
    private String songServiceSaveUrl;

    private final ResourceServiceValidator validator;
    private final RestTemplateBuilder restTemplateBuilder;
    private final ResourceRepository resourceRepository;
    private RestTemplate restTemplate;

    @PostConstruct
    private void postConstruct() {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public ResourceGetResponseDto getResourceById(Integer id) {
        try {
            Optional<Resource> optResource = resourceRepository.findById(id);

            validator.validateResourceExistence(optResource);

            return prepareGetResponse(optResource.get());
        } catch (Exception e) {
            if (isExceptionOfCode(e, HttpStatus.NOT_FOUND.value())) {
                throw e;
            } else {
                throw ResponseServiceException.init500();
            }
        }
    }

    private ResourceGetResponseDto prepareGetResponse(Resource resource) {
        return new ResourceGetResponseDto(resource.getResourceId(), resource.getResource());
    }

    @Override
    public ResourceCreateResponseDto saveResource(ResourceCreateRequestDto resourceCreateRequestDto) {
        try {
            final Resource resource = new Resource(resourceCreateRequestDto.getResource());
            resourceRepository.save(resource);
            Integer resourceId = resource.getResourceId();

            resourceCreateRequestDto.getSongDto().setResourceId(resourceId.toString());
            saveMetadata(resourceCreateRequestDto.getSongDto());

            return new ResourceCreateResponseDto(resourceId);
        } catch (Exception e) {
            throw ResponseServiceException.init500();
        }
    }

    private void saveMetadata(SongDto songDto) {
        System.out.println(songDto);
        // save resourceCreateRequestDto in Song Service using restCall
        String url = songServiceSaveUrl;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // build the request
        HttpEntity<SongDto> entity = new HttpEntity<>(songDto, headers);

        // send POST request
        ResponseEntity<SongCreateResponseDto> response = restTemplate.postForEntity(url, entity, SongCreateResponseDto.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            log.info("Song data saved in SongService. result=" + response.getBody());
        } else {
            log.info("Song data saveMetadata(). Response.StatusCode=" + response.getStatusCode());
        }

    }

    @Override
    public ResourceDeleteResponseDto deleteResources(String csvId) {
        try {
            validator.validateParamLength(csvId);
            final List<Integer> existingIds = Arrays.stream(csvId.split(","))
                    .map(Integer::parseInt)
                    .filter(resourceRepository::existsById)
                    .collect(Collectors.toList());

            resourceRepository.deleteAllById(existingIds);

            return  new ResourceDeleteResponseDto(existingIds);
        } catch (Exception e) {
            throw ResponseServiceException.init500();
        }
    }
}
