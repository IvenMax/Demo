使用方法：
声明全局变量：private LoadingUtils mLoadingUtils;
实例化对象(上下文一定要传Activity)：mLoadingUtils = LoadingUtils.getInstance(this);
启动：mLoadingUtils.startShowLoading();
停止：mLoadingUtils.stopShowLoading();