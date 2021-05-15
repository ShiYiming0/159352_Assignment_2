/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2_test_19023253;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
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
public class StartTest {
    
    CloseableHttpClient client;
    CloseableHttpResponse response;
    HttpPost httpPost;
    
    public StartTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
    }
    
    @After
    public void tearDown() throws Exception {
        client.close();
        response.close();
    }

    // test start game
    @Test
    public void testStart() throws Exception{
        client = HttpClients.createDefault();
        httpPost = new HttpPost("http://localhost:8080/jack/start");
        response = client.execute(httpPost);
        int status = response.getStatusLine().getStatusCode();
        
        Header[] headerArray = response.getAllHeaders();
        for(Header header : headerArray) {
            System.out.println("--Header-----------------------------------------");
            System.out.println("----Key: " + header.getName());
            System.out.println("----RawValue: " + header.getValue());
        }
        
        assertEquals(status, 302);
    }
}
