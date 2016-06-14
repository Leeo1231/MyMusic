package enjoy.the.music.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> b7a2272ce6b3c11cf6bacafc1e2a8b56dec71c06
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
<<<<<<< HEAD
=======
=======
>>>>>>> f767f3b04496eed7720946af6d1ac8164198fa07
>>>>>>> b7a2272ce6b3c11cf6bacafc1e2a8b56dec71c06

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


<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> b7a2272ce6b3c11cf6bacafc1e2a8b56dec71c06
import com.tarena.fashionmusic.util.Constant;
import com.tarena.fashionmusic.util.HttpTool;
import com.tarena.fashionmusic.util.StreamTool;
import com.tarena.fashionmusic.util.DownloadUtils;
<<<<<<< HEAD
=======
=======
import com.tarena.fashionmusic.util.HttpTool;
import com.tarena.fashionmusic.util.StreamTool;
>>>>>>> f767f3b04496eed7720946af6d1ac8164198fa07
>>>>>>> b7a2272ce6b3c11cf6bacafc1e2a8b56dec71c06

import enjoy.the.music.MainActivity;
import enjoy.the.music.R;
import enjoy.the.music.db.MusicDao;
import enjoy.the.music.entry.Music;
<<<<<<< HEAD
import enjoy.the.music.entry.SearchResult;
=======
<<<<<<< HEAD
import enjoy.the.music.entry.SearchResult;
=======
>>>>>>> f767f3b04496eed7720946af6d1ac8164198fa07
>>>>>>> b7a2272ce6b3c11cf6bacafc1e2a8b56dec71c06

public class DownloadService extends Service {
	private static final int MSG_OK = 1;// 下载完成
	private static final int MSG_ERROR = 2;// 下载错误
	private static final int MSG_START = 0;// 开始下载
	// 任务队列
	private ArrayList<Music> taskQueue;
	// 任务轮询线程
	private Thread thread;
	// Service是否已经unbind的标识值
	private boolean isUnbind = false;

	// 当前下载文件的总长度
	private long fileLength;
	// 当前下载的音乐文件名
	private String currentMusicName;
<<<<<<< HEAD
	
	private ExecutorService mThreadPool;
=======
<<<<<<< HEAD
	
	private ExecutorService mThreadPool;
=======
>>>>>>> f767f3b04496eed7720946af6d1ac8164198fa07
>>>>>>> b7a2272ce6b3c11cf6bacafc1e2a8b56dec71c06

	// binder对象，用于在Activity中向Service通信
	public class MyBinder extends Binder {
		// 添加新任务(此方法在activity中调用，在此service中执行)
		public void addTask(Music task) {
			if (!taskQueue.contains(task)) {
				taskQueue.add(task);
				synchronized (thread) {
					thread.notify();
				}
			}
		}
	}

	// 线程通信对象
	private Handler handler;

