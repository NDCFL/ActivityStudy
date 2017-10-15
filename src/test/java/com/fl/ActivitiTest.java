package com.fl;

import junit.framework.TestCase;
import org.activiti.engine.*;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.*;

/**
 * Created by chenfeilong on 2017/10/13.
 */
public class ActivitiTest extends TestCase {

    //获取流程引擎
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
    //部署流程
    @Test
    public void testactivitiDelopy() throws  Exception{
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.createDeployment().addClasspathResource("bpmn/leave_process_complex.bpmn20.xml").deploy();
    }
    //开始进行流程的操作
    //1、启动流程
    @Test
    public void teststartProcess() throws  Exception{
        //员工1启动流程
        runService(new UserBean("emp1","emp"));
    }
    //2、通过员工名称来查询谁启动的流程下的所有任务
    @Test
    public void testselectTaskListForUserName() throws  Exception{
        getTask(new UserBean("emp1","emp"),new LeaveBean(7,"家中有事",Calendar.getInstance().getTime()));
    }
    @Test
    public void testbossExeucteTask() throws  Exception{
        bossExeucte(new UserBean("boss1","boss"));
    }
    //启动流程
    public void runService(UserBean userBean) throws  Exception{
        //判断角色是否是员工，是员工才能启动流程
        if(userBean.getRoleName().equals("emp")){
            RuntimeService runtimeService = processEngine.getRuntimeService();
            //保存启动流程的员工信息
            Map<String,Object> userMap = new HashMap<String, Object>();
            //放置员工的基本信息
            userMap.put("userName",userBean.getUserName());
            //传送基本信息到下一个任务
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("leave_process_complex", userMap);
            System.out.println(userBean.getUserName()+"用户;流程启动成功！");
        }else{
            System.out.println("你没有启动流程任务的权限！");
        }
    }
    //查询指定员工的任务列表
    public List<Task> gettaskList(UserBean userBean){
        //根据当前登录的员工来查询他的所有任务
        TaskService taskService = processEngine.getTaskService();
        //根据当前登录的员工来查询任务列表
        List<Task> taskList = taskService.createTaskQuery().taskCandidateUser(userBean.getUserName()).list();
        //当前员工有需要执行的任务
        if(taskList.size()>0){
            //循环遍历当前用户的任务
            Iterator<Task> taskIterator = taskList.iterator();
            while (taskIterator.hasNext()){
                Task task = taskIterator.next();
                //获取启动流程的员工名称
                String userName = taskService.getVariable(task.getId(),"userName").toString();
                if(!userName.equals(userBean.getUserName())){
                    System.out.println("你没有任务！");
                    //移除任务
                    taskIterator.remove();
                }else{
                    //执行当前的任务
                    System.out.println("开始执行任务！");
                }
            }
        }
        return taskList;
    }
    //执行任务，请假
    public void getTask(UserBean userBean,LeaveBean leaveBean) throws  Exception{
        TaskService taskService = processEngine.getTaskService();
        List<Task> taskList = gettaskList(userBean);
        for(Task task:taskList){
            //开始执行任务
            Map<String,Object> taskMap = new HashMap<String, Object>();
            taskMap.put("days",leaveBean.getDays());
            taskMap.put("reason",leaveBean.getReason());
            taskMap.put("leaveTime",leaveBean.getLeaveTime());
            taskService.complete(task.getId(),taskMap);
        }
    }
    //老板进行审核批准
    public void bossExeucte(UserBean userBean) throws  Exception{
        //判断角色是否是员工，是员工才能启动流程
        if(userBean.getRoleName().equals("boss")){
            TaskService taskService = processEngine.getTaskService();
            //根据当前登录的员工来查询任务列表
            List<Task> taskList = taskService.createTaskQuery().taskCandidateUser(userBean.getUserName()).list();
            for(Task task:taskList){
                //获取员工传递的参数
                int days = Integer.parseInt(taskService.getVariable(task.getId(),"days").toString());
                String reason = taskService.getVariable(task.getId(),"reason").toString();
                Date leaveTime = (Date) taskService.getVariable(task.getId(),"leaveTime");
                System.out.println("尊敬的老板：");
                System.out.println("    "+reason);
                System.out.println("                 日期："+leaveTime);
                if(days<8){
                    taskService.complete(task.getId());
                }else{
                    System.out.println("不批准申请！");
                }
            }
        }else{
            System.out.println("你没有权限！");
        }
    }

}
