# 作者简介: 
冰河，高级软件架构师，Java编程专家，Spring、MySQL内核专家，Mykit系列开源框架创始人、首席架构师及开发者，Android开源消息组件Android-MQ独立作者，国内知名开源分布式数据库中间件Mycat核心架构师、开发者，精通Java, C, C++, Python, Hadoop大数据生态体系，熟悉MySQL、Redis内核，Android底层架构。多年来致力于分布式系统架构、微服务、分布式数据库、大数据技术的研究，曾主导过众多分布式系统、微服务及大数据项目的架构设计、研发和实施落地。在高并发、高可用、高可扩展性、高可维护性和大数据等领域拥有丰富的经验。对Hadoop、Spark、Storm等大数据框架源码进行过深度分析并具有丰富的实战经验，《海量数据处理与大数据技术实战》作者。

# 作者联系方式
QQ：2711098650

# 项目简述
mykit-chat是基于Netty实现的实时聊天系统服务器端，目前实现的功能如下：  
1. 支持昵称登录；  
2. 支持多人同时在线；  
3. 同步显示在线人数；  
4. 支持文字和表情的内容；  
5. 浏览器与服务器保持长连接，定时心跳检测；  

# 项目实现逻辑
1. 服务器端使用Netty作为通信框架，支持客户端通过WebSocket通信。服务器会检测链路是否处于空闲，如果60秒内没有收到客户端的任何消息，那么服务器会主动关闭该链路。  
2. 为保证链路的可用性，服务器会定时发送Ping消息给客户端，客户端收到Ping消息后，必须回一个Pong消息响应，避免链路由于空闲而被关闭。  
3. 客户端连接服务器之后，需要提供昵称给服务器，服务器保存每一个用户的昵称和相关的链路信息，用于后续的聊天显示。  
4. 客户端发送聊天消息到服务器之后，服务器不会存储聊天消息，而是直接转发给其它的客户端。  

# 项目协议

协议比较简单，所有的消息都一个Json字符串，格式如下：  
`head | body | extend`  

* head作为头部，用int类型存储，4个字节；
* body 消息的有效载体，用string类型存储，长度无限度；
* extend 协议的扩展字段，用map类型存储；
  
由于在解码消息时使用的是Netty自带的WebSocket的解码器，只支持文本帧的消息，解码出来的都是一个完整的帧消息，即上面格式的消息，所以协议上没有用长度字段。

# 项目结构说明
* mykit-chat-cache：mykit-chat项目的缓存模块，主要的功能是缓存客户端的连接信息和连接的管道信息等；
* mykit-chat-config： mykit-chat项目的配置模块，主要的功能是为项目提供统一的配置与获取配置项的入口；
* mykit-chat-constants：mykit-chat项目的常量模块，存储项目中所涉及的所有常量信息；
* mykit-chat-entity：mykit-chat项目的实体类模块，存储项目中所涉及的所有实体类信息；
* mykit-chat-netty： mykit-chat项目的核心模块，基于Netty实现的消息收发逻辑均在此模块实现；
* mykit-chat-proto：mykit-chat项目的协议模块，提供统一的协议封装；
* mykit-chat-utils：mykit-chat项目的通用工具类模块，提供统一的工具类；
* mykit-chat-core：mykit-chat项目的启动模块，也是整个项目入口；

# 项目启动类
整个服务端项目的启动类为mykit-chat-core模块下的```io.mykit.chat.core.MykitChatStarter```类。

# 项目打包发布
1. 安装Maven；  
2. 下载mykit-chat，下载链接为：https://github.com/sunshinelyz/mykit-chat 
3. 在命令行将当前目录切换到mykit-chat下，如下所示  
```cd mykit-chat```  
4. 执行Maven命令进行打包，如下所示  
```mvn clean package install```  
5. 将mykit-chat-core模块下的target目录下的mykit-chat-core-1.0.0-SNAPSHOT-jar-with-dependencies.jar复制到需要运行项目的目录  
6. 执行如下命令运行mykit-chat服务端  
```nohup java -jar mykit-chat-core-1.0.0-SNAPSHOT-jar-with-dependencies.jar >> /dev/null &```

# 注意事项
1. 目前项目只是一个简单的聊天模型，需要不断优化升级；  
2. 目前服务器未存储任何消息记录，而是直接将消息转发给其他客户端；  
3. 目前项目仅支持将所有客户端接入到同一个聊天组中；  
4. 目前项目仅支持JVM本地缓存，未使用任何缓存数据库或中间件；  

# 项目待升级部分
1. 支持私聊；  
2. 支持多群组；  
3. 支持修改昵称，增加个性签名；  
4. 支持发送图片、音频、视频、网页链接等；  
5. 支持语音、视频等；  
6. 消息体加密与解密；  
7. 接入Redis实现缓存数据，实现重启服务端，客户端不掉线；  
8. 其他待升级部分以后再定
