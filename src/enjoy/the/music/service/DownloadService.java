package enjoy.the.music.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.RemoteViews;
import android.widget.Toast;


import com.tarena.fashionmusic.util.Constant;
import com.tarena.fashionmusic.util.HttpTool;
import com.tarena.fashionmusic.util.StreamTool;
import com.tarena.fashionmusic.util.DownloadUtils;

import enjoy.the.music.MainActivity;
import enjoy.the.music.R;
import enjoy.the.music.db.MusicDao;
import enjoy.the.music.entry.Music;
import enjoy.the.music.entry.SearchResult;

public class DownloadService extends Service {
	private static final int MSG_OK = 1;// �������
	private static final int MSG_ERROR = 2;// ���ش���
	private static final int MSG_START = 0;// ��ʼ����
	// �������
	private ArrayList<Music> taskQueue;
	// ������ѯ�߳�
	private Thread thread;
	// Service�Ƿ��Ѿ�unbind�ı�ʶֵ
	private boolean isUnbind = false;

	// ��ǰ�����ļ����ܳ���
	private long fileLength;
	// ��ǰ���ص������ļ���
	private String currentMusicName;
	
	private ExecutorService mThreadPool;

	// binder����������Activity����Serviceͨ��
	public class MyBinder extends Binder {
		// ���������(�˷�����activity�е��ã��ڴ�service��ִ��)
		public void addTask(Music task) {
			if (!taskQueue.contains(task)) {
				taskQueue.add(task);
				synchronized (thread) {
					thread.notify();
				}
			}
		}
	}

	// �߳�ͨ�Ŷ���
	private Handler handler;

	@Override
	public IBinder onBind(Intent intent) {
		// ����binder����
		return new MyBinder();
	}

