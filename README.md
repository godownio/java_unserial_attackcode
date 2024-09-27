需要哪个payload就进去运行哪个。生成的字节码在项目文件ser.bin，可自行送往base64编码
项目运行需要排除ctf_complates_exclude，othercase目录
试运行，请修改至漏洞sdk，如CC1 JDK<=8U65
java目录下为三个恶意类加载源文件，分别是：
* JNDI开HTTP所需恶意类，JNDI_RuntimeEvil
* TemplatesImpl恶意类反弹shell，TemplatesImpl_bash_shell
* TemplatesImpl弹计算器（本次测试使用），TemplatesImpl_RuntimeEvil
类加载时自行更改文件路径
其他漏洞分析需要的文件：
* AnnotationInvocationHandler调试，JDK8u65 sun包：https://hg.openjdk.org/jdk8u/jdk8u/jdk/archive/af660750b2f4.zip
* Tomcat 8.5.56源码，Shiro分析Tomcat自定义双亲委派，请将java设置为源代码目录：https://github.com/godownio/java_tools/blob/main/apache-tomcat-8.5.56-src.zip
ctf目录加了依赖包也能用，自己看需求吧
