package com.inxedu.os.common.intercepter;

import com.inxedu.os.common.entity.PageEntity;
import com.inxedu.os.common.util.ObjectUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

@Intercepts({    @Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
)})
/**
 * @author www.inxedu.com
 */
public class PageInterceptor implements Interceptor {
    public PageInterceptor() {
    }

    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement)invocation.getArgs()[0];
        Object parameter = invocation.getArgs()[1];
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        String originalSql = boundSql.getSql().trim();
        Object parameterObject = boundSql.getParameterObject();
        Object obj = boundSql.getParameterObject();
        if(obj instanceof Map) {
            Map<String,Object> result = (Map<String,Object>)obj;
            PageEntity page = null;
            for (Object arg : result.values()) {
                if (arg instanceof PageEntity) {
                	page = (PageEntity) arg;
                }
            }
           //String countfalg = (String)result.get("countfalg");
            if(ObjectUtils.isNotNull(page)) {
            	 if(ObjectUtils.isNull(result.get("pageEntity"))){
               	  Object result1 = invocation.proceed();
                     return result1;
               }
                page = (PageEntity)result.get("pageEntity");
                String countSql = this.getCountSql(originalSql);
                Connection connection = mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection();
                PreparedStatement countStmt = connection.prepareStatement(countSql);
                BoundSql countBS = this.copyFromBoundSql(mappedStatement, boundSql, countSql);
                DefaultParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, countBS);
                parameterHandler.setParameters(countStmt);
                ResultSet rs = countStmt.executeQuery();
                int totpage = 0;
                if(rs.next()) {
                    totpage = rs.getInt(1);
                }

                rs.close();
                countStmt.close();
                connection.close();
                page.setTotalResultSize(totpage);
                int totalPageSize = (page.getTotalResultSize() - 1) / page.getPageSize() + 1;
                page.setTotalPageSize(totalPageSize);
                int offset = (page.getCurrentPage() - 1) * page.getPageSize();
                StringBuffer sb = new StringBuffer();
                sb.append(originalSql).append(" limit ").append(offset).append(",").append(page.getPageSize());
                BoundSql newBoundSql = this.copyFromBoundSql(mappedStatement, boundSql, sb.toString());
                MappedStatement newMs = this.copyFromMappedStatement(mappedStatement, new PageInterceptor.BoundSqlSqlSource(newBoundSql));
                invocation.getArgs()[0] = newMs;
            }
        }

        Object result1 = invocation.proceed();
        return result1;
    }

    private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        Builder builder = new Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
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

    private BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql, String sql) {
        BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
        Iterator i$ = boundSql.getParameterMappings().iterator();

        while(i$.hasNext()) {
            ParameterMapping mapping = (ParameterMapping)i$.next();
            String prop = mapping.getProperty();
            if(boundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }

        return newBoundSql;
    }

    private String getCountSql(String sql) {
        return "SELECT COUNT(*) FROM (" + sql + ") aliasForPage";
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {
    }

    public class BoundSqlSqlSource implements SqlSource {
        BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        public BoundSql getBoundSql(Object parameterObject) {
            return this.boundSql;
        }
    }
}
