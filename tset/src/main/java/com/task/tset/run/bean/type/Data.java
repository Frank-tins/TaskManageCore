package com.task.tset.run.bean.type;

import com.task.tset.run.EntityTest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Data {



    public List<EntityTest> list = new ArrayList<EntityTest>();

    {
        for (int i = 0; i < 8000 ; i++) {
            EntityTest entityTest = new EntityTest();
            entityTest.setId(i + "-id");
            entityTest.setName(i + "-name");
            list.add(entityTest);
        }
    }


    public List<EntityTest> findData(String name){
        System.out.println("我收到了" + name);
        return list;
    }


}
