package enjoy.the.music;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> b7a2272ce6b3c11cf6bacafc1e2a8b56dec71c06
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
<<<<<<< HEAD
=======
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
>>>>>>> b7a2272ce6b3c11cf6bacafc1e2a8b56dec71c06
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
<<<<<<< HEAD
import enjoy.the.music.main.adapter.NetMusicListAdapter;

public class NetLayout extends LinearLayout implements MusicLayIntenface{
=======

public class NetLayout extends LinearLayout implements MusicLayIntenface {
>>>>>>> f767f3b04496eed7720946af6d1ac8164198fa07
>>>>>>> b7a2272ce6b3c11cf6bacafc1e2a8b56dec71c06

	
	public static final int DOWN_MUSIC=0;
	public static final int LISTEN_MUSIC=1;
	public static final int MUSIC_LIST_CHANGE=2;
	View rootview;
	View lodingview;
	ListView netlistview;
	LayoutInflater inflater;
	Context context;
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> b7a2272ce6b3c11cf6bacafc1e2a8b56dec71c06
	//ArrayList<Music> netMusics;
	//begin add by helizhi at 2016.06.11
	MusicOnlinedapter adapter;
	//NetMusicListAdapter adapter;
	private EditText search_content;
	private ImageButton search_btn;
	//end add by helizhi at 2016.06.11
<<<<<<< HEAD
=======
=======
	ArrayList<Music> netMusics;
	MusicOnlinedapter adapter;
>>>>>>> f767f3b04496eed7720946af6d1ac8164198fa07
>>>>>>> b7a2272ce6b3c11cf6bacafc1e2a8b56dec71c06
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
=======
<<<<<<< HEAD
>>>>>>> b7a2272ce6b3c11cf6bacafc1e2a8b56dec71c06
		loadNetData();
		//initData();
		initListener();
	}

	//add by helizhi at 2016.06.11
	/*
<<<<<<< HEAD
=======
=======
		initData();
		initListener();
	}

>>>>>>> f767f3b04496eed7720946af6d1ac8164198fa07
>>>>>>> b7a2272ce6b3c11cf6bacafc1e2a8b56dec71c06
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				Toast.makeText(context, "���ֵ�ַxml�������",
				// xml������ɵ���ʾ
						Toast.LENGTH_SHORT).show();
				lodingview.setVisibility(View.GONE);
				break;
			case 1:
				// xml�н�����һ��music
				// ����listView
				adapter.addMusic((Music) msg.obj);
				break;
			default:
				lodingview.setVisibility(View.GONE);
				break;
			}
		}
	};
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> b7a2272ce6b3c11cf6bacafc1e2a8b56dec71c06
	*/
	//end by helizhi at 2016.06.11
	
	//add by helizhi at 2016.06.11
	private int page = 1;//�������ֵ�ҳ��
	//��������
	
