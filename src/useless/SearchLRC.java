package useless;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class SearchLRC {
	
	private StringBuffer getLRC(String song, String singer) {
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
				br.close();			//关闭缓冲数据流，同时释放连接
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		return sb;
	}

	private int getLRCId(String song, String singer) {
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

	public String getLRCContent(String song, String singer) {
		String lrcContent = "", lrcUrl = "", s = "";
		int id = 0;
		BufferedReader br = null;

		id = getLRCId(song, singer);
		if (id == 0) {
			Log.e("id = 0", "无歌词");
			return "";
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
		
		if (br == null) {
			System.out.print("stream is null");
		} else {
			try {
				while((s = br.readLine()) != null) {
					lrcContent += s + "\r\n";
				}
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return lrcContent;
	}
}
