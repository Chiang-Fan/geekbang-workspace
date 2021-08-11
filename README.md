# 极客事件小马哥 P7 课程 作业工程

## WEEK6 作业路径
1. 新建 `org.geektimes.configuration.microprofile.config.annotation.ConfigSources` 注解，注解成员为 ConfigSource 的数组对象。

2. 修改 `org.geektimes.configuration.microprofile.config.annotation.ConfigSource` 注解，增加 `@Repeatable(ConfigSources.class)` 的标注。

3. 新建 `sun.net.www.protocol.filepath.Handler` 已实现 filePath 协议的解析。其中通过协议 resource 定义的 file 文件路径，返回对应的 `fileInputStream`.

4. 新建 `org.geektimes.configuration.microprofile.config.annotation.YamlConfigSourceFactory` 实现 `ConfigSourceFactory` 接口用于完成 yaml 类型格式的配置解析。

5. 新建 `org.geektimes.configuration.microprofile.config.annotation.Week6Test` 用于作业的测试。
   * 在类名上标注一个不指定 ConfigSourceFactory 的注解：`@ConfigSource(ordinal = 200, resource = "filepath:D:\\chiangfan\\Workspace\\Learn\\小马哥 P7\\Project\\config\\test.properties")`
   * 标注在一个指定 `YamlConfigSourceFactory` 的注解：`@ConfigSource(ordinal = 200, resource = "filepath:D:\\chiangfan\\Workspace\\Learn\\小马哥 P7\\Project\\config\\test.yaml",
     factory = YamlConfigSourceFactory.class)`
     
   * 修改测试类中的 initConfigSourceFactory 方法，解析 ConfigSource 类型注解的数组。
   * 执行 test 方法。可以看到，properties 的文件以及 yaml 格式的文件都正常解析并加载到了 configSource 中:
   ![img_2.png](img_2.png)
     
6. @Repeatable 的实现原理: 
   * 在 `org.geektimes.configuration.microprofile.config.annotation.Week6Test.initConfigSourceFactory` 中通过 `getClass().getAnnotationsByType(ConfigSource.class)` 获取到了所有的 `ConfigSource`.
   * 从 `getAnnotationsByType` 入手。跟踪到 `sun.reflect.annotation.AnnotationSupport.getDirectlyAndIndirectlyPresent`
   * 在 `sun.reflect.annotation.AnnotationSupport.getIndirectlyPresent` 中看到对 Repeatable 做了处理
      ```java
        private static <A extends Annotation> A[] getIndirectlyPresent(Map<Class<? extends Annotation>, Annotation> var0, Class<A> var1) {
        Repeatable var2 = (Repeatable)var1.getDeclaredAnnotation(Repeatable.class);
        if (var2 == null) {
            return null;
        } else {
            Class var3 = var2.value();
            Annotation var4 = (Annotation)var0.get(var3);
            if (var4 == null) {
                return null;
            } else {
                Annotation[] var5 = getValueArray(var4);
                checkTypes(var5, var4, var1);
                return var5;
            }
        }
      }
      ```
     
   * 在 `Annotation[] var5 = getValueArray(var4);` 方法中，通过 Repeatable 的 value，也就是 ConfigSources 注解。通过反射，执行 ConfigSources 的 value() 方法，并返回。
   
## WEEK5 作业路径
1. 新建 `org.geektimes.cache.annotation.interceptor.CacheRemoveInterceptor` 
   继承 `org.geektimes.cache.annotation.interceptor.CacheOperationInterceptor` 抽象类。
   
2. 因为 `org.geektimes.cache.annotation.interceptor.CacheOperationInterceptor` 中实现了调用的模板。
   所以主要实现 `org.geektimes.cache.annotation.interceptor.CacheRemoveInterceptor` 中的 `beforeExecute` 及 `afterExecute` 方法。
   
3. 在 `afterExecute` 方法中使用 cache 的方法，删除对应的缓存。

4. 新增 `org.geektimes.cache.week5.Week5Test` 测试类，用于测试缓存删除的效果。
5. 作业思路: 新建一个用于处理 `@CacheRemove` 的 `Interceptor`, 根据方法上的 `@CacheRemove` 注解。
   获取一个对应的缓存对象 `Cache`，根据语义对 cache 中的内容做操作。完成 `Interceptor` 后，使用上周作业完成的 `enhancer` 
   对 `org.geektimes.cache.week5.DataRepository` 做增强，使该接口中的所有方法都会经过 `Interceptor`。

## WEEK4 作业路径
1. 新建 `org.geektimes.interceptor.dynamic.InvocationHandlerAdapter` 实现 JDK 动态代理 
   `java.lang.reflect.InvocationHandler` 接口。并实现其中的 `invoke()` 方法。
   
2. `InvocationHandlerAdapter#invoke` 方法中新建 `InvocationHandlerContext` 实例作为 
   `ChainableInvocationContext` 的入参，并返回 `context.proceed()` 方法的执行。
   
3. 实现 `org.geektimes.interceptor.dynamic.InterceptorDynamicEnhancer` 类中的 `enhance()` 方法，
   返回入参 target 的一个代理类。
   
4. 新增 `org.geektimes.interceptor.dynamic.IEchoService` 接口，用于提供服务。
5. 完成测试用例：`org.geektimes.interceptor.dynamic.Week4Test`。
   * 调用 echo(Object) 方法时，看到控制台打印的线程不为 main 线程，说明 `@Asynchronous` 使用生效。
   ![img_4.png](img_4.png)
   * 调用 echo(Long) 方法时，看到控制台有打印入参。该方法没有返回 `UnsupportedOperationException` 说明 `@Fallback` 使用生效。
   ![img_3.png](img_3.png)
     
## WEEK3 作业路径
1. 新建 `org.geektimes.rest.client.HttpPostInvocation` 类并实现 `javax.ws.rs.client.Invocation`。
实现主要方法 `invoke()` 增加 POST 方法的参数处理。

2. 修改 `org.geektimes.rest.client.DefaultInvocationBuilder.buildPost` 方法，该方法返回 1 中的 `HttpPostInvocation` 实例。

3. 在 week1 中的 demo 工程中，开放对应的 actuator endpoint。并新增 `org.geektime.week1.controller.EchoServiceController` 
   提供 echo 方法。
   
4. 新增 `org.geektimes.microprofile.rest.Week3Demo` 测试类，分别对其中的 `ActuatorService` 及 `EchoService` 进行调用。
调用情况：
   ![img.png](img.png)
   ![img_1.png](img_1.png)
   
*注：前两次作业的 README 在子工程内*
