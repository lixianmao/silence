package listener_adapter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class LrcRead {

	private List<LyricContent> LyricList;
	private LyricContent mLyricContent;

	public LrcRead() {
		mLyricContent = new LyricContent();
		LyricList = new ArrayList<LyricContent>();
	}

	public StringBuffer getLRC(String song, String singer) {
		StringBuffer sb = new StringBuffer();

		try {
			singer = URLEncoder.encode(singer, "utf-8");
			song = URLEncoder.encode(song, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String strURL = "http://box.zhangmen.baidu.com/x?op=12&count=1&title="
				+ song + "$$" + singer + "$$$$";
		Log.d("test strURL", strURL);
		HttpClient client = new DefaultHttpClient();
		HttpGet getXML = new HttpGet(strURL);
		HttpResponse response = null;
		try {
			response = client.execute(getXML);
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		BufferedReader br = null;
		String s = "";
		try {
			br = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
		} catch (IllegalStateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (br == null) {
			Log.v("br", "no xml acquired");
		} else {
			try {
				while ((s = br.readLine()) != null) {
					sb.append(s + "/r/n");
				}
				br.close(); // 关闭缓冲数据流，同时释放连接
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return sb;

	}

	public int getLRCId(String song, String singer) {
		StringBuffer sb = null;
		int begin = 0, end = 0, number = 0; // number = 0 表示暂无歌词
		String strId = "";

		sb = getLRC(song, singer);
		begin = sb.indexOf("<lrcid>");
		if (begin != -1) {
			end = sb.indexOf("</lrcid>", begin);
			strId = sb.substring(begin + 7, end);
			number = Integer.parseInt(strId);
		}
		return number;
	}

	public BufferedReader getLRCContent(String song, String singer) {
		String lrcUrl = "";
		int id = 0;
		BufferedReader br = null;

		id = getLRCId(song, singer);
		if (id == 0) {
			Log.e("id = 0", "无歌词");
			return null;
		}

		lrcUrl = "http://box.zhangmen.baidu.com/bdlrc/" + id / 100 + "/" + id
				+ ".lrc";
		HttpClient client = new DefaultHttpClient();
		HttpGet getLRCContent = new HttpGet(lrcUrl);
		HttpResponse response = null;
		try {
			response = client.execute(getLRCContent);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			br = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent(), "GB2312"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return br;
	}

	public void read(String song, String singer) throws FileNotFoundException,
			IOException {

		String Lrc_data = "";
		BufferedReader br = getLRCContent(song, singer);
		if (br == null) {
			System.out.print("stream is null");
			return;
		}

		while ((Lrc_data = br.readLine()) != null) {
			
			Lrc_data = Lrc_data.replace("[", "");
			Lrc_data = Lrc_data.replace("]", "@");
			String splitLrc_data[] = Lrc_data.split("@"); // 将歌词句子拆分成时间和歌词两部分

			if (splitLrc_data.length > 1) {
				mLyricContent.setLyric(splitLrc_data[1]);
				int LyricTime = TimeStr(splitLrc_data[0]);
				mLyricContent.setLyricTime(LyricTime);
				LyricList.add(mLyricContent);
				mLyricContent = new LyricContent();
			}
		}

		br.close();
	}

	public int TimeStr(String timeStr) {

		timeStr = timeStr.replace(":", ".");
		timeStr = timeStr.replace(".", "@");
		String timeData[] = timeStr.split("@");

		int minute = Integer.parseInt(timeData[0]); // 分
		int second = Integer.parseInt(timeData[1]); // 秒
		int millisecond = Integer.parseInt(timeData[2]); // 毫秒
		int currentTime = (minute * 60 + second) * 1000 + millisecond * 10;

		return currentTime;
	}

	public List<LyricContent> getLyricContent() {

		return LyricList;
	}
}
