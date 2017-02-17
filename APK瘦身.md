1、减少切片，大图，及背景图
2、图片压缩
3、采用压缩率更高的webp图片格式，代替当前的png格式
4、清理不需要的布局文件，XML格式资源文件
5、删除一些用户量极少，“无意义”的功能
6、检查第三方包，把不需要的组件、图片之类的删除或替换
7、降低so库的体积
8、把部分页面做成H5，客户端删除这部分功能
9、自查代码，尤其是逻辑相似的部分。删除冗余代码
10、降低插件体积


1、lint使用
     采用lint工具，删除了大量无用的资源。有一定作用。Android studio集成了lint工具，检测“unused resoure“及unused declaration等。这里我们使用lint检测了无用的资源文件。
2、其他团队so库的体积减少，作用明显著
3、代码冗余部分：效果非常有限
4、使用图片压缩工具，有一定作用
    Google推荐图片压缩工具：https://developers.google.com/speed/docs/insights/OptimizeImages，市面上有许多工具可用来对JPEG和PNG文件执行进一步的无损压缩，且不会对图片质量造成任何影响。对于JPEG文件，我们建议您使用jpegtran或jpegoptim（仅适用于Linux；使用--strip-all选项运行）。对于PNG文件，我们建议使用OptiPNG或PNGOUT。
5、H5页面，作用明显
6、支持插件 so，插件支持网络加载so及更新原则， 作用明显
7、jar包资源混淆