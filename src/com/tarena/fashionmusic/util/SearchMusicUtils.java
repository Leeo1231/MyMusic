package com.tarena.fashionmusic.util;


import android.os.Handler;
import android.os.Message;

import enjoy.the.music.entry.Music;
import enjoy.the.music.entry.SearchResult;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.parsers.ParserConfigurationException;


/**
 * Created by helizhi on 2016/6/10.
 * �������ֹ�����
 */
public class SearchMusicUtils {
	private static final int SIZE = 20;//��ѯ��������
	
	private static SearchMusicUtils sInstance;
    private OnSearchResultListener mListener;

    private ExecutorService mThreadPool;//�̳߳�
    
    public synchronized static SearchMusicUtils getInstance(){
        if (sInstance == null){
            try {
                sInstance = new SearchMusicUtils();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
        }
        return sInstance;
    }
    
  //���������¼�
    private SearchMusicUtils() throws ParserConfigurationException{
        mThreadPool = Executors.newSingleThreadExecutor();//�������̳߳�
    }
    
    public SearchMusicUtils setListener(OnSearchResultListener l){
        mListener = l;
        return this;
    }
    
    public void search(final String key,final int page){
        final Handler handler = new Handler(){//handler ���ڷ�������
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case Constant.SUCCESS:
                        if (mListener != null) mListener.onSearchResult((ArrayList<Music>)msg.obj);
                        break;
                    case Constant.FAILED:
                        if (mListener != null) mListener.onSearchResult(null);
                        break;
                }
            }
        };

        //ִ���߳�
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {//run����
                ArrayList<Music> results = getMusicList(key,page);
                if(results == null){
                    handler.sendEmptyMessage(Constant.FAILED);
                    return;
                }
                handler.obtainMessage(Constant.SUCCESS,results).sendToTarget();
            }
        });

    }

    //ʹ��Jsoup�����������,��������������
    private ArrayList<Music> getMusicList(final String key,final int page){
        final String start = String.valueOf((page - 1) * SIZE);
        ArrayList<Music> searchResults = new ArrayList<Music>();
        Document doc = null;
        String URL = null;
        try {

            //�������� ��ȡҳ�� ���key��ת�� �޷�����ȷ����
            //ʹ��URLEncoder.encodeת��,תΪutf8
            //���� ת��Ϊ %E6%B2%A1%E6%9C%89";
            String keyUrlEnCode = URLEncoder.encode(key, "utf8");
            URL = Constant.MIGU_SEARCH_HEAD + keyUrlEnCode +  Constant.MIGU_SEARCH_FOOT;
            System.out.println(URL);
            doc = Jsoup.connect(URL)
                    .data("query", "Java")
                    .userAgent(Constant.USER_AGENT)
                    .timeout(60 * 1000).get();
            //System.out.println("~~doc = " + doc);

            Elements songTitles = doc.select("span.fl.song_name");
            System.out.println(songTitles);
            Elements artists = doc.select("span.fl.singer_name.mr5");
            System.out.println(artists);
            for (int i=0;i<songTitles.size();i++){
                Music searchResult = new Music();
                //System.out.println("@searchResult : " + searchResult);

                //<a href="/song/121353608"
                //<a href="/song/264506450"
                //a����,����urls������;��,����url����;
                //a����,��һ��a����,href���Ե�ֵ;��,���յ�url;
                //a����,��һ��a����,text(a���ӵ�����,��:>���ɴ<,���ɴ����a���ӵ�����);��,���յĸ���;
                Elements urls = songTitles.get(i).getElementsByTag("a");
                //System.out.println("@urls : " + urls);
                searchResult.setMusicPath(urls.get(0).attr("href"));//�������յ�url
                searchResult.setMusicName(urls.get(0).text());//�������յĸ���

                //a����,����urls������;��,����url����;
                Elements artistElements = artists.get(i).getElementsByTag("a");
                //System.out.println("@artistElements : " + artistElements);
                searchResult.setSinger(artistElements.get(0).text());//�������յĸ���

                searchResult.setAlbumName("�����");//�������յ�ר��

                System.out.println("@searchResult : " + searchResult);
                searchResults.add(searchResult);//�����յ�������Ϣ,��ӵ�����
            }
            System.out.println("@searchResults : " + searchResults);

            return searchResults;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public interface OnSearchResultListener {
        public void onSearchResult(ArrayList<Music> results);
    }

}
