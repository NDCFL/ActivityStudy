package com.fl;

import org.activiti.engine.*;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by chenfeilong on 2017/10/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:/config/spring-applicationContext.xml", "classpath:/config/spring-mybatis.xml"})
public class SSMActivitiTest {
    ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("config/spring-activiti.xml");
    ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
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
    @Test
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
