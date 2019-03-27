package com.base.support.thread;

import com.base.support.logger.support.RunLogger;
import com.base.util.GsonUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "taskSupportController")
public final class SupportController {

    public SupportController() {
        System.out.println(" yes yes yes ");
    }

    @PostMapping(value = "/info")
    public RunLogger getInfo() {
        System.out.println(GsonUtils.toString(TaskSupervise.getRunLogger()));
        return TaskSupervise.getRunLogger();
    }

}
