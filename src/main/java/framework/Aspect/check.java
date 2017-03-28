package framework.Aspect;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * 切点注解
 * Created by Willow on 1/18/17.
 */
@Service
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Check {
}
