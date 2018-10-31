package com.swen.testassistant;

import android.app.FragmentManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.swen.testassistant.testInfo.TaTestInfo;


public class TaMainActivity extends AppCompatActivity implements TaMasterFragment.OnMasterFragmentInteractionListener, TaTestInfoFragment.OnTestInfoFragmentInteractionListener,
        TaTestPlanListFragment.OnListFragmentInteractionListener {

    final static String mTag = "TEST_ASSISTANT";
    final static String mMasterFragmentTag = "MASTER FRAGMENT";
    final static String mTestPlanListFragmentTag = "TEST PLAN LIST FRAGMENT";
    final static String mTestInfoFragmentTag = "TEST INFO FRAGMENT";
    final static int mTestPlanListColumnNumber = 1;
    private TaMasterFragment mMasterFragment;
    private TaTestPlanListFragment mTestPlanListFragment;
    private TaTestInfoFragment mTestInfoFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(mTag, "TaMainActivity:onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ta_main);
        FragmentManager fm = getFragmentManager();
        mMasterFragment = TaMasterFragment.newInstance();
        fm.beginTransaction().add(R.id.fragment_container, mMasterFragment, mMasterFragmentTag).commit();

        if(savedInstanceState != null)
            Log.d(mTag, "savedInstanceState: " + savedInstanceState.getString("MyKey"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState (Bundle outState)
    {
        Log.d(mTag, "TaMainActivity:onSaveInstanceState");
        outState.putString("MyKey", "Just a Test");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        Log.d(mTag, "TaMainActivity:onResume");
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        Log.d(mTag, "TaMainActivity:onPause");
        super.onPause();
    }

    @Override
    protected void onStop()
    {
        Log.d(mTag, "TaMainActivity:onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        Log.d(mTag,"TaMainActivity:onDestroy");
        mMasterFragment = null;
        super.onDestroy();
    }

    @Override
    public void onStartNewTestInteraction() {
        if(mTestInfoFragment == null)
            mTestInfoFragment = TaTestInfoFragment.newInstance();

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().hide(mMasterFragment).add(R.id.fragment_container, mTestInfoFragment, mTestInfoFragmentTag).addToBackStack(mMasterFragmentTag).commit();

        return;
    }

    @Override
    public void onResumeTestInteraction() {
        return;
    }

    @Override
    public void onListFragmentInteraction(TaTestInfo.TaTestInfoDetail item) {
        return;
    }

    @Override
    public void onTestInfoManageTestPlanInteraction() {
        if(mTestPlanListFragment == null)
            mTestPlanListFragment = TaTestPlanListFragment.newInstance(1);

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().hide(mTestInfoFragment).add(R.id.fragment_container, mTestPlanListFragment, mTestPlanListFragmentTag).addToBackStack(mTestInfoFragmentTag).commit();
        return;
    }
}