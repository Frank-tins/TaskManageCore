package com.task.core.annotation;

import com.task.core.start.StartLoading;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({StartLoading.class})
public @interface EnableTaskCore {
}
