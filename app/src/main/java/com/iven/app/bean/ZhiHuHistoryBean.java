package com.iven.app.bean;

import java.util.List;

/**
 * @author Iven
 * @date 2017/2/20 17:54
 * @Description
 */

public class ZhiHuHistoryBean {

    /**
     * date : 20141031
     * stories : [{"images":["http://pic3.zhimg.com/e22345ed46bbd400360ab70164a9d3aa.jpg"],"type":0,"id":4255499,"ga_prefix":"103122","title":"深夜食堂 · 入戏"},{"images":["http://pic3.zhimg.com/3896e6e4e6160e0ada14d1693ef75c60.jpg"],"type":0,"id":4273634,"ga_prefix":"103121","title":"月球上都是陨石坑，那陨石哪去了？"},{"images":["http://pic4.zhimg.com/8b17ecd17426bb78809e012dc45f70a0.jpg"],"type":0,"id":4273558,"ga_prefix":"103120","title":"很多人都极爱吃柿饼，你们知道是怎么做出来的吗"},{"images":["http://pic2.zhimg.com/c9ce7881f01e61302a3ab6d7224cb777.jpg"],"type":0,"id":4274826,"ga_prefix":"103119","title":"一个小视频，看看美国民众对穆斯林的态度是怎么样的"},{"images":["http://pic4.zhimg.com/772eb05d314ae3e6b803246d6b0d4536.jpg"],"type":0,"id":4276536,"ga_prefix":"103118","title":"整点儿新闻 · 今天的头条，毫无疑问只能是这条"},{"title":"爱穿冲锋衣的程序员，是时候换身行头了（多图）","ga_prefix":"103118","images":["http://pic4.zhimg.com/b741a7cd4a77de76c8ed50fc4b112832.jpg"],"multipic":true,"type":0,"id":4273908},{"title":"库克的信其实是温和的提醒：要持续改变社会，只有说个不停","theme":{"id":5,"subscribed":false,"name":"大公司日报"},"ga_prefix":"103117","images":["http://pic3.zhimg.com/d95fd56e4b8d5a6cffdf10d6cf2a5fe2.jpg"],"type":2,"id":4275087},{"title":"饿了 · 把万圣节的南瓜都吃掉吃掉！（多图）","ga_prefix":"103117","images":["http://pic2.zhimg.com/860622b06c7087c8b99583f86aa2f081.jpg"],"multipic":true,"type":0,"id":4275207},{"images":["http://pic3.zhimg.com/1e4890c441b40335e599f012ba897ddd.jpg"],"type":0,"id":4274061,"ga_prefix":"103116","title":"冲进下水道的东西，后来都去了哪里？"},{"images":["http://pic1.zhimg.com/a01e3d42c1de582ec458978272cd92c9.jpg"],"type":0,"id":4273465,"ga_prefix":"103115","title":"如果你可以回到过去杀死你的祖父，事情将会怎样？"},{"title":"有钱又有人，科研实力杠杠的就是中科院（多图）","ga_prefix":"103114","images":["http://pic3.zhimg.com/2b4bfdb672e2168b4b47adb8c333f812.jpg"],"multipic":true,"type":0,"id":4273942},{"images":["http://pic4.zhimg.com/13875e9ccc4a6f6ab7d22cade91a0866.jpg"],"type":0,"id":4275363,"ga_prefix":"103113","title":"三星第三季度跌惨了，未来几年的路比较艰难"},{"images":["http://pic4.zhimg.com/f4d1a753f2b94c8207f29af1fe7e04be.jpg"],"type":0,"id":4271189,"ga_prefix":"103112","title":"Apple Pay 和支付宝合作，可不是在 iPhone 里用支付宝这么简单"},{"images":["http://pic2.zhimg.com/4555b9358b0c7525060ada9417e078b0.jpg"],"type":0,"id":4271851,"ga_prefix":"103111","title":"想买防护 PM2.5 的口罩，雾霾区市民请认准这些"},{"images":["http://pic1.zhimg.com/f3dd7d2c15d3de2b802f9fdf4dce2ebb.jpg"],"type":0,"id":4272814,"ga_prefix":"103110","title":"未来汽车发动机到底是什么动力的，现在还说不准"},{"images":["http://pic2.zhimg.com/7e3ae6f83908f579675fbcb0204b4984.jpg"],"type":0,"id":4273767,"ga_prefix":"103109","title":"蔡依林和李健为什么被选为《中国正在听》导师？"},{"images":["http://pic2.zhimg.com/5b274148513a34cb93b2e0b14e87779f.jpg"],"type":0,"id":4272042,"ga_prefix":"103108","title":"现在什么饮食习惯最健康？"},{"theme":{"id":5,"subscribed":false,"name":"大公司日报"},"type":2,"id":4274225,"ga_prefix":"103107","title":"Tim Cook 出柜宣言全文（英文）"},{"images":["http://pic1.zhimg.com/7cfe9e948bb49ca955b680f6e87ef6f6.jpg"],"type":0,"id":4273710,"ga_prefix":"103107","title":"阿里注册「双十一」商标后，其他人就不能用了？"},{"images":["http://pic3.zhimg.com/8b3d1b1c1204838a7fb9c2465ec99dcb.jpg"],"type":0,"id":4272855,"ga_prefix":"103107","title":"为什么《我爱我家》之后，英达就再没有同样水平的作品出现了？"},{"images":["http://pic2.zhimg.com/7d31dc7abd19507273fdb62cb3b6d52d.jpg"],"type":0,"id":4275055,"ga_prefix":"103107","title":"在微博上被很多很多女人叫「老公」的就是他"},{"images":["http://pic4.zhimg.com/f5caf57fa27a1ea9dc1fddd3f8cd9762.jpg"],"type":0,"id":4274913,"ga_prefix":"103106","title":"瞎扯 · 如何正确地吐槽"}]
     */

    private String date;
    private List<StoriesBean> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public static class StoriesBean {
        /**
         * images : ["http://pic3.zhimg.com/e22345ed46bbd400360ab70164a9d3aa.jpg"]
         * type : 0
         * id : 4255499
         * ga_prefix : 103122
         * title : 深夜食堂 · 入戏
         * multipic : true
         * theme : {"id":5,"subscribed":false,"name":"大公司日报"}
         */

        private int type;
        private int id;
        private String ga_prefix;
        private String title;
        private boolean multipic;
        private ThemeBean theme;
        private List<String> images;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isMultipic() {
            return multipic;
        }

        public void setMultipic(boolean multipic) {
            this.multipic = multipic;
        }

        public ThemeBean getTheme() {
            return theme;
        }

        public void setTheme(ThemeBean theme) {
            this.theme = theme;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public static class ThemeBean {
            /**
             * id : 5
             * subscribed : false
             * name : 大公司日报
             */

            private int id;
            private boolean subscribed;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public boolean isSubscribed() {
                return subscribed;
            }

            public void setSubscribed(boolean subscribed) {
                this.subscribed = subscribed;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
