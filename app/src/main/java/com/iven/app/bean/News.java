package com.iven.app.bean;

import java.util.List;

/**
 * @author Iven
 * @date 2017/1/18 11:27
 * @Description
 */

public class News {

    /**
     * date : 20170118
     * stories : [{"images":["http://pic2.zhimg.com/7e540455b279150bbcee49c09bd02441.jpg"],"type":0,"id":9157797,"ga_prefix":"011811","title":"想载人登陆火星，一定要突破「化学能动力时代」才行吗？"},{"images":["http://pic3.zhimg.com/037b92cb6eb027c8512a9d626ab94a12.jpg"],"type":0,"id":9149471,"ga_prefix":"011810","title":"最好的幼儿园应该是什么样子？"},{"images":["http://pic1.zhimg.com/3923df57633b76c70f85a7a80ae3d21c.jpg"],"type":0,"id":9157232,"ga_prefix":"011809","title":"刚刚穿上新的身体，我该怎么控制自己？"},{"images":["http://pic4.zhimg.com/55973836a6885752cd52de7136e82dd7.jpg"],"type":0,"id":9156669,"ga_prefix":"011808","title":"三国鼎立里的博弈，就像是一条蜈蚣"},{"images":["http://pic1.zhimg.com/94bc8747ae95b8d2287e6f615e0561ac.jpg"],"type":0,"id":9157284,"ga_prefix":"011808","title":"NBA 感人的比赛或者画面有哪些？"},{"images":["http://pic4.zhimg.com/da4d5d51a98737293b2695ecf23344f3.jpg"],"type":0,"id":9157287,"ga_prefix":"011807","title":"熬夜很累，为什么反而还有一点点兴奋？"},{"images":["http://pic1.zhimg.com/d8270c7b711da0ddb64830374f0f6858.jpg"],"type":0,"id":9157394,"ga_prefix":"011807","title":"先不管是不是真爱，起码一夫一妻有利于后代"},{"images":["http://pic3.zhimg.com/84799c353f8c02bdbb2888d6e5e45f22.jpg"],"type":0,"id":9155327,"ga_prefix":"011806","title":"瞎扯 · 如何正确地吐槽"}]
     * top_stories : [{"image":"http://pic3.zhimg.com/ca334f453d5ebd6234a1982786488bba.jpg","type":0,"id":9157394,"ga_prefix":"011807","title":"先不管是不是真爱，起码一夫一妻有利于后代"},{"image":"http://pic2.zhimg.com/b24c3fc6aa7aeda20a386b5cf9122f01.jpg","type":0,"id":9157284,"ga_prefix":"011808","title":"NBA 感人的比赛或者画面有哪些？"},{"image":"http://pic3.zhimg.com/18110ba0132779fa16930bbeb1d26242.jpg","type":0,"id":9156024,"ga_prefix":"011717","title":"我们就是剧组里的机器猫，导演要什么都得立马变出来"},{"image":"http://pic4.zhimg.com/9df2571b517e0e1cd7a5713d7db40067.jpg","type":0,"id":9155883,"ga_prefix":"011716","title":"美图秀秀上市，谁的钱翻了 20 倍，谁早早退出有点亏？"},{"image":"http://pic2.zhimg.com/2f1cff972588cd4947b1f376a9a39211.jpg","type":0,"id":9154689,"ga_prefix":"011713","title":"要命还是要梦？乐视和贾跃亭最终选择了命"}]
     */

    private String date;
    private List<StoriesBean> stories;
    private List<TopStoriesBean> top_stories;

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

    public List<TopStoriesBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoriesBean> top_stories) {
        this.top_stories = top_stories;
    }

    public static class StoriesBean {
        /**
         * images : ["http://pic2.zhimg.com/7e540455b279150bbcee49c09bd02441.jpg"]
         * type : 0
         * id : 9157797
         * ga_prefix : 011811
         * title : 想载人登陆火星，一定要突破「化学能动力时代」才行吗？
         */

        private int type;
        private int id;
        private String ga_prefix;
        private String title;
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

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }

    public static class TopStoriesBean {
        /**
         * image : http://pic3.zhimg.com/ca334f453d5ebd6234a1982786488bba.jpg
         * type : 0
         * id : 9157394
         * ga_prefix : 011807
         * title : 先不管是不是真爱，起码一夫一妻有利于后代
         */

        private String image;
        private int type;
        private int id;
        private String ga_prefix;
        private String title;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

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
    }
}