    private void searchMusic() {
        //���ؼ���
        AppUtils.hideInputMethod(search_content);
        //��ȡ���������
        String key = search_content.getText().toString();
        if (TextUtils.isEmpty(key)){//���Ϊ��,��,Toast��ʾ
            Toast.makeText(MyApplication.context,"��������ֻ���",Toast.LENGTH_SHORT).show();
            return;
        }
        //���item ʹ��SearchMusicUtils�������ֹ�����,��,ʹ�ù۲������ģʽ,�Լ��ص�,�Լ�����
        SearchMusicUtils.getInstance().setListener(new SearchMusicUtils.OnSearchResultListener(){
            @Override
            public void onSearchResult(ArrayList<Music> results) {
                ArrayList<Music> sr = adapter.getSearchResults();
                sr.clear();
                sr.addAll(results);
                adapter.notifyDataSetChanged();//�������������б�
            }
        }).search(key,page);
    }
	
	
	private ArrayList<Music> netMusics = new ArrayList<Music>();
<<<<<<< HEAD
=======
=======

>>>>>>> f767f3b04496eed7720946af6d1ac8164198fa07
>>>>>>> b7a2272ce6b3c11cf6bacafc1e2a8b56dec71c06
	@Override
	public void initView() {
		intent = new Intent(Constant.ACTION_NET_PLAY);
		netlistview = (ListView) findViewById(R.id.lvSounds);
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> b7a2272ce6b3c11cf6bacafc1e2a8b56dec71c06
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
	
	//��������б�
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
		            //�����¼�
		            searchMusic();
		            break;
		        }					
			}			 
		}

	private void loadNetData() {
		//lodingview.setVisibility(View.VISIBLE);
        //�����������ֵ��첽����
        new LoadNetDataTask().execute(Constant.MIGU_CHINA);
    }
	
	//�����������ֵ��첽����
    //Android1.5�ṩ�� ������ android.os.AsyncTask����ʹ�����첽�����ø��Ӽ򵥣�������Ҫ��д�����̺߳�Handlerʵ�����������ͬ������
    class LoadNetDataTask extends AsyncTask<String,Integer,Integer>{
        //onPreExecute����������ִ�к�̨����ǰ��һЩUI����
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // lodingview.setVisibility(View.VISIBLE);//����layout.��ʾ
            netlistview.setVisibility(View.GONE);//item.����
            netMusics.clear();//�������.����
        }

        //doInBackground�����ڲ�ִ�к�̨����,�����ڴ˷������޸�UI
        @Override
        protected Integer doInBackground(String... params) {
            String url = params[0];
            try {
                //url = "http://music.migu.cn/webfront/searchNew/searchAll.do?keyword=%E5%88%98%E5%BE%B7%E5%8D%8E&keytype=all&pagesize=20&pagenum=1";
                //ʹ��Jsoup�����������,��������������
                Document doc = Jsoup.connect(url).userAgent(Constant.USER_AGENT).timeout(6000).get();
                System.out.println("start**********doc**********doc**********doc**********");
                System.out.println(doc);
                System.out.println(" end **********doc**********doc**********doc**********");

                //��doc��������html����;������span��ǩ��fl song_name���ҳ���,����songTitles������;��,��������;
                //��doc��������html����;������span��ǩ��fl singer_name.mr5t���ҳ���,����artists������;��,���ּ���;
                Elements songTitles = doc.select("span.fl.song_name");
                System.out.println(songTitles);
                Elements artists = doc.select("span.fl.singer_name.mr5");
                System.out.println(artists);

                for (int i=0;i<songTitles.size();i++){
                	Music music = new Music();

                    //a����,����urls������;��,����url����;
                    //a����,��һ��a����,href���Ե�ֵ;��,���յ�url;
                    //a����,��һ��a����,text(a���ӵ�����,��:>���ɴ<,���ɴ����a���ӵ�����);��,���յĸ���;
                    Elements urls = songTitles.get(i).getElementsByTag("a");              
                    music.setMusicPath(urls.get(0).attr("href"));//�������յ�url
                    music.setMusicName(urls.get(0).text());//�������յĸ���

                    //a����,����urls������;��,����url����;
                    Elements artistElements = artists.get(i).getElementsByTag("a");
                    music.setSinger(artistElements.get(0).text());//�������յĸ���

                    music.setAlbumName("�����");//�������յ�ר��

                    System.out.println("@searchResult : " + music);
                    netMusics.add(music);//�����յ�������Ϣ,��ӵ�����
                    
                }
                System.out.println("@searchResults : " + netMusics);               
            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            }
            return 1;
        }

        //onPostExecute����������ִ�����̨��������UI,��ʾ���
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
<<<<<<< HEAD
=======
=======
		lodingview=rootview.findViewById(R.id.lodinginfo);
		netMusics = new ArrayList<Music>();
		adapter = new MusicOnlinedapter(context, netMusics, netlistview);
		netlistview.setAdapter(adapter);
	}

>>>>>>> f767f3b04496eed7720946af6d1ac8164198fa07
>>>>>>> b7a2272ce6b3c11cf6bacafc1e2a8b56dec71c06
	@Override
	public void initData() {
		new Thread() {
			@Override
			public void run() {
				try {
					// ���xml�ļ���������
					InputStream in = HttpTool.getStream(HttpTool.URI+"sounds.xml", null, null, HttpTool.GET);
					// ����xml�ļ�
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
<<<<<<< HEAD
    */
	//end add by helizhi at 2016.06.11
	
=======

>>>>>>> f767f3b04496eed7720946af6d1ac8164198fa07
>>>>>>> b7a2272ce6b3c11cf6bacafc1e2a8b56dec71c06
	@Override
	public void initListener() {
	
	}

	@Override
	public void Refresh(Object... obj) {
          int flag=Integer.parseInt(String.valueOf(obj[0]));
          int n=Integer.parseInt(String.valueOf(obj[1]));
		if (flag==DOWN_MUSIC) {
			Toast.makeText(context, "���ظ������Ϊ"+""+n, 0).show();
		}else if(flag==LISTEN_MUSIC){
			Toast.makeText(context, "���Ÿ������Ϊ"+""+n, 0).show();
		}else if(flag==MUSIC_LIST_CHANGE){
			adapter.notifyDataSetChanged();
		}
		
	}

<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> b7a2272ce6b3c11cf6bacafc1e2a8b56dec71c06

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
	}	

<<<<<<< HEAD
=======
=======
>>>>>>> f767f3b04496eed7720946af6d1ac8164198fa07
>>>>>>> b7a2272ce6b3c11cf6bacafc1e2a8b56dec71c06
}
