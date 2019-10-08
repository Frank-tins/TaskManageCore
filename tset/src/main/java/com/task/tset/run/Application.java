package com.task.tset.run;

import com.task.core.annotation.start.EnableTaskCore;
import com.task.core.annotation.start.TaskPackage;
import com.task.core.annotation.start.TaskThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * start Class
 *
 * @author Frank
 */
@SpringBootApplication(
        scanBasePackages = {"com.task.tset.run"}
)
@EnableTaskCore(
        baseTaskPackage = @TaskPackage(packageName = "com.task.tset.run"),
        poolAnnotation = @TaskThreadPool(corePoolSize = 300)
)
public class Application {

    private Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

}
