package enjoy.the.music.main.adapter;

import java.util.ArrayList;

import enjoy.the.music.entry.SearchResult;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import enjoy.the.music.R;

public class NetMusicListAdapter extends BaseAdapter{
	private Context ctx; //�����Ķ�������
    private ArrayList<SearchResult> searchResults;//���SearchResult���õļ���
    private SearchResult searchResult;//SearchResult��������
    //private int pos = -1;			//�б�λ��

    /**
     * ���캯��
     * @param ctx    ������
     * @param searchResults  ���϶���
     */
    public NetMusicListAdapter(Context ctx, ArrayList<SearchResult> searchResults){
        this.ctx = ctx;
        this.searchResults = searchResults;
        //System.out.println("MyMusicListAdapter.java #0 : ctx = " + ctx + ",mp3Infos = " + mp3Infos.size());
    }

    public ArrayList<SearchResult> searchResults() {
        System.out.println("NetMusicListAdapter.java #1 : public ArrayList<SearchResult> searchResults() {");
        return searchResults;
    }

    public void setSearchResults(ArrayList<SearchResult> searchResults) {
        System.out.println("NetMusicListAdapter.java #2 : public void setMp3Infos(ArrayList<SearchResult> searchResults) {");
        this.searchResults = searchResults;
    }

    public ArrayList<SearchResult> getSearchResults() {
        return searchResults;
    }

    @Override
    public int getCount() {
        //System.out.println("NetMusicListAdapter.java #3 : public int getCount() {" + mp3Infos.size());
        //return mp3Infos.size();
        return searchResults.size();
    }

    @Override
    public Object getItem(int position) {
        System.out.println("NetMusicListAdapter.java #4 : public Object getItem(int position) {");
        return searchResults.get(position);
    }

    @Override
    public long getItemId(int position) {
        //System.out.println("NetMusicListAdapter.java #5 : public long getItemId(int position) {");
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //System.out.println("NetMusicListAdapter.java #6 : public View getView ");
        ViewHolder vh;
        if(convertView==null){
            //vh = new ViewHolder();
            convertView = LayoutInflater.from(ctx).inflate(R.layout.net_play_list_item,null);
            vh = new ViewHolder();
            vh.textView1_title = (TextView) convertView.findViewById(R.id.tvMusicName);
            vh.textView2_singer = (TextView) convertView.findViewById(R.id.tvSinger);
            //vh.textView3_time = (TextView) convertView.findViewById(R.id.textView3_time);
            //vh.imageView1_ablum = (ImageView) convertView.findViewById(R.id.imageView1_ablum);

            //System.out.println("NetMusicListAdapter.java #7 : textView1_title = " + vh.textView1_title);
            convertView.setTag(vh);//��ʾ��View���һ����������ݣ�
        }else {
            vh = (ViewHolder)convertView.getTag();//ͨ��getTag�ķ���������ȡ����
        }

        SearchResult searchResult = searchResults.get(position);
        vh.textView1_title.setText(searchResult.getMusicName());//��ʾ����
        vh.textView2_singer.setText(searchResult.getArtist());//��ʾ����
        //vh.textView3_time.setText(MediaUtils.formatTime(mp3Info.getDuration()));//��ʾʱ��

        //��ȡר������ͼƬ
        //Bitmap albumBitmapItem = MediaUtils.getArtwork(ctx,mp3Info.getId(),mp3Info.getAlbumId(),true,true);
        //System.out.println("NetMusicListAdapter.java #8 : albumBitmapItem = " + albumBitmapItem.getConfig());

        //�ı䲥�Ž���ר������ͼƬ
        //vh.imageView1_ablum.setImageBitmap(albumBitmapItem);
        //vh.imageView1_ablum.setImageResource(R.mipmap.music);

        return convertView;
    }

    /**
     * ����һ���ڲ���
     * ������Ӧ�Ŀؼ�����
     */
    static class ViewHolder{
        //���пؼ���������
        TextView textView1_title;//����
        TextView textView2_singer;//����
        //TextView textView3_time;//ʱ��
        //ImageView imageView1_ablum;//ר������ͼƬ
    }

}
