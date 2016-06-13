package enjoy.the.music;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
<<<<<<< HEAD
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import enjoy.the.music.MainActivity.NetMusicListener;
import enjoy.the.music.entry.SearchResult;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import enjoy.the.music.R;


import com.tarena.fashionmusic.util.SearchMusicUtils;
import com.tarena.fashionmusic.MusicLayIntenface;
import com.tarena.fashionmusic.MyApplication;
import com.tarena.fashionmusic.util.AppUtils;
=======
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.tarena.fashionmusic.MusicLayIntenface;
>>>>>>> f767f3b04496eed7720946af6d1ac8164198fa07
import com.tarena.fashionmusic.util.Constant;
import com.tarena.fashionmusic.util.HttpTool;

import enjoy.the.music.entry.Music;
import enjoy.the.music.entry.NetMusic;
import enjoy.the.music.lrc.xml.MusicXmlParser;
import enjoy.the.music.main.adapter.MusicOnlinedapter;
<<<<<<< HEAD
import enjoy.the.music.main.adapter.NetMusicListAdapter;

public class NetLayout extends LinearLayout implements MusicLayIntenface{
=======

public class NetLayout extends LinearLayout implements MusicLayIntenface {
>>>>>>> f767f3b04496eed7720946af6d1ac8164198fa07

	
	public static final int DOWN_MUSIC=0;
	public static final int LISTEN_MUSIC=1;
	public static final int MUSIC_LIST_CHANGE=2;
	View rootview;
	View lodingview;
	ListView netlistview;
	LayoutInflater inflater;
	Context context;
<<<<<<< HEAD
	//ArrayList<Music> netMusics;
	//begin add by helizhi at 2016.06.11
	MusicOnlinedapter adapter;
	//NetMusicListAdapter adapter;
	private EditText search_content;
	private ImageButton search_btn;
	//end add by helizhi at 2016.06.11
=======
	ArrayList<Music> netMusics;
	MusicOnlinedapter adapter;
>>>>>>> f767f3b04496eed7720946af6d1ac8164198fa07
	Intent intent;
    NetMusic nownMusic;
	public NetLayout(Context context) {
		super(context);
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT));
		rootview = inflater.inflate(R.layout.netmusiclist, this, true);
		
		
		initView();
<<<<<<< HEAD
		loadNetData();
		//initData();
		initListener();
	}

	//add by helizhi at 2016.06.11
	/*
=======
		initData();
		initListener();
	}

>>>>>>> f767f3b04496eed7720946af6d1ac8164198fa07
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				Toast.makeText(context, "音乐地址xml解析完成",
				// xml解析完成的提示
						Toast.LENGTH_SHORT).show();
				lodingview.setVisibility(View.GONE);
				break;
			case 1:
				// xml中解析出一条music
				// 更新listView
				adapter.addMusic((Music) msg.obj);
				break;
			default:
				lodingview.setVisibility(View.GONE);
				break;
			}
		}
	};
<<<<<<< HEAD
	*/
	//end by helizhi at 2016.06.11
	
	//add by helizhi at 2016.06.11
	private int page = 1;//搜索音乐的页码
	//搜索音乐
	
    private void searchMusic() {
        //隐藏键盘
        AppUtils.hideInputMethod(search_content);
        //获取输入的文字
        String key = search_content.getText().toString();
        if (TextUtils.isEmpty(key)){//如果为空,则,Toast提示
            Toast.makeText(MyApplication.context,"请输入歌手或歌词",Toast.LENGTH_SHORT).show();
            return;
        }
        //填充item 使用SearchMusicUtils搜索音乐工具类,并,使用观察者设计模式,自己回调,自己监听
        SearchMusicUtils.getInstance().setListener(new SearchMusicUtils.OnSearchResultListener(){
            @Override
            public void onSearchResult(ArrayList<Music> results) {
                ArrayList<Music> sr = adapter.getSearchResults();
                sr.clear();
                sr.addAll(results);
                adapter.notifyDataSetChanged();//更新网络音乐列表
            }
        }).search(key,page);
    }
	
	
	private ArrayList<Music> netMusics = new ArrayList<Music>();
=======

