package www.wanandroid.com.wanandroid.service.bean;

import android.text.TextUtils;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class IndexArticle {


    /**
     * curPage : 2
     * datas : [{"apkLink":"","author":"请叫我百米冲刺","chapterId":100,"chapterName":"RecyclerView","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":3421,"link":"https://www.jianshu.com/p/e01543e5164c","niceDate":"2018-09-19","origin":"","projectLink":"","publishTime":1537340291000,"superChapterId":54,"superChapterName":"5.+高新技术","tags":[],"title":"改造LayoutManager实现不一样的列表效果","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"Mitaer ","chapterId":70,"chapterName":"retrofit","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":3420,"link":"https://www.jianshu.com/p/437d0f032ed4","niceDate":"2018-09-19","origin":"","projectLink":"","publishTime":1537339698000,"superChapterId":98,"superChapterName":"网络访问","tags":[],"title":"Android 自动登录&mdash;&mdash;持久化Cookie","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"字节跳动技术团队","chapterId":135,"chapterName":"二维码","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":3419,"link":"https://www.jianshu.com/p/76cd4e8805ec","niceDate":"2018-09-18","origin":"","projectLink":"","publishTime":1537272162000,"superChapterId":135,"superChapterName":"项目必备","tags":[],"title":"二维码扫描优化","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"小编","chapterId":274,"chapterName":"个人博客","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":3418,"link":"http://www.androidblog.cn/","niceDate":"2018-09-18","origin":"","projectLink":"","publishTime":1537272011000,"superChapterId":272,"superChapterName":"导航主Tab","tags":[{"name":"导航","url":"/navi#274"}],"title":"Android Blog 周刊 ","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"wurensen","chapterId":400,"chapterName":"ViewPager","collect":false,"courseId":13,"desc":"1.支持ViewPager按需添加、删除视图，以及局部刷新； 2.修复多场景下ViewPager.PageTransformer返回的position错误，让开发者专注于动画实现； 3.修复ViewPager的width、paddingLeft、paddingRight、...\r\n","envelopePic":"http://wanandroid.com/blogimgs/55d773d6-5fda-4e1c-a8b5-e61999ecbdf0.png","fresh":false,"id":3415,"link":"http://www.wanandroid.com/blog/show/2360","niceDate":"2018-09-18","origin":"","projectLink":"https://github.com/wurensen/GraceViewPager","publishTime":1537270220000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=400"}],"title":"修复多场景下ViewPager问题 GraceViewPager","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"darryrzhong","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"仿照(开眼视频)Android端(旧版UI,新版UI已改变)做的一个App，每天更新一个精美短视频应用，一个非常美的短视频应用，UI界面基本上是参照开眼视频Android端来做的。 在该项目中，视频播放框架采用的是Vitamio的视频播放器框架.","envelopePic":"http://wanandroid.com/blogimgs/41979276-c4f2-4219-8655-4ebdf1168b52.png","fresh":false,"id":3417,"link":"http://www.wanandroid.com/blog/show/2362","niceDate":"2018-09-18","origin":"","projectLink":"https://github.com/darryrzhong/OpenEyes","publishTime":1537270148000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"Android入门开源项目之仿开眼视频APP OpenEyes","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"鸿洋","chapterId":298,"chapterName":"我的博客","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":3408,"link":"http://www.wanandroid.com/blog/show/2358","niceDate":"2018-09-18","origin":"","projectLink":"","publishTime":1537269950000,"superChapterId":298,"superChapterName":"原创文章","tags":[],"title":"分享一个学习的小技巧","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"陪代码度过的漫长岁月","chapterId":121,"chapterName":"ViewPager","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":3414,"link":"https://blog.csdn.net/wurensen/article/details/81390641","niceDate":"2018-09-18","origin":"","projectLink":"","publishTime":1537255208000,"superChapterId":26,"superChapterName":"常用控件","tags":[],"title":"ViewPager源码分析（发现刷新数据的正确使用姿势）","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"啸天AskSky","chapterId":261,"chapterName":"屏幕适配","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":3412,"link":"https://www.jianshu.com/p/96265f9ab386","niceDate":"2018-09-17","origin":"","projectLink":"","publishTime":1537190968000,"superChapterId":135,"superChapterName":"项目必备","tags":[],"title":"Android齐刘海适配完全攻略","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"yellowcath","chapterId":97,"chapterName":"音视频","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":3411,"link":"https://www.jianshu.com/p/863b65e4a9e4","niceDate":"2018-09-17","origin":"","projectLink":"","publishTime":1537190928000,"superChapterId":95,"superChapterName":"多媒体技术","tags":[],"title":"Android高仿抖音照片电影功能","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"Pqpo","chapterId":95,"chapterName":"相机Camera","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":3410,"link":"https://pqpo.me/2018/09/12/android-camera-real-time-scanning/","niceDate":"2018-09-17","origin":"","projectLink":"","publishTime":1537190905000,"superChapterId":95,"superChapterName":"多媒体技术","tags":[],"title":"Android 端相机视频流采集与实时边框识别","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"SelfZhangTQ","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"艺术帮是一款艺术图片应用，采用LiveData+ViewModel+RxJava+okHttp+Retrofit+Glide架构的项目,仅用于学习交流，不断完善中。\r\n","envelopePic":"http://www.wanandroid.com/blogimgs/427f123b-defd-4c21-90e4-08c494fa3cb4.png","fresh":false,"id":3407,"link":"http://www.wanandroid.com/blog/show/2357","niceDate":"2018-09-16","origin":"","projectLink":"https://github.com/SelfZhangTQ/T-MVVM","publishTime":1537107087000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"艺术图片应用 T-MVVM","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"afkT","chapterId":367,"chapterName":"资源聚合类","collect":false,"courseId":13,"desc":"DevUtils 是一个 Android 工具库, 主要根据不同功能模块，封装快捷使用的工具类及 API 方法调用。 该项目尽可能的便于开发人员，快捷、快速开发安全可靠的项目，以及内置部分常用的资源文件，如color.xml、(toast) layout.xml等\r\n","envelopePic":"http://www.wanandroid.com/resources/image/pc/default_project_img.jpg","fresh":false,"id":3406,"link":"http://www.wanandroid.com/blog/show/2356","niceDate":"2018-09-16","origin":"","projectLink":"https://github.com/afkT/DevUtils","publishTime":1537107048000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=367"}],"title":"一个 Android 工具库 DevUtils","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"zqljintu","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"这是一个简单的练手小项目，项目基于MPV的设计模式打造，我把她命名为&ldquo;记忆胶囊&rdquo;。我记性不好，又不太喜欢记日记，而且对手机自带的备忘录程序不是特别满意，于是萌生了一个这样的想法。\r\n    界面是采用的Material design设计语言，模仿的网上的一些界面，加入了一些自己的原创，该App有创建不同事件，日历查找，搜索，界面皮肤等模块。可以满足不同的用户需求。","envelopePic":"http://wanandroid.com/blogimgs/2ee0f1a3-18b5-44ab-a5f0-1425b9a19ae1.png","fresh":false,"id":3404,"link":"http://www.wanandroid.com/blog/show/2354","niceDate":"2018-09-16","origin":"","projectLink":"https://github.com/zqljintu/Memory-capsule","publishTime":1537094095000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"Memory-capsule（一款备忘录小App&ldquo;记忆胶囊&rdquo;）","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"zeleven","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"玩Android（http://www.wanandroid.com/） APP（MVP + RxJava2 + Retrofit2 + Dagger2）\r\n","envelopePic":"http://wanandroid.com/blogimgs/9027b14c-6916-4e39-9325-2e744208e990.png","fresh":false,"id":3403,"link":"http://www.wanandroid.com/blog/show/2353","niceDate":"2018-09-16","origin":"","projectLink":"https://github.com/zeleven/playa","publishTime":1537094061000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"玩Android APP（MVP + RxJava2 + Retrofit2 + Dagger2） ","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"JavaNoober","chapterId":363,"chapterName":"创意汇","collect":false,"courseId":13,"desc":"通过标签直接生成shape，无需再写shape.xml ","envelopePic":"http://wanandroid.com/blogimgs/e07361cf-1a9e-4bda-be19-ee8f277a1aa2.png","fresh":false,"id":3402,"link":"http://www.wanandroid.com/blog/show/2352","niceDate":"2018-09-16","origin":"","projectLink":"https://github.com/JavaNoober/BackgroundLibrary","publishTime":1537094003000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=363"}],"title":"通过标签直接生成shape，无需再写shape.xml BackgroundLibrary","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"速查小编","chapterId":398,"chapterName":"速查","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":3400,"link":"http://www.wanandroid.com/blog/show/2350","niceDate":"2018-09-15","origin":"","projectLink":"","publishTime":1537008401000,"superChapterId":398,"superChapterName":"速查知识表","tags":[],"title":"速查 | ascii 码表","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"YzyCoding","chapterId":363,"chapterName":"创意汇","collect":false,"courseId":13,"desc":"支付宝商家语音播报 PushVoiceBroadcast\r\n\r\n","envelopePic":"http://www.wanandroid.com/blogimgs/c3542f2d-c2fb-41ab-bd7f-0440ab5e2435.png","fresh":false,"id":3398,"link":"http://www.wanandroid.com/blog/show/2348","niceDate":"2018-09-13","origin":"","projectLink":"https://github.com/YzyCoding/PushVoiceBroadcast","publishTime":1536854300000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=363"}],"title":"支付宝商家语音播报 PushVoiceBroadcast","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"XBaron","chapterId":228,"chapterName":"辅助 or 工具类","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":3397,"link":"https://www.jianshu.com/p/7b354eb8d0d3","niceDate":"2018-09-13","origin":"","projectLink":"","publishTime":1536854182000,"superChapterId":135,"superChapterName":"项目必备","tags":[],"title":"Android优雅地处理按钮重复点击","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"PrototypeZ","chapterId":268,"chapterName":"优秀的设计","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":3396,"link":"https://www.jianshu.com/p/4a2948ef606d","niceDate":"2018-09-13","origin":"","projectLink":"","publishTime":1536854080000,"superChapterId":135,"superChapterName":"项目必备","tags":[],"title":"不需要再手写 onSaveInstanceState 了，因为你的时间非常值钱","type":0,"userId":-1,"visible":1,"zan":0}]
     * offset : 20
     * over : false
     * pageCount : 84
     * size : 20
     * total : 1677
     */

    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private List<DatasBean> datas;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean implements MultiItemEntity {
        public static final int TEXT = 1;
        public static final int IMG = 2;
        /**
         * apkLink :
         * author : 请叫我百米冲刺
         * chapterId : 100
         * chapterName : RecyclerView
         * collect : false
         * courseId : 13
         * desc :
         * envelopePic :
         * fresh : false
         * id : 3421
         * link : https://www.jianshu.com/p/e01543e5164c
         * niceDate : 2018-09-19
         * origin :
         * projectLink :
         * publishTime : 1537340291000
         * superChapterId : 54
         * superChapterName : 5.+高新技术
         * tags : []
         * title : 改造LayoutManager实现不一样的列表效果
         * type : 0
         * userId : -1
         * visible : 1
         * zan : 0
         */

        private String apkLink;
        private String author;
        private int chapterId;
        private String chapterName;
        private boolean collect;
        private int courseId;
        private String desc;
        private String envelopePic;
        private boolean fresh;
        private int id;
        private String link;
        private String niceDate;
        private String origin;
        private String projectLink;
        private long publishTime;
        private int superChapterId;
        private String superChapterName;
        private String title;
        private int type;
        private int userId;
        private int visible;
        private int zan;
        private List<?> tags;

        public String getApkLink() {
            return apkLink;
        }

        public void setApkLink(String apkLink) {
            this.apkLink = apkLink;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getChapterId() {
            return chapterId;
        }

        public void setChapterId(int chapterId) {
            this.chapterId = chapterId;
        }

        public String getChapterName() {
            return chapterName;
        }

        public void setChapterName(String chapterName) {
            this.chapterName = chapterName;
        }

        public boolean isCollect() {
            return collect;
        }

        public void setCollect(boolean collect) {
            this.collect = collect;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getEnvelopePic() {
            return envelopePic;
        }

        public void setEnvelopePic(String envelopePic) {
            this.envelopePic = envelopePic;
        }

        public boolean isFresh() {
            return fresh;
        }

        public void setFresh(boolean fresh) {
            this.fresh = fresh;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getNiceDate() {
            return niceDate;
        }

        public void setNiceDate(String niceDate) {
            this.niceDate = niceDate;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getProjectLink() {
            return projectLink;
        }

        public void setProjectLink(String projectLink) {
            this.projectLink = projectLink;
        }

        public long getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }

        public int getSuperChapterId() {
            return superChapterId;
        }

        public void setSuperChapterId(int superChapterId) {
            this.superChapterId = superChapterId;
        }

        public String getSuperChapterName() {
            return superChapterName;
        }

        public void setSuperChapterName(String superChapterName) {
            this.superChapterName = superChapterName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getVisible() {
            return visible;
        }

        public void setVisible(int visible) {
            this.visible = visible;
        }

        public int getZan() {
            return zan;
        }

        public void setZan(int zan) {
            this.zan = zan;
        }

        public List<?> getTags() {
            return tags;
        }

        public void setTags(List<?> tags) {
            this.tags = tags;
        }

        @Override
        public int getItemType() {
            return TextUtils.isEmpty(getEnvelopePic())?TEXT:IMG;
        }
    }
}
