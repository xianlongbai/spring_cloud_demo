package com.bxl.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by root on 2019/3/29.
 * 测试同类中调用异步任务
 */

@Controller
@RequestMapping("/app")
@Transactional(value = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class EmailController {

    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    private ApplicationContext applicationContext;


    @RequestMapping(value = "/email/asyncCall", method = GET)
    @ResponseBody
    public Map<String, Object> asyncCall () {

        Map<String, Object> resMap = new HashMap<String, Object>();
        try{
            //不可行
//            EmailController.Demo demo = this.new Demo();
//            demo.testAsyncTask();
//            //this.testAsyncTask();
            //emailService.testSyncTask();
            /*cacheExecutor.submit(()->{
                try {
                    EmailController.this.testSyncTask();
                } catch (InterruptedException e) {
                    logger.error("执行异常！！！");
                }
            });*/

            boolean isAop = AopUtils.isAopProxy(EmailController.class);//是否是代理对象；
            boolean isCglib = AopUtils.isCglibProxy(EmailController.class);  //是否是CGLIB方式的代理对象；
            boolean isJdk = AopUtils.isJdkDynamicProxy(EmailController.class);  //是否是JDK动态代理方式的代理对象；
            EmailController emailController = (EmailController)applicationContext.getBean(EmailController.class);
            //emailController.testAsyncTask();
            EmailController proxy = (EmailController)AopContext.currentProxy();
            System.out.println(emailController == proxy ? true : false);
            proxy.testAsyncTask();
            System.out.println("end!");
            resMap.put("code", 200);
        }catch (Exception e) {
            resMap.put("code", 500);
            logger.error("",e);
        }
        return resMap;
    }

//    @RequestMapping(value = "/email/asyncCallTwo", method = GET)
//    @ResponseBody
//    public Map<String, Object> asyncCallTwo () {
//
//        Map<String, Object> resMap = new HashMap<String, Object>();
//        try{
//            emailService.asyncCallTwo();
//            resMap.put("code", ResultStatusCode.SUCCESS.getCode());
//        }catch (Exception e) {
//            resMap.put("code", ResultStatusCode.FAILED.getCode());
//            logger.error("",e);
//        }
//        return resMap;
//    }

    @Async
    public void testAsyncTask() throws InterruptedException {
        Thread.sleep(10000);
        System.out.println("异步任务执行完成！");
    }

    @Transactional(value = "transactionManager")
    private void testSyncTask() throws InterruptedException {
        Thread.sleep(10000);
        System.out.println("同步任务执行完成！");
    }

    class Demo{
        @Async
        public void testAsyncTask() throws InterruptedException {
            Thread.sleep(10000);
            System.out.println("异步任务执行完成！");
        }
    }


}
