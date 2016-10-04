package com.devqt.kubg.info.util;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class RSSHandler extends DefaultHandler {


	private Article currentArticle = new Article();
	private List<Article> articleList = new ArrayList<Article>();


	private int articlesAdded = 0;


	private static final int ARTICLES_LIMIT = 15;
	

	StringBuffer chars = new StringBuffer();

	

	public void startElement(String uri, String localName, String qName, Attributes atts) {
		chars = new StringBuffer();
	}




	public void endElement(String uri, String localName, String qName) throws SAXException {

		if (localName.equalsIgnoreCase("title"))
		{
			Log.d("LOGGING RSS XML", "Setting article title: " + chars.toString());
			currentArticle.setTitle(chars.toString());

		}
		else if (localName.equalsIgnoreCase("description"))
		{
			Log.d("LOGGING RSS XML", "Setting article description: " + chars.toString());
			currentArticle.setDescription(chars.toString());
		}
		else if (localName.equalsIgnoreCase("pubDate"))
		{
			Log.d("LOGGING RSS XML", "Setting article published date: " + chars.toString());
			currentArticle.setPubDate(chars.toString());
		}
		else if (localName.equalsIgnoreCase("encoded"))
		{
			Log.d("LOGGING RSS XML", "Setting article content: " + chars.toString());
			currentArticle.setEncodedContent(chars.toString());
		}
		else if (localName.equalsIgnoreCase("item"))
		{

		}
		else if (localName.equalsIgnoreCase("link"))
		{
			try {
				Log.d("LOGGING RSS XML", "Setting article link url: " + chars.toString());
				currentArticle.setUrl(new URL(chars.toString()));
			} catch (MalformedURLException e) {
				Log.e("RSA Error", e.getMessage());
			}

		}





		if (localName.equalsIgnoreCase("item")) {

			articleList.add(currentArticle);
			
			currentArticle = new Article();


			articlesAdded++;
			if (articlesAdded >= ARTICLES_LIMIT)
			{
				throw new SAXException();
			}
		}
	}
	
	




	public void characters(char ch[], int start, int length) {
		chars.append(new String(ch, start, length));
	}






	public List<Article> getLatestArticles(String feedUrl) {
		URL url = null;
		try {

			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();

			url = new URL(feedUrl);
			
			xr.setContentHandler(this);
			xr.parse(new InputSource(url.openStream()));


		} catch (IOException e) {
			Log.e("RSS Handler IO", e.getMessage() + " >> " + e.toString());
		} catch (SAXException e) {
			Log.e("RSS Handler SAX", e.toString());
		} catch (ParserConfigurationException e) {
			Log.e("RSS Handler Parser", e.toString());
		}
		
		return articleList;
	}

}
