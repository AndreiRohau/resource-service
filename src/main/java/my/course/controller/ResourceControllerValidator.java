package my.course.controller;

import my.course.exception.ResponseServiceException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import static my.course.config.Constant.AUDIO_MPEG;
import static my.course.config.Constant.MP3;


@Component
public class ResourceControllerValidator {

    public void validateSaveResourceRequest(MultipartFile file) {
        validateRequestContentType(file);
        validateRequestResourceType(file);
    }

    private void validateRequestContentType(MultipartFile file) {
        if (!AUDIO_MPEG.equals(file.getContentType())) {
            throw ResponseServiceException.init400();
        }
    }

    private void validateRequestResourceType(MultipartFile file) {
        String[] splitFilename = file.getOriginalFilename().split("\\.");
        String ext = splitFilename[splitFilename.length - 1];
        if (!ext.equals(MP3)) {
            throw ResponseServiceException.init400();
        }
    }
}
