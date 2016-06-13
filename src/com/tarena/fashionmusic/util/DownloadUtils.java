package com.tarena.fashionmusic.util;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import enjoy.the.music.entry.SearchResult;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.parsers.ParserConfigurationException;

/**
 * created by helizhi at 2016.06.10
 * �����������ع�����
 */
public class DownloadUtils {
	private static final int SUCCESS_LRC = 1;//���ظ�ʳɹ�
    private static final int FAILED_LRC = 2;//���ظ��ʧ��
    private static final int SUCCESS_MP3 = 3;//���ظ����ɹ�
    private static final int FAILED_MP3 = 4;//���ظ���ʧ��
    private static final int GET_MP3_URL = 5;//��ȡ�������ص�ַ�ɹ�
    private static final int GET_FAILED_MP3_URL = 6;//��ȡ�������ص�ַʧ��
    private static final int MUSIC_EXISTS = 7;//����ʱ,�����Ѵ���
    
    private static DownloadUtils sInstance;
    private OnDownloadListener mListener;

    private ExecutorService mThreadPool;
    
    /**
     *���ûص�����������
     * @param mListener
     * @return
     */
    public DownloadUtils setListener(OnDownloadListener mListener){
        this.mListener = mListener;
        return this;
    }
    
  //��ȡ���ع��ߵ�ʵ��
    public synchronized static DownloadUtils getsInstance(){
        if (sInstance == null){
            try {
                sInstance = new DownloadUtils();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
        }
        return  sInstance;
    }
    
    /**
     * ���صľ���ҵ�񷽷�
     * @throws ParserConfigurationException
     */
    private DownloadUtils() throws ParserConfigurationException{
        mThreadPool = Executors.newSingleThreadExecutor();
    }
    
    public void download(final SearchResult searchResult){
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
               switch (msg.what){
                   case SUCCESS_LRC:
                       if (mListener != null) mListener.onDowload(searchResult.getMusicName()+".mp3");
                       break;
                   case FAILED_LRC:
                       if (mListener != null) mListener.onFailed(searchResult.getMusicName()+"�ĸ������ʧ��");
                       break;
                   case GET_MP3_URL:
                       System.out.println("GET_MP3_URL:"+msg.obj);
                       downloadMusic(searchResult,(String)msg.obj,this);
                       break;
                   case GET_FAILED_MP3_URL:
                       if (mListener != null) mListener.onFailed(searchResult.getMusicName()+"��MP3����ʧ��");
                       break;
                   case SUCCESS_MP3:
                       //if (mListener != null) mListener.onDowload(Environment.getExternalStorageDirectory()+Constant.DIR_MUSIC + "/" + searchResult.getMusicName()+".mp3");
                       if (mListener != null) mListener.onDowload(searchResult.getMusicName()+".mp3");
                       String url = Constant.MIGU_URL + searchResult.getUrl();
                       System.out.println("download lrc:"+url);
                       downloadLRC(searchResult.getMusicName(),searchResult.getArtist(),this);
                       break;
                   case FAILED_MP3:
                       if (mListener != null) mListener.onFailed(searchResult.getMusicName()+"��MP3����ʧ��");
                       break;
                   case MUSIC_EXISTS:
                       if (mListener != null) mListener.onFailed(searchResult.getMusicName()+"�Ѵ���");
                       break;
               }
            }
        };
        getDownloadMusicURL(searchResult,handler);
    }
    
    
  //��ȡ���ظ�ʵ�URL
    private void getDownloadLrcURL(final SearchResult searchResult, final Handler handler) {
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                //http://music.baidu.com/search/lrc?key=%E6%9C%89%E5%BF%83%E4%BA%BA%20G.E.M.%E9%82%93%E7%B4%AB%E6%A3%8B
                //"http://music.baidu.com/search/lrc?key=" + ���� + " " + ����
                //System.out.println("searchResult.getUrl() = " + searchResult.getUrl());
                //String[] aa = searchResult.getUrl().split("/");
                //String sn = aa[5];
                //System.out.println("������� = " + sn);

                //����������Ƴ�����Url��������,"http://music.baidu.com/search?key=%E6%B2%A1%E6%9C%89";
                //���־���utf8����,���� ���� == %E5%86%B0%E9%9B%A8;
                //�������� ��ȡҳ�� ʹ��"http://music.baidu.com/search?key=����";�޷�����ȷ����
                //����ʹ��URLEncoder.encodeת��,תΪutf8
                //ʵ��ʹ�� ��ȡҳ�� ʹ��"http://music.baidu.com/search?key=%E6%B2%A1%E6%9C%89";
                try {
                    String musicName = URLEncoder.encode(searchResult.getMusicName(), "utf8");
                    String artistName = URLEncoder.encode(searchResult.getArtist(), "utf8");
                    //String url = Constant.BAIDU_LRC_SEARCH_HEAD + searchResult.getMusicName() + " " + searchResult.getArtist();
                    String url = Constant.BAIDU_LRC_SEARCH_HEAD + musicName + "+" + artistName;
                    System.out.println("�������ҳ��url = " + url);

                    Document doc = Jsoup.connect(url).userAgent(Constant.USER_AGENT).timeout(6000).get();
                    //System.out.println("�������ҳ�� doc : " + doc);

                    Elements lrcUrls = doc.select("span.lyric-action");
                    System.out.println(lrcUrls);

                    for (int i=0;i<lrcUrls.size();i++) {
                        Elements urlsa = lrcUrls.get(i).getElementsByTag("a");
                        System.out.println("tag a urlsa : " + urlsa);
                        for (int a=0;i<urlsa.size();a++) {
                            System.out.println("----" + urlsa.get(a).toString());
                            String urla = urlsa.get(a).toString();
                            System.out.println("-----" + urla);
                            //-----<a class="down-lrc-btn { 'href':'/data2/lrc/14488216/14488216.lrc' }" href="#">����LRC���</a>
                            if (urla.indexOf("'href':'")>0){
                                String[] uu = urla.split("'href':'");
                                System.out.println("uu1 : " + uu[1]);
                                //uu1 : /data2/lrc/14488216/14488216.lrc' }" href="#">����LRC���</a>
                                String[] uuu = uu[1].split(".lrc");
                                System.out.println("uuu0 : " + uuu[0]);
                                //uuu0 : /data2/lrc/14488216/14488216
                                String result = "http://music.baidu.com" + uuu[0] + ".lrc";
                                System.out.println("result : " + result);
                                //result :  http://music.baidu.com/data2/lrc/14488216/14488216.lrc
                                Message msg = handler.obtainMessage(SUCCESS_LRC,result);
                                msg.sendToTarget();
                                break;
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    handler.obtainMessage(FAILED_LRC).sendToTarget();
                }

            }
        });
    }
    
    //��ȡ�������ֵ�URL
    private void getDownloadMusicURL(final SearchResult searchResult, final Handler handler) {
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                //http://music.migu.cn/#/album/1003215276
                //1003215279
                //http://music.migu.cn/order/1003215279/down/self/P2Z3Y12L1N2/3/001002A/1003215279
                System.out.println("searchResult.getUrl() = " + searchResult.getUrl());
                String[] aa = searchResult.getUrl().split("/");
                String sn = aa[5];
                System.out.println("������� = " + sn);
                String url = Constant.MIGU_DOWN_HEAD + sn + Constant.MIGU_DOWN_FOOT;
                System.out.println("��������ҳ��url = " + url);

                try {
                    Document doc = Jsoup.connect(url).userAgent(Constant.USER_AGENT).timeout(6000).get();
                    //System.out.println(doc);

                    System.out.println("doc.toString() = " + doc.toString());
                    String[] bb = doc.toString().split("song");//�� ����ҳ��Դ�� ����"song"�ָ�
                    for (int i=0;i<bb.length;i++){
                        System.out.println("bb[" + i + "] = " + bb[i]);
                        if (bb[i].indexOf("mp3?msisdn")>0){
                            System.out.println("mp3?msisdn = " + bb[i]);
                            String initMp3Url = bb[i];//initMp3Url ��ʼMp3��������,����
                            //mp3?msisdn = ":"http://tyst.migu.cn/public/ringmaker01/10��31����������/�ļ�/ȫ�׸�ʽ/9000��/ȫ������/Mp3_128_44_16/һ���߹�������-���»�.mp3?msisdn\u003d7b609763f0ff","

                            String[] arrayHttp = initMp3Url.split("http");//�� ��ʼMp3�������� ����"http"�ָ�
                            String[] arrayMp3 = arrayHttp[1].split(".mp3");//�� arrayHttp ����".mp3"�ָ�
                            String result = "http" + arrayMp3[0] + ".mp3";//�ѷָ�ȥ����"http"��".mp3",��ϻ���
                            System.out.println("DownloadUtils.getDownloadMusicURL.result = " + result);

                            //String result = "http://tyst.migu.cn/public/ringmaker01/10��31����������/�ļ�/ȫ�׸�ʽ/9000��/ȫ������/Mp3_128_44_16/һ���߹�������-���»�.mp3";
                            Message msg = handler.obtainMessage(GET_MP3_URL,result);
                            msg.sendToTarget();
                            break;
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    handler.obtainMessage(GET_FAILED_MP3_URL).sendToTarget();
                }
            }
        });
    }
    
  //���ظ��
    public void downloadLRC(final String musicName, final String artistName, final Handler handler){
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {


                //����������Ƴ�����Url��������,"http://music.baidu.com/search?key=%E6%B2%A1%E6%9C%89";
                //���־���utf8����,���� ���� == %E5%86%B0%E9%9B%A8;
                //�������� ��ȡҳ�� ʹ��"http://music.baidu.com/search?key=����";�޷�����ȷ����
                //����ʹ��URLEncoder.encodeת��,תΪutf8
                //ʵ��ʹ�� ��ȡҳ�� ʹ��"http://music.baidu.com/search?key=%E6%B2%A1%E6%9C%89";
                try {
                    String musicNameEn = URLEncoder.encode(musicName, "utf8");
                    String artistNameEn = URLEncoder.encode(artistName, "utf8");
                    //String url = Constant.BAIDU_LRC_SEARCH_HEAD + searchResult.getMusicName() + " " + searchResult.getArtist();
                    String url = Constant.BAIDU_LRC_SEARCH_HEAD + musicNameEn + "+" + artistNameEn;
                    System.out.println("�������ҳ��url = " + url);

                    Document doc = Jsoup.connect(url).userAgent(Constant.USER_AGENT).timeout(6000).get();
                    //System.out.println("�������ҳ�� doc : " + doc);

                    Elements lrcUrls = doc.select("span.lyric-action");
                    System.out.println(lrcUrls);

                    for (int i = 0; i < lrcUrls.size(); i++) {
                        Elements urlsa = lrcUrls.get(i).getElementsByTag("a");
                        System.out.println("tag a urlsa : " + urlsa);
                        for (int a = 0; a < urlsa.size(); a++) {
                            //System.out.println("----" + urlsa.get(a).toString());
                            String urla = urlsa.get(a).toString();
                            System.out.println("-----" + urla);
                            //-----<a class="down-lrc-btn { 'href':'/data2/lrc/14488216/14488216.lrc' }" href="#">����LRC���</a>
                            if (urla.indexOf("'href':'") > 0) {
                                String[] uu = urla.split("'href':'");
                                System.out.println("uu1 : " + uu[1]);
                                //uu1 : /data2/lrc/14488216/14488216.lrc' }" href="#">����LRC���</a>
                                String[] uuu = uu[1].split(".lrc'");
                                System.out.println("uuu0 : " + uuu[0]);
                                //uuu0 : /data2/lrc/246970367/246970367.lrc
                                String lrcDwonUrl = "http://music.baidu.com" + uuu[0] + ".lrc";
                                System.out.println("lrcDwonUrl : " + lrcDwonUrl);
                                //result :  http://music.baidu.com/data2/lrc/14488216/14488216.lrc

                                //File LrcDirFile = new File(Environment.getExternalStorageDirectory() + "/drm_music");
                                File LrcDirFile = new File(Environment.getExternalStorageDirectory() + Constant.DIR_LRC);
                                System.out.println("LrcDirFile : " + LrcDirFile);
                                if (!LrcDirFile.exists()) {
                                    LrcDirFile.mkdirs();
                                }
                                String target = LrcDirFile + "/" + musicName + ".lrc";
                                System.out.println("lrcDwonUrl : " + lrcDwonUrl);
                                System.out.println("target : " + target);
                                File fileTarget = new File(target);
                                if (fileTarget.exists()) {
                                    handler.obtainMessage(MUSIC_EXISTS).sendToTarget();
                                    return;
                                } else {
                                    OkHttpClient client = new OkHttpClient();
                                    Request request = new Request.Builder().url(lrcDwonUrl).build();
                                    Response response = client.newCall(request).execute();
                                    if (response.isSuccessful()) {
                                        PrintStream ps = new PrintStream(new File(target));
                                        byte[] bytes = response.body().bytes();
                                        ps.write(bytes, 0, bytes.length);
                                        ps.close();
                                        handler.obtainMessage(SUCCESS_LRC, target).sendToTarget();
                                    }
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
    }
    
  //����MP3
    private void downloadMusic(final SearchResult searchResult,final String url,final Handler handler){
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                File musicDirFile = new File(Environment.getExternalStorageDirectory()+Constant.DIR_MUSIC);
                if (!musicDirFile.exists()){
                    musicDirFile.mkdirs();
                }

                String mp3url = url;
                //String mp3url = "http://tyst.migu.cn/public/600902-2008430/tone/2008/09/10/2008��9��/4�»���106�׸���/����/7_mp3-128kbps/����ȵ�����ʹ-��ѧ��.mp3";

                String target = musicDirFile + "/" + searchResult.getMusicName() + ".mp3";
                System.out.println("DownloadUtils.downloadMusic.mp3url = " + mp3url);
                System.out.println("DownloadUtils.downloadMusic.target = " + target);
                File fileTarget = new File(target);
                if (fileTarget.exists()){
                    handler.obtainMessage(MUSIC_EXISTS).sendToTarget();
                    return;
                }else {
                    //ʹ��OkHttpClient���
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(mp3url).build();
                    System.out.println("DownloadUtils.downloadMusic.request = " + request);
                    try {
                        Response response = client.newCall(request).execute();
                        if (response.isSuccessful()){
                            PrintStream ps = new PrintStream(fileTarget);
                            byte[] bytes = response.body().bytes();
                            ps.write(bytes,0,bytes.length);
                            ps.close();
                            handler.obtainMessage(SUCCESS_MP3).sendToTarget();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        handler.obtainMessage(FAILED_MP3).sendToTarget();
                    }
                }
            }
        });
    }
    
  //�Զ��������¼�������
    public interface OnDownloadListener {
        public void onDowload(String mp3Url);
        public void onFailed(String error);
    }
}
