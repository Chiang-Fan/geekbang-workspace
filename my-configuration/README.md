## 极客事件小马哥 P7 第一周作业
### 作业要求
在 my-configuration 基础上，实现 ServletRequest 请求参
数的 ConfigSource（MicroProfile Config）

### 作业路径
1. 在 my-configuration 的基础上，新建 `org.geektimes.configuration.microprofile.config.source.servlet.ServletRequestConfigSource`
类并继承 `org.geektimes.configuration.microprofile.config.source.MapBasedConfigSource` 。

2. 构造方法中，声明 name 及优先级并注入 `servletRequest`. 

3. 实现 `prepareConfigData()` 方法,从 `servletRequest` 中获取 parameter 并添加到 `configData` 中。及 `getValue()` 方法，根据指定的参数名称返回值对象。

4. 添加 spi 文件 `META-INF/services/org.eclipse.microprofile.config.spi.ConfigSource`。并在 spi 文件中添加如下内容：

    org.geektimes.configuration.microprofile.config.source.servlet.ServletRequestConfigSource