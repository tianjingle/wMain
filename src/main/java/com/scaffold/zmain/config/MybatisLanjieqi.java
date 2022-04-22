package com.scaffold.zmain.config;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Connection;

/**
 * @Author tianjl
 * @Date 2021/8/17 20:35
 * @Discription disc
 */

@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class,Integer.class})})
public class MybatisLanjieqi implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        System.out.println(statementHandler.toString());
        return invocation.proceed();
    }


    @Override
    public Object plugin(Object target) {
        System.out.println("enter the plugin");
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }
}

