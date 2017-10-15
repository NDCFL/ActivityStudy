package com.fl;

import junit.framework.TestCase;
import org.activiti.engine.*;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;

/**
 * Created by chenfeilong on 2017/10/12.
 */
public class test extends TestCase{

    private ProcessEngine processEngine;
    protected void setUp(){
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:mysql://localhost:3306/activiti")
                .setJdbcUsername("root")
                .setJdbcPassword("cfl,1997")
                .setJdbcDriver("com.mysql.jdbc.Driver")
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        processEngine = cfg.buildProcessEngine();
        String pName = processEngine.getName();
        String ver = ProcessEngine.VERSION;
        System.out.println("ProcessEngine [" + pName + "] Version: [" + ver + "]");
    }
    //启动流程
    @Test
    public void testDeploy(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.createDeployment().addClasspathResource("bpmn/student_Leave.bpmn20.xml").deploy();
    }
    @Test
    public void testRuntimeService(){
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("student_Leave");
    }
    //查询学生的任务
    public void testFindTack(){
        TaskService taskService = processEngine.getTaskService();
        List<Task> taskList = taskService.createTaskQuery().taskAssignee("student").list();
        for (Task task :taskList) {
            System.out.println(task.getName());
            //完成任务
            taskService.complete(task.getId());
        }
    }
}
