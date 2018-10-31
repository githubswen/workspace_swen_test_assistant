package com.swen.testassistant;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TaMasterFragment.OnMasterFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TaMasterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaMasterFragment extends Fragment {
    private static Button mStartNewTestButton;
    private static Button mResumeTestButton;
    private OnMasterFragmentInteractionListener mListener;

    public TaMasterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TaMasterFragment.
     */
    public static TaMasterFragment newInstance() {
        TaMasterFragment fragment = new TaMasterFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TaMainActivity.mTag, "TaMasterFragment:onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TaMainActivity.mTag, "TaMasterFragment:onCreateView");
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_ta_master, container, false);

        if (mStartNewTestButton == null) {
            mStartNewTestButton = v.findViewById(R.id.start_new_test_button);
            mStartNewTestButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onStartNewTestInteraction();
                    return;
                }
            });
        }

        if (mResumeTestButton == null) {
            mResumeTestButton = v.findViewById(R.id.resume_test_button);
            mResumeTestButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onResumeTestInteraction();
                    return;
                }
            });
        }

        return v;
    }

    @Override
    public void onAttach(Context context) {
        Log.d(TaMainActivity.mTag, "TaMasterFragment:onAttach");
        super.onAttach(context);
        if (context instanceof OnMasterFragmentInteractionListener) {
            mListener = (OnMasterFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMasterFragmentInteractionListener");
        }
    }

    @Override
    public void onStart() {
        Log.d(TaMainActivity.mTag, "TaMasterFragment:onStart");
        super.onStart();
    }

    @Override
    public void onDetach() {
        Log.d(TaMainActivity.mTag, "TaMasterFragment:onDetach");
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        Log.d(TaMainActivity.mTag, "TaMasterFragment:onDestroy");
        super.onDestroy();
        mStartNewTestButton = null;
        mResumeTestButton = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnMasterFragmentInteractionListener {
        void onStartNewTestInteraction();
        void onResumeTestInteraction();
    }
}