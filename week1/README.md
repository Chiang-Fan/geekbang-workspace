## 极客事件小马哥 P7 第一周作业
### 作业要求
1. 参考 `com.salesmanager.shop.tags.CommonResponseHeadersTag` 实现
   ⼀个⾃定义的 Tag，将 Hard Code 的 Header 名值对，变为属性配置的⽅式。
2. 实现自定义标签
3. 部署到 servlet 应用。

### 作业路径
1. 新建 `org.geektime.week1.tag.CommonResponseHeadersTag`。定义了三个字段 `cacheControl`,`pragma`,`expires`。
在 doTag() 方法中，将三个字段填充到 response 的 header 中。
   
2. 并在 `src/main/webapp/WEB-INF/demo1-tags.tld` tld 文件中
定义该类的标签声明。标签的前缀为 `ds` ,有三个字段可填充。
3. 在 `src/main/webapp/pages/index.jsp` 中显式使用了自定义的标签：
   `<ds:common-response-headers cacheControl="no-cache" pragma="no-cache" expires="-1"/>`