package com.swen.testassistant;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.swen.testassistant.testInfo.TaTestInfo;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TaTestInfoFragment.OnTestInfoFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TaTestInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaTestInfoFragment extends Fragment{

    private OnTestInfoFragmentInteractionListener mListener;
    private EditText mTestNameText;
    private EditText mTesterText;
    private EditText mEmailText;
    private Spinner mTestPlanSpinner;
    private Button mProceedTestButton;
    private Button mManageTestPlanButton;
    private String mTestPlan;
    private TaTestInfo mTestInfoInstance;

    public TaTestInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TaTestInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaTestInfoFragment newInstance() {
        TaTestInfoFragment fragment = new TaTestInfoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TaMainActivity.mTag, "TaTestInfoFragment:onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TaMainActivity.mTag, "TaTestInfoFragment:onCreateView");
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ta_test_info, container, false);

        mTestNameText = v.findViewById(R.id.testInfoTestName);
        mTesterText = v.findViewById(R.id.testInfoTester);
        mEmailText = v.findViewById(R.id.testInfoEmail);
        mTestPlanSpinner = v.findViewById(R.id.testInfoTestPlanList);
        mProceedTestButton = v.findViewById(R.id.testInfoProceedTest);
        mManageTestPlanButton = v.findViewById(R.id.testInfoManageTestPlan);

        /*
         * Spinner - drop down list for the test plan.
         */
        String[] testPlan = {"FleetSafer GPS Resume Zone", "FleetSafer Beacon Resume Zone"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, testPlan);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mTestPlanSpinner.setAdapter(adapter);
        mTestPlanSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mTestPlan = (String)parent.getItemAtPosition(position);
                if(mTestPlan instanceof String){
                    Log.d(TaMainActivity.mTag, "TaTestInfoFragment:onItemSelected: selected item is a string: " + mTestPlan);
                }else{
                    Log.d(TaMainActivity.mTag, "TaTestInfoFragment:onItemSelected: selected item is NOT a string: " + mTestPlan);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(mTestInfoInstance == null) {
            mTestInfoInstance = TaTestInfo.getInstance(getActivity());
            mTestInfoInstance.open();
        }

        /*
         * Button to proceed the test.
         */
        mProceedTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 * Check if user has entered test information.
                 */
                String testName = mTestNameText.getText().toString();
                String tester = mTesterText.getText().toString();
                String email = mEmailText.getText().toString();
                CharSequence message = null;

                if(testName.matches("")){
                    message = "Missing required field Test Name, enter Test Name to proceed with the test";
                }else if(tester.matches("")){
                    message = "Missing required field Tester, enter Tester to proceed with the test";
                }else if(email.matches("")){
                    message = "Missing required field Email, enter Email to proceed with the test";
                }else {
                    /*
                     * Check for information integrity.
                     */
                }

                if(message != null){
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                    return;
                }

                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle(getResources().getString(R.string.alert_title_add_test_plan));
                int status = mTestInfoInstance.add(testName, tester, email, mTestPlan);
                String alertMessage = "";
                switch(status){
                    case TaTestInfo.ADD_KEY_EXIST:
                        alertMessage = getResources().getString(R.string.alert_message_test_plan_key_exist);
                        break;
                    case TaTestInfo.ADD_KEY_OVERFLOW:
                        alertMessage = getResources().getString(R.string.alert_message_test_plan_overflow);
                        break;
                    case TaTestInfo.ADD_KEY_FILE_STREAM_ERROR:
                        alertMessage = getResources().getString(R.string.alert_message_test_plan_file_stream_error);
                        break;
                    default:
                        break;
                }
                alertDialog.setMessage(alertMessage);
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                if(!alertMessage.isEmpty()){
                    alertDialog.show();
                }
            }
        });

        /*
         * Button to manage test plan.
         */
        mManageTestPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onTestInfoManageTestPlanInteraction();
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTestInfoFragmentInteractionListener) {
            mListener = (OnTestInfoFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface OnTestInfoFragmentInteractionListener {
        void onTestInfoManageTestPlanInteraction();
    }
}
