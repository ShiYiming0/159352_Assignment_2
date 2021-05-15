/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2_test_19023253;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
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
 * @author Yiming Shi
 */
public class StandTest {
    
    CloseableHttpClient client;
    CloseableHttpResponse response;
    HttpPost httpPost;
    
    public StandTest() {
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

    // normal stand
    @Test
    public void testMain() throws Exception {
        httpPost = new HttpPost("http://localhost:8080/jack/move/stand");
        response = client.execute(httpPost);
        int status = response.getStatusLine().getStatusCode();
        
        Header[] headerArray = response.getAllHeaders();
        for(Header header : headerArray) {
            System.out.println("--Header-----------------------------------------");
            System.out.println("----Key: " + header.getName());
            System.out.println("----RawValue: " + header.getValue());
        }
        
        assertEquals(200, status);
    }
    
    // stand when no active game.
    @Test
    public void testNoActiveGame1() throws Exception {
        client = HttpClients.createDefault();
        httpPost = new HttpPost("http://localhost:8080/jack/move/stand");
        response = client.execute(httpPost);
        int status = response.getStatusLine().getStatusCode();
        
        HttpEntity entity = response.getEntity();
        String string = EntityUtils.toString(entity,"UTF-8");
        System.out.println(string);
        
        assertEquals(404, status);
    }
    
    // stand when game is over.
    @Test
    public void testNoActiveGame2() throws Exception {
        httpPost = new HttpPost("http://localhost:8080/jack/move/stand");
        client.execute(httpPost);
        httpPost = new HttpPost("http://localhost:8080/jack/won");
        client.execute(httpPost);
        httpPost = new HttpPost("http://localhost:8080/jack/move/stand");
        response = client.execute(httpPost);
        int status = response.getStatusLine().getStatusCode();
        
        HttpEntity entity = response.getEntity();
        String string = EntityUtils.toString(entity,"UTF-8");
        System.out.println(string);
        
        assertEquals(404, status);
    }
    
}
