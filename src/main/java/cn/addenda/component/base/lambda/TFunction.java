package cn.addenda.component.base.lambda;

/**
 * @author addenda
 * @since 2023/6/4 14:58
 */
@FunctionalInterface
public interface TFunction<T, R> {

  R apply(T t) throws Throwable;

}
