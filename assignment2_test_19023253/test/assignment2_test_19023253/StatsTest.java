/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2_test_19023253;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author win10
 */
public class StatsTest {
    
    CloseableHttpClient client;
    CloseableHttpResponse response;
    HttpPost httpPost;
    HttpGet httpGet;
    
    public StatsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        client = HttpClients.createDefault();
        httpPost = new HttpPost("http://localhost:8080/jack/start");
        client.execute(httpPost);
    }
    
    @After
    public void tearDown() throws Exception {
        client.close();
        response.close();
    }

    // normal stats
    @Test
    public void testStats() throws Exception {
        httpGet = new HttpGet("http://localhost:8080/jack/stats");
        response = client.execute(httpGet);
        int status = response.getStatusLine().getStatusCode();
        
        assertEquals(200, status);
    }
    
    // stats when no active game
    @Test
    public void testNoActiveGame() throws Exception {
        client = HttpClients.createDefault();
        httpGet = new HttpGet("http://localhost:8080/jack/stats");
        response = client.execute(httpGet);
        int status = response.getStatusLine().getStatusCode();
        
        HttpEntity entity = response.getEntity();
        String string = EntityUtils.toString(entity,"UTF-8");
        System.out.println(string);
        
        assertEquals(404, status);
    }
    
}
