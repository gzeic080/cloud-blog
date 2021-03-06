package cn.xiaojunyun.cloud.blog.data.interceptor;


import cn.xiaojunyun.cloud.blog.data.sqlsource.BoundSqlSqlSource;
import cn.xiaojunyun.cloud.blog.data.util.ExecutorUtil;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Properties;

/**
 * TODO: 分页拦截器
 *
 * @author junyunxiao
 * @date 2018-9-14 15:25
 */
@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,RowBounds.class, ResultHandler.class }) })
@Component
public class PageInterceptor<E> implements Interceptor {

    private Logger logger= LoggerFactory.getLogger(PageInterceptor.class);

    private int MAPPED_STATEMENT_INDEX;
    private int PARAMETER_INDEX;
    private int ROWBOUNDS_INDEX;

    public PageInterceptor() {
        this.MAPPED_STATEMENT_INDEX = 0;
        this.PARAMETER_INDEX = 1;
        this.ROWBOUNDS_INDEX = 2;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] queryArgs=invocation.getArgs();
        RowBounds rowBounds = (RowBounds) queryArgs[this.ROWBOUNDS_INDEX];
        int offset = rowBounds.getOffset();
        int limit = rowBounds.getLimit();
        if (offset != 0 || limit != Integer.MAX_VALUE){
            logger.info("com.maomiyibian.microservice.provider.interceptor.PageInterceptor：分页拦截器介入，开始拦截");
            MappedStatement mappedStatement = (MappedStatement) queryArgs[this.MAPPED_STATEMENT_INDEX];
            Object parameter = queryArgs[this.PARAMETER_INDEX];
            Executor executor=(Executor) invocation.getTarget();
            BoundSql boundSql=mappedStatement.getBoundSql(parameter);
            CacheKey cacheKey=executor.createCacheKey(mappedStatement,parameter,rowBounds,boundSql);
            ArrayList<ParameterMapping> list = new ArrayList<>();
            if (boundSql.getParameterMappings() != null) {
                list.addAll(boundSql.getParameterMappings());
            }
            String sql= ExecutorUtil.getPageSql(boundSql,rowBounds,cacheKey);
            //设置
            BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration(), sql, list, boundSql.getParameterObject());
            queryArgs[this.MAPPED_STATEMENT_INDEX] =this.copyFromMappedStatement(mappedStatement,new BoundSqlSqlSource(newBoundSql) );
            if (offset > 0) {
                offset = 0;
            }
            limit = Integer.MAX_VALUE;
            queryArgs[this.ROWBOUNDS_INDEX] = new RowBounds(offset, limit);
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {}

    /**
     *
     * @param ms
     * @param newSqlSource
     * @return
     */
    private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        return builder.build();
    }
}
