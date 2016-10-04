package com.devqt.kubg.info.reader;

import android.text.Html;
import android.util.Log;

import com.devqt.kubg.info.util.Article;
import com.devqt.kubg.info.util.RSSHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class RssReader {
	
	private final static String BOLD_OPEN = "<B>";
	private final static String BOLD_CLOSE = "</B>";
	private final static String BREAK = "<BR>";
	private final static String ITALIC_OPEN = "<I>";
	private final static String ITALIC_CLOSE = "</I>";
	private final static String SMALL_OPEN = "<SMALL>";
	private final static String SMALL_CLOSE = "</SMALL>";


	public static List<JSONObject> getLatestRssFeed(){
		String feed = "http://feeds.feedburner.com/kubg/news?format=xml";
		
		
		RSSHandler rh = new RSSHandler();
		List<Article> articles =  rh.getLatestArticles(feed);
		Log.e("RSS ERROR", "Number of articles " + articles.size());
		return fillData(articles);
	}
	
	

	private static List<JSONObject> fillData(List<Article> articles) {

        List<JSONObject> items = new ArrayList<JSONObject>();
        for (Article article : articles) {
            JSONObject current = new JSONObject();
            try {
            	buildJsonObject(article, current);
			} catch (JSONException e) {
				Log.e("RSS ERROR", "Error creating JSON Object from RSS feed");
			}
			items.add(current);
        }
        
        return items;
	}



	private static void buildJsonObject(Article article, JSONObject current) throws JSONException {
		String title = article.getTitle();
		String description = article.getDescription();
		String date = article.getPubDate();
		String imgLink = article.getImgLink();
		
		StringBuffer sb = new StringBuffer();
		sb.append(BOLD_OPEN).append(title).append(BOLD_CLOSE);
		sb.append(BREAK);
		sb.append(description);
		sb.append(BREAK);
		sb.append(SMALL_OPEN).append(ITALIC_OPEN).append(date).append(ITALIC_CLOSE).append(SMALL_CLOSE);
		
		current.put("text", Html.fromHtml(sb.toString()));
		current.put("imageLink", imgLink);
	}
}
