package com.tarena.fashionmusic.util;


public class Constant {

	
	public static final String ACTION_PLAY = "com.tarena.action.PLAY";
	public static final String ACTION_NET_PLAY = "com.tarena.action.NET_PLAY";
	public static final String ACTION_PAUSE = "com.tarena.action.PAUSE";
	public static final String ACTION_STOP = "com.tarena.action.STOP";
	public static final String ACTION_SEEK = "com.tarena.action.SEEK";
	public static final String ACTION_PREVIOUS = "com.tarena.action.PREVIOUS";
	public static final String ACTION_NEXT = "com.tarena.action.NEXT";
	public static final String ACTION_JUMR = "com.tarena.action.JUMP";
	public static final String ACTION_JUMR_OTHER = "com.tarena.action.JUMP_OTHER";
	public static final String ACTION_FIND = "com.tarena.action.FIND";
	public static final String ACTION_UPDATE = "com.tarena.action.UPDATE";
	public static final String ACTION_UPDATE_ALL = "com.tarena.action.UPDATE_ALL";
	public static final String ACTION_UPDATE_TIME = "com.tarena.action.UPDATE_TIME";
	public static final String ACTION_UPDATE_LRC = "com.tarena.action.UPDATE_LRC";
	public static final String ACTION_SERVICESTOPED = "com.tarena.action.SERVICESTOPED";
	public static final String ACTION_LISTCHANGED = "com.tarena.action.LISTCHANGED";
	public static final String ACTION_DISS_DIALOG = "com.tarena.action.DISSDIALOG";
	public static final String ACTION_SET_PLAYMODE = "com.tarena.action.PALY_MODE";
	public static final String ACTION_STAR_THREAD = "com.tarena.action.STAR_THREA";
	public static final String ACTION_LRC = "com.tarena.action.lrc";
	
	
	
	

    //中国移动音乐网址
    public static final String MIGU_URL = "http://music.migu.cn";
    //网络音乐界面 默认搜索刘德华
    public static final String MIGU_CHINA = "http://music.migu.cn/webfront/searchNew/searchAll.do?keyword=%E5%88%98%E5%BE%B7%E5%8D%8E&keytype=all&pagesize=20&pagenum=1";
    //下载
    public static final String MIGU_DOWN_HEAD = "http://music.migu.cn/order/";
    public static final String MIGU_DOWN_FOOT = "/down/self/P2Z3Y12L1N2/3/001002A/1003215279";
    //搜索
    public static final String MIGU_SEARCH_HEAD = "http://music.migu.cn/webfront/searchNew/searchAll.do?keyword=";
    public static final String MIGU_SEARCH_FOOT = "&keytype=all&pagesize=20&pagenum=1";
    //歌词  "http://music.baidu.com/search/lrc?key=" + 歌名 + " " + 歌手
    public static final String BAIDU_LRC_SEARCH_HEAD = "http://music.baidu.com/search/lrc?key=";

    //userAgent 属性是一个只读的字符串，声明了浏览器用于 HTTP 请求的用户代理头的值。
    //关于userAgent更多资料请看这里  http://www.w3school.com.cn/jsref/prop_nav_useragent.asp
    //在任何一个可以在线运行html的网站
    //我使用 http://tool.chinadmoz.org/htmlrun.asp
    //运行以下html代码 获得用户代理
    /**
    <html>
    <body>
    <script type="text/javascript">
            document.write("<p>UserAgent: ")
            document.write(navigator.userAgent + "</p>")
    </script>
    </body>
    </html>
     */
    public static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:45.0) Gecko/20100101 Firefox/45.0";
    //public static final String USER_AGENT = "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0; .NET CLR 2.0.50727; SLCC2; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.3; .NET4.0C; Tablet PC 2.0; .NET4.0E)";
    //成功标记
    public static final int SUCCESS = 1;
    //失败标记
    public static final int FAILED = 2;

    public static final String DIR_MUSIC = "/drm_music";
    public static final String DIR_LRC = "/drm_music/lrc/";

}
