package com.swen.testassistant.testInfo;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/*
COPYRIGHT NOTICE:
.
(c) 2018 Cogosense Technology Inc. All rights reserved.
.
This file is the property of Cogosense Technology Inc.
.
This file contains proprietary information of Cogosense Technology Inc., its
affiliates and subsidiaries (COGOSENSE). The contents are company confidential
and any disclosure to persons other than the officers, employees, agents
or subcontractors of the owner or licensee of these materials, without the
prior written consent of COGOSENSE, is strictly prohibited. No part of this
file may be reproduced, stored in a retrieval system, or transmitted in
any form or by any means, including photocopying, electronic, mechanical,
recording or otherwise, without prior written permission of the copyright
holder. Product names mentioned in this file are trademarks or registered
trademarks of their respective companies and are hereby acknowledged.
.
Cogosense Technology Inc. ,
315 - 255 Newport Drive
Port Moody, BC,
Canada, V3H 5H1.
.
Phone: +1604-899-1008
*/
public class TaTestInfo implements java.io.Serializable{
    private final String mFilename = "TestInfo";
    private final int MAX_MAP_SIZE = 10;
    private static TaTestInfo mSingleton;
    private final static String mTag = "TA_TEST_INFO";
    private File mFile = null;
    private static Context mContext;
    private Map mMap;
    public final static int ADD_KEY_SUCCESS = 100;
    public final static int ADD_KEY_EXIST = 101;
    public final static int ADD_KEY_OVERFLOW = 102;
    public final static int ADD_KEY_FILE_STREAM_ERROR = 103;
    private TaTestInfo(){}
    public static TaTestInfo getInstance(Context context){
        if(mSingleton == null)
            mSingleton = new TaTestInfo();

        mContext = context;

        return mSingleton;
    }

    public class TaTestInfoDetail implements java.io.Serializable{
        private String mTestName;
        private String mTester;
        private String mEmail;
        private String mTestPlan;
        TaTestInfoDetail(String testName, String tester, String email, String testPlan){
            mTestName = testName;
            mTester = tester;
            mEmail = email;
            mTestPlan = testPlan;
        }
        public String getTestName(){
            return mTestName;
        }

        public String getTester(){
            return mTester;
        }

        public String getEmail(){
            return mEmail;
        }

        public String getTestPlan(){
            return mTestPlan;
        }
    }

    public void open(){
        /*
         * open file or create file if it does not exist.
         */
        if(mFile == null)
            mFile = new File(mContext.getFilesDir(), mFilename);

        try {
            FileInputStream inputStream = new FileInputStream(mFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            mMap = (HashMap)objectInputStream.readObject();
            objectInputStream.close();
            inputStream.close();
        }catch(Exception e) {
            Log.e(mTag, "Cannot create input stream, exception: " + e.toString());
        }

        if(mMap == null)
            mMap = new HashMap();

    }

    public int add(String testName, String tester, String email, String testPlan){
        Log.d(mTag, "Adding test: " + testName);
        if(mMap.get(testName) != null){
            Log.d(mTag, "testName: " + testName + "already exists");
            return ADD_KEY_EXIST;
        }

        if(mMap.size() == MAX_MAP_SIZE){
            Log.d(mTag, "Maximum number of Test Plan has been reached: " + MAX_MAP_SIZE + " ,cannot add this test: " + testName);
            return ADD_KEY_OVERFLOW;
        }

        TaTestInfoDetail ti = new TaTestInfoDetail(testName, tester, email, testPlan);
        mMap.put(testName, ti);
        try {
            FileOutputStream outputStream = new FileOutputStream(mFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(mMap);
            objectOutputStream.close();
            outputStream.close();
        }catch (Exception e) {
            Log.e(mTag, "Cannot create output stream, exception: " + e.toString());
            return ADD_KEY_FILE_STREAM_ERROR;
        }

        return ADD_KEY_SUCCESS;
    }

    public Map getMap() {
        return mMap;
    }

    public void remove(String testName){
        Log.d(mTag, "Removing test: " + testName);
        mMap.remove(testName);
        return;
    }

    public void close(){
        mFile = null;
        mMap = null;
    }
}
