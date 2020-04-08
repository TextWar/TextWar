package cn.qqtextwar.dsl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 这个是用于标记父标签的映射方法
 * <code>
 *     //这里对应了
 *     <code>
 *         {@code @DSLMethod}
 *         void server(Closure closure){
 *             closure()
 *         }
 *     </code>
 *     server{
 *          server "name" //这个将会被missingMethod捕捉
 *     }
 * </code>
 *
 * @author MagicLu550 @ 卢昶存
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface DSLMethod {
}
