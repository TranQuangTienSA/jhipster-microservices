package net.tinyset.customer.aop.authorization;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import net.tinyset.customer.security.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Table;

@Aspect
public class AuthorizationAspect {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* *(..)) && args(predicate, ..) && @annotation(entityAuthorize)")
    public void entityAuthorizePointcut(Predicate predicate, EntityAuthorize entityAuthorize) {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    @Around("entityAuthorizePointcut(predicate, entityAuthorize)")
    public Object entityAuthorizeAround(ProceedingJoinPoint joinPoint, Predicate predicate, EntityAuthorize entityAuthorize) throws Throwable {
        log.debug("entity authorize around: joinPoint={}, predicate={}, entityAuthorize={}", joinPoint, predicate, entityAuthorize);

        Class<?> clazz = entityAuthorize.root();
        predicate = authorizePredicate(predicate, clazz);
        log.debug("authorized predicate: {}", predicate);

        Object[] args = joinPoint.getArgs();
        args[0] = predicate;
        return joinPoint.proceed(args);
    }

    private Predicate authorizePredicate(Predicate predicate, Class<?> clazz) {
        String tableName;
        Table table = clazz.getAnnotation(Table.class);
        if (table != null && StringUtils.isNotBlank(table.name())) {
            tableName = table.name();
        } else {
            tableName = clazz.getSimpleName().toLowerCase();
        }
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        PathBuilder<?> pathBuilder = new PathBuilder<Object>(clazz, tableName);
        String currentUserLogin = SecurityUtils.getCurrentUserLogin();
        if (!hasViewAllPermission(currentUserLogin)) {
//            String relationshipTableName = String.format("%s_relationship", tableName);
            BooleanExpression ownerLogin = pathBuilder.getString("ownerLogin").eq(currentUserLogin);
            booleanBuilder.and(ownerLogin);
        }
        if (predicate != null) {
            booleanBuilder.and(predicate);
        }
        return booleanBuilder.getValue();
    }

    // Test
    private boolean hasViewAllPermission(String currentUserLogin) {
        return "admin".equals(currentUserLogin);
    }
}
