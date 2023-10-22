package my.course.service;

import my.course.entity.Resource;
import my.course.exception.ResponseServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ResourceServiceValidator {

    @Value("${validator.parameter.length}")
    private Integer parameterLength;

    public void validateResourceExistence(Optional<Resource> optResource) {
        if (optResource.isEmpty()) {
            throw ResponseServiceException.init404();
        }
    }

    /**
     * Valid CSV length < 200 characters
     * @param csvId, e.g. "1,2"
     */
    public void validateParamLength(String csvId) {
        if (csvId.length() >= parameterLength) {
            throw ResponseServiceException.init500();
        };
    }
}
