package com.zp.common.config.config;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

@Aspect
@Configuration
public class TransactionAdviceConfig {
     private static final String AOP_POINTCUT_EXPRESSION =  "execution (* com.zp.*.*.service.*.*(..)) || execution (* com.tr.*.service.*.*(..))";

        @Autowired
        private PlatformTransactionManager transactionManager;

        @Bean
        public TransactionInterceptor txAdvice() {
            //事务管理
            DefaultTransactionAttribute txAttr_REQUIRED = new DefaultTransactionAttribute();
            txAttr_REQUIRED.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

            /**
             * 只读事务
             */
            DefaultTransactionAttribute txAttr_REQUIRED_READONLY = new DefaultTransactionAttribute();
            txAttr_REQUIRED_READONLY.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED );
            txAttr_REQUIRED_READONLY.setReadOnly(true);

            NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();

            source.addTransactionalMethod("add*", txAttr_REQUIRED);
            source.addTransactionalMethod("save*", txAttr_REQUIRED);
            source.addTransactionalMethod("insert*", txAttr_REQUIRED);

            source.addTransactionalMethod("delete*", txAttr_REQUIRED);
            source.addTransactionalMethod("del*", txAttr_REQUIRED);
            source.addTransactionalMethod("remove*", txAttr_REQUIRED);

            source.addTransactionalMethod("update*", txAttr_REQUIRED);
            source.addTransactionalMethod("modify*", txAttr_REQUIRED);

            source.addTransactionalMethod("next*", txAttr_REQUIRED);
            source.addTransactionalMethod("register*", txAttr_REQUIRED);
            source.addTransactionalMethod("exec*", txAttr_REQUIRED);
            source.addTransactionalMethod("clear*", txAttr_REQUIRED);
            source.addTransactionalMethod("reset*", txAttr_REQUIRED);
            source.addTransactionalMethod("call*", txAttr_REQUIRED);


            source.addTransactionalMethod("set*", txAttr_REQUIRED);
            source.addTransactionalMethod("get*", txAttr_REQUIRED_READONLY);
            source.addTransactionalMethod("query*", txAttr_REQUIRED_READONLY);
            source.addTransactionalMethod("find*", txAttr_REQUIRED_READONLY);
            source.addTransactionalMethod("list*", txAttr_REQUIRED_READONLY);
            source.addTransactionalMethod("count*", txAttr_REQUIRED_READONLY);
            source.addTransactionalMethod("select*", txAttr_REQUIRED_READONLY);
            source.addTransactionalMethod("is*", txAttr_REQUIRED_READONLY);

            return new TransactionInterceptor(transactionManager, source);
        }

        @Bean
        public Advisor txAdviceAdvisor(TransactionInterceptor txAdvice) {
            AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
            pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
            return new DefaultPointcutAdvisor(pointcut, txAdvice);
        }
}
 