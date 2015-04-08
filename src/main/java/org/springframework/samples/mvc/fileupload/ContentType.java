package org.springframework.samples.mvc.fileupload;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
/**
 * http://stackoverflow.com/questions/13083036/how-do-i-display-validation-errors-about-an-uploaded-multipart-file-using-spring
 * The annotated element must have specified content type.
 *
 * Supported types are:
 * <ul>
 * <li><code>MultipartFile</code></li>
 * </ul>
 *
 * @author Michal Kreuzman
 */
@Documented
@Retention(RUNTIME)
@Constraint(validatedBy = {ContentTypeMultipartFileValidator.class})
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
public @interface ContentType {

    String message() default "{com.kreuzman.ContentType.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

/**
 * Specify accepted content types.
 *
 * Content type example :
 * <ul>
 * <li>application/pdf - accepts PDF documents only</li>
 * <li>application/msword - accepts MS Word documents only</li>
 * <li>images/png - accepts PNG images only</li>
 * </ul>
 *
 * @return accepted content types
 */
     String[] value();
}