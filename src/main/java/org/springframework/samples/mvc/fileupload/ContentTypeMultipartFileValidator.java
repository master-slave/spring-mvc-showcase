package org.springframework.samples.mvc.fileupload;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ContentTypeMultipartFileValidator implements ConstraintValidator<ContentType, MultipartFile> {

private String[] acceptedContentTypes;

@Override
public void initialize(ContentType constraintAnnotation) {
    this.acceptedContentTypes = constraintAnnotation.value();
}

@Override
public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
    if (value == null || value.isEmpty())
        return true;

    return ContentTypeMultipartFileValidator.acceptContentType(value.getContentType(), acceptedContentTypes);
}

private static boolean acceptContentType(String contentType, String[] acceptedContentTypes) {
    for (String accept : acceptedContentTypes) {
            // TODO this should be done more clever to accept all possible content types
        if (contentType.equalsIgnoreCase(accept)) {
            return true;
        }
    }

    return false;
}
}