>>>>>>> f767f3b04496eed7720946af6d1ac8164198fa07
	@Override
	public void initView() {
		intent = new Intent(Constant.ACTION_NET_PLAY);
		netlistview = (ListView) findViewById(R.id.lvSounds);
<<<<<<< HEAD
		//lodingview=rootview.findViewById(R.id.lodinginfo);
		
		//add by helizhi at 2016.06.11
		//adapter = new MusicOnlinedapter(context, netMusics, netlistview);
		search_content = (EditText) rootview.findViewById(R.id.searchmusic);
		search_btn = (ImageButton) rootview.findViewById(R.id.searchbutton);
		
		search_btn.setOnClickListener(new NetMusicSearchListener());
		//search_btn.setOnClickListener(this);
		//end add by helizhi at 2016.06.11
		//netlistview.setAdapter(adapter);		
	}
	
	
	//add by helizhi at 2016.06.11	
	
	//网络歌曲列表
	class NetMusicSearchListener implements View.OnClickListener{
		@Override
		public void onClick(View v) {
		// TODO Auto-generated method stub
			switch (v.getId()){
			/*
		    	case R.id.ll_search_btn_container:
		        	ll_search_btn_container.setVisibility(View.GONE);
		            ll_search_container.setVisibility(View.VISIBLE);
		            break;
		    */
		        case R.id.searchbutton:
		            //搜索事件
		            searchMusic();
		            break;
		        }					
			}			 
		}

	private void loadNetData() {
		//lodingview.setVisibility(View.VISIBLE);
        //加载网络音乐的异步任务
        new LoadNetDataTask().execute(Constant.MIGU_CHINA);
    }
	
	//加载网络音乐的异步任务
    //Android1.5提供了 工具类 android.os.AsyncTask，它使创建异步任务变得更加简单，不再需要编写任务线程和Handler实例即可完成相同的任务。
    class LoadNetDataTask extends AsyncTask<String,Integer,Integer>{
        //onPreExecute方法用于在执行后台任务前做一些UI操作
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // lodingview.setVisibility(View.VISIBLE);//加载layout.显示
            netlistview.setVisibility(View.GONE);//item.隐藏
            netMusics.clear();//搜索结果.清理
        }

        //doInBackground方法内部执行后台任务,不可在此方法内修改UI
        @Override
        protected Integer doInBackground(String... params) {
            String url = params[0];
            try {
                //url = "http://music.migu.cn/webfront/searchNew/searchAll.do?keyword=%E5%88%98%E5%BE%B7%E5%8D%8E&keytype=all&pagesize=20&pagenum=1";
                //使用Jsoup组件请求网络,并解析音乐数据
                Document doc = Jsoup.connect(url).userAgent(Constant.USER_AGENT).timeout(6000).get();
                System.out.println("start**********doc**********doc**********doc**********");
                System.out.println(doc);
                System.out.println(" end **********doc**********doc**********doc**********");

                //从doc分析以上html代码;把所有span标签下fl song_name查找出来,存在songTitles集合中;即,歌名集合;
                //从doc分析以上html代码;把所有span标签下fl singer_name.mr5t查找出来,存在artists集合中;即,歌手集合;
                Elements songTitles = doc.select("span.fl.song_name");
                System.out.println(songTitles);
                Elements artists = doc.select("span.fl.singer_name.mr5");
                System.out.println(artists);

                for (int i=0;i<songTitles.size();i++){
                	Music music = new Music();

                    //a链接,存在urls集合中;即,歌曲url集合;
                    //a链接,第一个a连接,href属性的值;即,最终的url;
                    //a链接,第一个a连接,text(a链接的内容,例:>半壶纱<,半壶纱就是a链接的内容);即,最终的歌名;
                    Elements urls = songTitles.get(i).getElementsByTag("a");              
                    music.setMusicPath(urls.get(0).attr("href"));//设置最终的url
                    music.setMusicName(urls.get(0).text());//设置最终的歌名

                    //a链接,存在urls集合中;即,歌曲url集合;
                    Elements artistElements = artists.get(i).getElementsByTag("a");
                    music.setSinger(artistElements.get(0).text());//设置最终的歌手

                    music.setAlbumName("华语榜");//设置最终的专辑

                    System.out.println("@searchResult : " + music);
                    netMusics.add(music);//把最终的所有信息,添加到集合
                    
                }
                System.out.println("@searchResults : " + netMusics);               
            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            }
            return 1;
        }

        //onPostExecute方法用于在执行完后台任务后更新UI,显示结果
        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (result==1){
                //netMusicListAdapter = new NetMusicListAdapter(mainActivity,searchResults);
                //System.out.println(searchResults);
            	System.out.println("@HELIZHI result==1");  
            	//adapter = new NetMusicListAdapter(context,searchResults);
            	adapter = new MusicOnlinedapter(context, netMusics, netlistview);
            	adapter.setSearchResults(netMusics);
            	netlistview.setAdapter(adapter);
               // listView_net_music.addFooterView(LayoutInflater.from(mainActivity).inflate(R.layout.footviwe_layout,null));
            }
            //lodingview.setVisibility(View.GONE);
            netlistview.setVisibility(View.VISIBLE);

            }
        }
	
	
	/*
=======
		lodingview=rootview.findViewById(R.id.lodinginfo);
		netMusics = new ArrayList<Music>();
		adapter = new MusicOnlinedapter(context, netMusics, netlistview);
		netlistview.setAdapter(adapter);
	}

>>>>>>> f767f3b04496eed7720946af6d1ac8164198fa07
	@Override
	public void initData() {
		new Thread() {
			@Override
			public void run() {
				try {
					// 获得xml文件的输入流
					InputStream in = HttpTool.getStream(HttpTool.URI+"sounds.xml", null, null, HttpTool.GET);
					// 解析xml文件
					new MusicXmlParser(handler).parse(in);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	
	}
<<<<<<< HEAD
    */
	//end add by helizhi at 2016.06.11
	
=======

>>>>>>> f767f3b04496eed7720946af6d1ac8164198fa07
	@Override
	public void initListener() {
	
	}

	@Override
	public void Refresh(Object... obj) {
          int flag=Integer.parseInt(String.valueOf(obj[0]));
          int n=Integer.parseInt(String.valueOf(obj[1]));
		if (flag==DOWN_MUSIC) {
			Toast.makeText(context, "下载歌曲序号为"+""+n, 0).show();
		}else if(flag==LISTEN_MUSIC){
			Toast.makeText(context, "播放歌曲序号为"+""+n, 0).show();
		}else if(flag==MUSIC_LIST_CHANGE){
			adapter.notifyDataSetChanged();
		}
		
	}

<<<<<<< HEAD

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
	}	

=======
>>>>>>> f767f3b04496eed7720946af6d1ac8164198fa07
}
