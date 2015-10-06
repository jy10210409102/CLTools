/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chenli.udp;

import com.chenli.dao.MessageDao;
import com.chenli.dao.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Administrator
 */
public class UdpReceiveTest {
    
    public UdpReceiveTest() {
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
    public void tearDown() {
    }

    /**
     * Test of Star method, of class UdpReceive.
     */
    @Test
    public void testStar() {
        System.out.println("Star");
        UdpReceive instance = new UdpReceive();
        instance.Star();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of receiveMsg method, of class UdpReceive.
     */
    @Test
    public void testReceiveMsg() throws Exception {
        System.out.println("receiveMsg");
        UdpReceive instance = new UdpReceive();
        MessageDao expResult = null;
        MessageDao result = instance.receiveMsg();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fType method, of class UdpReceive.
     */
    @Test
    public void testFType() throws Exception {
        System.out.println("fType");
        MessageDao msg = null;
        UdpReceive instance = new UdpReceive();
        instance.fType(msg);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of upline method, of class UdpReceive.
     */
    @Test
    public void testUpline() throws Exception {
        System.out.println("upline");
        MessageDao msg = null;
        UdpReceive instance = new UdpReceive();
        instance.upline(msg);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addUser method, of class UdpReceive.
     */
    @Test
    public void testAddUser() {
        System.out.println("addUser");
        MessageDao msg = null;
        UdpReceive instance = new UdpReceive();
        instance.addUser(msg);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of SendSelfOline method, of class UdpReceive.
     */
    @Test
    public void testSendSelfOline() throws Exception {
        System.out.println("SendSelfOline");
        MessageDao msg = null;
        UdpReceive instance = new UdpReceive();
        instance.SendSelfOline(msg);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of judgeUser method, of class UdpReceive.
     */
    @Test
    public void testJudgeUser() {
        System.out.println("judgeUser");
        MessageDao msg = null;
        UdpReceive instance = new UdpReceive();
        boolean expResult = false;
        boolean result = instance.judgeUser(msg);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of online method, of class UdpReceive.
     */
    @Test
    public void testOnline() {
        System.out.println("online");
        User user = null;
        UdpReceive instance = new UdpReceive();
        instance.online(user);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of flushListView method, of class UdpReceive.
     */
    @Test
    public void testFlushListView() {
        System.out.println("flushListView");
        UdpReceive instance = new UdpReceive();
        instance.flushListView();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of downline method, of class UdpReceive.
     */
    @Test
    public void testDownline() {
        System.out.println("downline");
        MessageDao msg = null;
        UdpReceive instance = new UdpReceive();
        instance.downline(msg);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of doMsg method, of class UdpReceive.
     */
    @Test
    public void testDoMsg() {
        System.out.println("doMsg");
        MessageDao msg = null;
        UdpReceive instance = new UdpReceive();
        instance.doMsg(msg);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of doFile method, of class UdpReceive.
     */
    @Test
    public void testDoFile() {
        System.out.println("doFile");
        MessageDao msg = null;
        UdpReceive instance = new UdpReceive();
        instance.doFile(msg);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sendFile method, of class UdpReceive.
     */
    @Test
    public void testSendFile() {
        System.out.println("sendFile");
        MessageDao msg = null;
        UdpReceive instance = new UdpReceive();
        instance.sendFile(msg);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of tiShi method, of class UdpReceive.
     */
    @Test
    public void testTiShi() {
        System.out.println("tiShi");
        String str = "";
        UdpReceive instance = new UdpReceive();
        instance.tiShi(str);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of doFileJu method, of class UdpReceive.
     */
    @Test
    public void testDoFileJu() {
        System.out.println("doFileJu");
        MessageDao msg = null;
        UdpReceive instance = new UdpReceive();
        instance.doFileJu(msg);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fileProgress method, of class UdpReceive.
     */
    @Test
    public void testFileProgress() {
        System.out.println("fileProgress");
        UdpReceive instance = new UdpReceive();
        instance.fileProgress();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of replyInterRequest method, of class UdpReceive.
     */
    @Test
    public void testReplyInterRequest() throws Exception {
        System.out.println("replyInterRequest");
        MessageDao msg = null;
        UdpReceive instance = new UdpReceive();
        instance.replyInterRequest(msg);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of msgBufferRemoveMsg method, of class UdpReceive.
     */
    @Test
    public void testMsgBufferRemoveMsg() {
        System.out.println("msgBufferRemoveMsg");
        MessageDao msg = null;
        UdpReceive instance = new UdpReceive();
        boolean expResult = false;
        boolean result = instance.msgBufferRemoveMsg(msg);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of chooseBaseInterServce method, of class UdpReceive.
     */
    @Test
    public void testChooseBaseInterServce() throws Exception {
        System.out.println("chooseBaseInterServce");
        MessageDao msg = null;
        UdpReceive instance = new UdpReceive();
        instance.chooseBaseInterServce(msg);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of rplyTrans method, of class UdpReceive.
     */
    @Test
    public void testRplyTrans() throws Exception {
        System.out.println("rplyTrans");
        MessageDao msg = null;
        UdpReceive instance = new UdpReceive();
        boolean expResult = false;
        boolean result = instance.rplyTrans(msg);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of transAndRply method, of class UdpReceive.
     */
    @Test
    public void testTransAndRply() throws Exception {
        System.out.println("transAndRply");
        MessageDao msg = null;
        UdpReceive instance = new UdpReceive();
        String expResult = "";
        String result = instance.transAndRply(msg);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}