package com.example.dennislam.myapplication.dao;

import android.util.Log;

import com.example.dennislam.myapplication.activity.FeedbackActivity;
import com.example.dennislam.myapplication.xml.EducationLevelXML;
import com.example.dennislam.myapplication.xml.IndustryXML;
import com.example.dennislam.myapplication.xml.ItemsInfoBaseXML;
import com.example.dennislam.myapplication.xml.SendFeedbackXML;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.FloatConverter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dennislam on 16/1/2017.
 */

public class SendFeedbackDao {

    static final String URL = "http://192.168.232.66:8009/API_CT2_SALARY/SEND_FEEDBACK.aspx";
    private List<ItemsInfoBaseXML> feedbackItemList;

    public List<ItemsInfoBaseXML> getFeedbackItemDao(String udid, String name, String email, String comments, Float rating){

        String xml;

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(URL);

            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(5);
            nameValuePair.add(new BasicNameValuePair("udid", udid));
            nameValuePair.add(new BasicNameValuePair("name", name));
            nameValuePair.add(new BasicNameValuePair("emailAddress", email));
            nameValuePair.add(new BasicNameValuePair("comments", comments));
            nameValuePair.add(new BasicNameValuePair("rating", Float.toString(rating)));

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            xml = EntityUtils.toString(httpEntity, HTTP.UTF_8);

            String content = xml;
            byte[] bytes = content.getBytes();
            String contentNoBom = new String(bytes, 3, bytes.length - 3);

            XStream xStream = new XStream();
            xStream.processAnnotations(SendFeedbackXML.class);

            SendFeedbackXML rss = (SendFeedbackXML) xStream.fromXML(contentNoBom);

            feedbackItemList = rss.getItemsInfo();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return feedbackItemList;
    }

}