	@Override
	public IBinder onBind(Intent intent) {
		// 返回binder对象
		return new MyBinder();
	}

<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> b7a2272ce6b3c11cf6bacafc1e2a8b56dec71c06
	//获取下载音乐的URL
    private String getDownloadMusicURL(Music music) {           
                //http://music.migu.cn/#/album/1003215276
                //1003215279
                //http://music.migu.cn/order/1003215279/down/self/P2Z3Y12L1N2/3/001002A/1003215279
                System.out.println("music.getMusicPath() = " + music.getMusicPath());
                String[] aa = music.getMusicPath().split("/");
                String sn = aa[5];
                System.out.println("歌曲编号 = " + sn);
                String url = Constant.MIGU_DOWN_HEAD + sn + Constant.MIGU_DOWN_FOOT;
                System.out.println("歌曲下载页面url = " + url);
                String result = url;
                try {
                    Document doc = Jsoup.connect(url).userAgent(Constant.USER_AGENT).timeout(6000).get();
                    //System.out.println(doc);

                    System.out.println("doc.toString() = " + doc.toString());
                    String[] bb = doc.toString().split("song");//把 下载页面源码 按照"song"分割
                    for (int i=0;i<bb.length;i++){
                        System.out.println("bb[" + i + "] = " + bb[i]);
                        if (bb[i].indexOf("mp3?msisdn")>0){
                            System.out.println("mp3?msisdn = " + bb[i]);
                            String initMp3Url = bb[i];//initMp3Url 初始Mp3下载链接,如下
                            //mp3?msisdn = ":"http://tyst.migu.cn/public/ringmaker01/10月31日中文延期/文件/全套格式/9000首/全曲试听/Mp3_128_44_16/一起走过的日子-刘德华.mp3?msisdn\u003d7b609763f0ff","

                            String[] arrayHttp = initMp3Url.split("http");//把 初始Mp3下载链接 按照"http"分割
                            String[] arrayMp3 = arrayHttp[1].split(".mp3");//把 arrayHttp 按照".mp3"分割
                            result = "http" + arrayMp3[0] + ".mp3";//把分割去掉的"http"和".mp3",组合回来
                            System.out.println("DownloadUtils.getDownloadMusicURL.result = " + result);
                            break;
                            //String result = "http://tyst.migu.cn/public/ringmaker01/10月31日中文延期/文件/全套格式/9000首/全曲试听/Mp3_128_44_16/一起走过的日子-刘德华.mp3";                                                   
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("获取歌曲下载页面url失败 ");
                }
                return result;
            }
        
    
	
	
<<<<<<< HEAD
=======
=======
>>>>>>> f767f3b04496eed7720946af6d1ac8164198fa07
>>>>>>> b7a2272ce6b3c11cf6bacafc1e2a8b56dec71c06
	@Override
	public void onCreate() {
		super.onCreate();
		// 初始化任务队列
<<<<<<< HEAD
		mThreadPool = Executors.newSingleThreadExecutor();
=======
<<<<<<< HEAD
		mThreadPool = Executors.newSingleThreadExecutor();
=======
>>>>>>> f767f3b04496eed7720946af6d1ac8164198fa07
>>>>>>> b7a2272ce6b3c11cf6bacafc1e2a8b56dec71c06
		taskQueue = new ArrayList<Music>();
		// 创建任务轮询线程
		thread = new Thread() {
			@Override
			public void run() {
				while (true) {
					// 当任务队列中有任务时，循环下载任务
					while (taskQueue.size() > 0) {
						// 下载任务
						try {
							// 取出任务队列中的第一条任务
							Music task = taskQueue.remove(0);
							// //获取当前下载的任务的文件长度
<<<<<<< HEAD
							//String uri = HttpTool.URI + task.getMusicPath();
							String uri = getDownloadMusicURL(task);
							
=======
<<<<<<< HEAD
							//String uri = HttpTool.URI + task.getMusicPath();
							String uri = getDownloadMusicURL(task);
							
=======
							String uri = HttpTool.URI + task.getMusicPath();
>>>>>>> f767f3b04496eed7720946af6d1ac8164198fa07
>>>>>>> b7a2272ce6b3c11cf6bacafc1e2a8b56dec71c06
							fileLength = HttpTool.getLength(uri, null, null,HttpTool.GET) / 1024;
							// 获取当前下载的任务的文件名
							currentMusicName = task.getMusicName();
							// 开始下载时发送消息回主线程
							handler.sendEmptyMessage(MSG_START);
							// 下载文件
							InputStream in = HttpTool.getStream(uri, null,
									null, HttpTool.GET);
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> b7a2272ce6b3c11cf6bacafc1e2a8b56dec71c06
							
							String pathName = task.getSavePath() + "/" + task.getMusicName() + ".mp3";
							System.out.println("HELIZHI save music path = " + pathName);
							StreamTool.save(in, pathName, handler,fileLength);
<<<<<<< HEAD
=======
=======
							StreamTool.save(in, task.getSavePath(), handler,
									fileLength);
>>>>>>> f767f3b04496eed7720946af6d1ac8164198fa07
>>>>>>> b7a2272ce6b3c11cf6bacafc1e2a8b56dec71c06
							// 下载完成时发送消息回主线程
							Message msg = handler.obtainMessage(MSG_OK, task);
							handler.sendMessage(msg);
						} catch (IOException e) {
							e.printStackTrace();
							// 下载失败时发送消息回主线程
							handler.sendEmptyMessage(MSG_ERROR);
						}

					}
					// 如果任务列表中所有任务都已下载完成，且service已经解绑，
					// 则停止service，退出线程
					if (isUnbind) {
						stopSelf();
						break;
					}

					// 如果任务列表为空，且未解绑，则线程等待
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
		// 线程运行
		thread.start();
		// 创建handler对象
		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// 获得通知管理器对象
				NotificationManager manager = (NotificationManager) getApplication()
						.getSystemService(NOTIFICATION_SERVICE);
				// 创建通知对象
				Notification noti = new Notification(
						android.R.drawable.stat_sys_download, "通知",
						System.currentTimeMillis());
				// 设置有通知时闪灯
				// noti.defaults = Notification.DEFAULT_LIGHTS;
				// 设置通知不能清除
				noti.flags = Notification.FLAG_NO_CLEAR;
				// 设置通知显示自定义View视图
				noti.contentView = new RemoteViews(getApplication()
						.getPackageName(), R.layout.notiitem);
				// 设置通知的PendingIntent
				noti.contentIntent = PendingIntent.getActivity(
						DownloadService.this, 0, new Intent(
								DownloadService.this, MainActivity.class),
						PendingIntent.FLAG_UPDATE_CURRENT);
				switch (msg.what) {
				case MSG_START:// 开始下载
					// 设置通知显示的音乐名
					noti.contentView.setTextViewText(R.id.tvMusicName_noti,
							currentMusicName);
					// 设置通知中的文件总长度
					noti.contentView.setTextViewText(R.id.tvFileLength,
							String.valueOf(fileLength) + "kb");
					// 设置通知中的已下载的长度
					noti.contentView
							.setTextViewText(R.id.tvLoadedLength, "0kb");
					// 设置通知中的进度条的当前进度
					noti.contentView.setProgressBar(R.id.progressBar1,
							(int) fileLength, 0, false);
					// 发送通知到通知栏
					manager.notify(0, noti);
					break;
				case StreamTool.MSG_PROGRESS:// 下载进行中
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
				case MSG_OK:// 下载完成
					// music信息添加到loadedmusic表中
					MusicDao musicDao = new MusicDao(DownloadService.this);
					musicDao.insert((Music) msg.obj);
					// 发送下载完成的广播
					Intent intent = new Intent(
							DownloadManager.ACTION_DOWNLOAD_COMPLETE);
					Bundle bundle = new Bundle();
					bundle.putSerializable("music", (Music) msg.obj);
					intent.putExtras(bundle);
					sendBroadcast(intent);
					Toast.makeText(DownloadService.this, "下载完成", 1).show();
				case MSG_ERROR:// 下载出错
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
		// 当service解绑时，设置inUnbind为true
		isUnbind = true;
		return super.onUnbind(intent);
	}

}
