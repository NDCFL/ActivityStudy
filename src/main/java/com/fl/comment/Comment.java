package com.fl.comment;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.runtime.ProcessInstance;

/**
 * Created by chenfeilong on 2017/10/11.
 */
public class Comment{
    public static void main(String[] args) {
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:mysql://localhost:3306/activiti")
                .setJdbcUsername("root")
                .setJdbcPassword("cfl,1997")
                .setJdbcDriver("com.mysql.jdbc.Driver")
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        ProcessEngine processEngine = cfg.buildProcessEngine();
        String pName = processEngine.getName();
        String ver = ProcessEngine.VERSION;
        System.out.println("ProcessEngine [" + pName + "] Version: [" + ver + "]");
        RepositoryService repositoryService = processEngine.getRepositoryService();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //部署流程定义，ACT_GE_BYTEARRAY,activiti.ACT_RE_DEPLOYMENT,ACT_RE_PROCDEF
        repositoryService.createDeployment().addClasspathResource("").deploy();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("");


    }

}
