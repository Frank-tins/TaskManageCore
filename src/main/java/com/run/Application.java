package com.run;

import com.task.core.annotation.start.EnableTaskCore;
import com.task.core.annotation.start.TaskPackage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * start Class
 *
 * @author Frank
 */
@SpringBootApplication(
        scanBasePackages = {"com.run"}
)
@EnableTaskCore(
        baseTaskPackage = @TaskPackage(packageName = "com.run.controller")
)
public class Application {

    private Logger logger = LogManager.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

}