	//��ȡ�������ֵ�URL
    private String getDownloadMusicURL(Music music) {           
                //http://music.migu.cn/#/album/1003215276
                //1003215279
                //http://music.migu.cn/order/1003215279/down/self/P2Z3Y12L1N2/3/001002A/1003215279
                System.out.println("music.getMusicPath() = " + music.getMusicPath());
                String[] aa = music.getMusicPath().split("/");
                String sn = aa[5];
                System.out.println("������� = " + sn);
                String url = Constant.MIGU_DOWN_HEAD + sn + Constant.MIGU_DOWN_FOOT;
                System.out.println("��������ҳ��url = " + url);
                String result = url;
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
                            result = "http" + arrayMp3[0] + ".mp3";//�ѷָ�ȥ����"http"��".mp3",��ϻ���
                            System.out.println("DownloadUtils.getDownloadMusicURL.result = " + result);
                            break;
                            //String result = "http://tyst.migu.cn/public/ringmaker01/10��31����������/�ļ�/ȫ�׸�ʽ/9000��/ȫ������/Mp3_128_44_16/һ���߹�������-���»�.mp3";                                                   
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("��ȡ��������ҳ��urlʧ�� ");
                }
                return result;
            }
        
    
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		// ��ʼ���������
		mThreadPool = Executors.newSingleThreadExecutor();
		taskQueue = new ArrayList<Music>();
		// ����������ѯ�߳�
		thread = new Thread() {
			@Override
			public void run() {
				while (true) {
					// �����������������ʱ��ѭ����������
					while (taskQueue.size() > 0) {
						// ��������
						try {
							// ȡ����������еĵ�һ������
							Music task = taskQueue.remove(0);
							// //��ȡ��ǰ���ص�������ļ�����
							//String uri = HttpTool.URI + task.getMusicPath();
							String uri = getDownloadMusicURL(task);
							
							fileLength = HttpTool.getLength(uri, null, null,HttpTool.GET) / 1024;
							// ��ȡ��ǰ���ص�������ļ���
							currentMusicName = task.getMusicName();
							// ��ʼ����ʱ������Ϣ�����߳�
							handler.sendEmptyMessage(MSG_START);
							// �����ļ�
							InputStream in = HttpTool.getStream(uri, null,
									null, HttpTool.GET);
							
							String pathName = task.getSavePath() + "/" + task.getMusicName() + ".mp3";
							System.out.println("HELIZHI save music path = " + pathName);
							StreamTool.save(in, pathName, handler,fileLength);
							// �������ʱ������Ϣ�����߳�
							Message msg = handler.obtainMessage(MSG_OK, task);
							handler.sendMessage(msg);
						} catch (IOException e) {
							e.printStackTrace();
							// ����ʧ��ʱ������Ϣ�����߳�
							handler.sendEmptyMessage(MSG_ERROR);
						}

					}
					// ��������б�������������������ɣ���service�Ѿ����
					// ��ֹͣservice���˳��߳�
					if (isUnbind) {
						stopSelf();
						break;
					}

					// ��������б�Ϊ�գ���δ������̵߳ȴ�
					try {
						synchronized (this) {
							this.wait();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		};
		// �߳�����
		thread.start();
		// ����handler����
		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// ���֪ͨ����������
				NotificationManager manager = (NotificationManager) getApplication()
						.getSystemService(NOTIFICATION_SERVICE);
				// ����֪ͨ����
				Notification noti = new Notification(
						android.R.drawable.stat_sys_download, "֪ͨ",
						System.currentTimeMillis());
				// ������֪ͨʱ����
				// noti.defaults = Notification.DEFAULT_LIGHTS;
				// ����֪ͨ�������
				noti.flags = Notification.FLAG_NO_CLEAR;
				// ����֪ͨ��ʾ�Զ���View��ͼ
				noti.contentView = new RemoteViews(getApplication()
						.getPackageName(), R.layout.notiitem);
				// ����֪ͨ��PendingIntent
				noti.contentIntent = PendingIntent.getActivity(
						DownloadService.this, 0, new Intent(
								DownloadService.this, MainActivity.class),
						PendingIntent.FLAG_UPDATE_CURRENT);
				switch (msg.what) {
				case MSG_START:// ��ʼ����
					// ����֪ͨ��ʾ��������
					noti.contentView.setTextViewText(R.id.tvMusicName_noti,
							currentMusicName);
					// ����֪ͨ�е��ļ��ܳ���
					noti.contentView.setTextViewText(R.id.tvFileLength,
							String.valueOf(fileLength) + "kb");
					// ����֪ͨ�е������صĳ���
					noti.contentView
							.setTextViewText(R.id.tvLoadedLength, "0kb");
					// ����֪ͨ�еĽ������ĵ�ǰ����
					noti.contentView.setProgressBar(R.id.progressBar1,
							(int) fileLength, 0, false);
					// ����֪ͨ��֪ͨ��
					manager.notify(0, noti);
					break;
				case StreamTool.MSG_PROGRESS:// ���ؽ�����
					noti.contentView.setTextViewText(R.id.tvMusicName_noti,
							currentMusicName);
					noti.contentView.setTextViewText(R.id.tvFileLength,
							String.valueOf(fileLength) + "kb");
					noti.contentView.setTextViewText(R.id.tvLoadedLength,
							String.valueOf(msg.arg1) + "kb");
					noti.contentView.setProgressBar(R.id.progressBar1,
							(int) fileLength, msg.arg1, false);
					manager.notify(0, noti);
					break;
				case MSG_OK:// �������
					// music��Ϣ��ӵ�loadedmusic����
					MusicDao musicDao = new MusicDao(DownloadService.this);
					musicDao.insert((Music) msg.obj);
					// ����������ɵĹ㲥
					Intent intent = new Intent(
							DownloadManager.ACTION_DOWNLOAD_COMPLETE);
					Bundle bundle = new Bundle();
					bundle.putSerializable("music", (Music) msg.obj);
					intent.putExtras(bundle);
					sendBroadcast(intent);
					Toast.makeText(DownloadService.this, "�������", 1).show();
				case MSG_ERROR:// ���س���
					manager.cancel(0);
					break;

				}

			}

		};

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// ��service���ʱ������inUnbindΪtrue
		isUnbind = true;
		return super.onUnbind(intent);
	}

}
