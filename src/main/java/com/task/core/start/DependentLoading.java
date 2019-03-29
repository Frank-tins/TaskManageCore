package com.task.core.start;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * 依赖组加载
 *
 * @author Frank
 */
public class DependentLoading {

    private Logger logger = LogManager.getLogger(DependentLoading.class);

    private final Map<String, String> DEPENDENTS = new HashMap<>();

    {
        DEPENDENTS.put("SPRING MVC", "org.springframework.web.servlet.FrameworkServlet");
    }

    private void run(){


    }

    private void loadDependents(){
        DEPENDENTS.entrySet().forEach( e -> loadNode(e));
    }

    private void loadNode (Map.Entry<String, String> entry){
        try {
            Class.forName(entry.getValue());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
