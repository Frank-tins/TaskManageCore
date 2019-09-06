package com.task.core.bean;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TaskRunLogger implements Serializable {

        private LocalDateTime runTime;
        private String msg;

        public TaskRunLogger(LocalDateTime runTime, String msg) {
            this.runTime = runTime;
            this.msg = msg;
        }

    }