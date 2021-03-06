package com.task.tset.run.bean.type;

import com.google.gson.Gson;
import com.task.core.annotation.CreateTask;
import com.task.core.annotation.TaskData;
import com.task.core.annotation.TaskService;
import com.task.core.enums.DataType;
import com.task.tset.run.EntityTest;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@TaskService
public class MethodDataTest {

    @CreateTask(
            code = "testMethodData",
            value =  "测试-01",
            describe = "测试从方法中获取执行参数 ",
            dataType = DataType.EXP,
            dataExp = "bean[data(findData)]"
    )
    public String methodDataTestFunction( String name, @TaskData List<EntityTest> entityTests){

        System.out.println("我叫" + Thread.currentThread().getName() + ",我收到了" + entityTests.size() + "条数据\n" + new Gson().toJson(entityTests));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Gson().toJson(entityTests);
    }

    @CreateTask(
            code = "testMapperData",
            value =  "测试-02",
            describe = "测试从mapper中获取数据",
            dataType = DataType.EXP,
            dataExp = "mapper[com.task.tset.run.dao.TmcMapperTestMapper(selectByParameter)]"
    )
    public String mapperDataTestFunction(@TaskData List<EntityTest> entityTests, String name, String one, Integer two){

        System.out.println("我叫" + Thread.currentThread().getName() + ",我收到了" + entityTests.size() + "条数据\n" + new Gson().toJson(entityTests) + " [name]" + name + " [one]" + one + " [two]" + two);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Gson().toJson(entityTests);
    }

}
