package com.example.dennislam.myapplication.dao.criteria;

import com.example.dennislam.myapplication.xml.IndustryXML;
import com.example.dennislam.myapplication.xml.ItemsInfoBaseXML;
import com.example.dennislam.myapplication.xml.JobCatXML;
import com.thoughtworks.xstream.XStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by dennislam on 10/1/2017.
 */

public class IndustryDao {

    static final String URL = "http://192.168.232.66:8009/API_CT2_SALARY/GET_ALL_INDUSTRY.aspx";
    private List<IndustryXML.IndustryItem> industryItemList;

    private List<ItemsInfoBaseXML> getItemsInfo;
    int statusCode;
    public int getStatusCode() {
        return statusCode;
    }

    public List<IndustryXML.IndustryItem> getIndustryItemDao(){

        String xml;

        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(URL);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            xml = EntityUtils.toString(httpEntity, HTTP.UTF_8);

            String content = xml;
            byte[] bytes = content.getBytes();
            String contentNoBom = new String(bytes, 3, bytes.length - 3);

            XStream xStream = new XStream();
            xStream.processAnnotations(IndustryXML.class);

            IndustryXML rss = (IndustryXML) xStream.fromXML(contentNoBom);

            getItemsInfo = rss.getItemsInfo();
            statusCode = getItemsInfo.get(0).getStatus_code();

            if(statusCode == 0) {
                industryItemList = rss.getItems().getItem();
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return industryItemList;
    }
}
