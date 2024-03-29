package com.quicsolv.appointmentapp.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.quicsolv.appointmentapp.MyApplication;
import com.quicsolv.appointmentapp.R;
import com.quicsolv.appointmentapp.activities.DashboardActivity;
import com.quicsolv.appointmentapp.retrofit.RetrofitClient;
import com.quicsolv.appointmentapp.retrofit.RetrofitConstants;
import com.quicsolv.appointmentapp.retrofit.models.interfaces.GetPatientProfiletInterface;
import com.quicsolv.appointmentapp.retrofit.models.interfaces.UpdatePatientProfileInterface;
import com.quicsolv.appointmentapp.retrofit.models.pojo.patientprofile.Data;
import com.quicsolv.appointmentapp.retrofit.models.pojo.patientprofile.GetPatientProfileResponse;
import com.quicsolv.appointmentapp.retrofit.models.pojo.patientprofile.UpdatePatientProfileResponse;
import com.quicsolv.appointmentapp.utils.Connectivity;
import com.quicsolv.appointmentapp.utils.Constants;
import com.quicsolv.appointmentapp.utils.Prefs;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    private Context mContext;
    private TextView mTxt_uname;
    private EditText mTxt_name;
    private EditText mTxt_mob_no;
    private EditText mTxt_email;
    private EditText mTxt_dob;
    private TextView btnEdit;
    private Button btnUpdateProfile;
    private Button btnMyReports;
    private GetPatientProfiletInterface getPatientProfiletInterface;
    private UpdatePatientProfileInterface updatePatientProfileInterface;
    Calendar myCalendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener selectedStartDate;
    private ProgressBar progressBar;
    private RadioGroup rgGender;
    private RadioButton rbGenderType;
    private RadioButton rbMale, rbFemale;
    private LinearLayout layoutGender;
    private TextView txtGender;
    private CircleImageView profileImage;
    private static final int RESULT_SELECT_IMAGE = 1;
    public String SERVER = "http://qtademo.com/bookingapp/api/upload_pp",
            timestamp;
    public String imagePath;
    private Dialog dialog;


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ((DashboardActivity) getActivity()).setToolBarTitle(getString(R.string.header_my_profile));

        mContext = getActivity();
        getPatientProfiletInterface = RetrofitClient.getClient(RetrofitConstants.BASE_URL).create(GetPatientProfiletInterface.class);
        updatePatientProfileInterface = RetrofitClient.getClient(RetrofitConstants.BASE_URL).create(UpdatePatientProfileInterface.class);


        getIds(view);

        progressBar.setVisibility(View.VISIBLE);
        layoutGender.setVisibility(View.GONE);
        txtGender.setVisibility(View.VISIBLE);

        fetchPatientProfileData();

        return view;
    }

    private void fetchPatientProfileData() {
        getPatientProfiletInterface.getPatientDetails(Prefs.getSharedPreferenceString(mContext, Prefs.PREF_PID, "")).enqueue(new Callback<GetPatientProfileResponse>() {
            @Override
            public void onResponse(Call<GetPatientProfileResponse> call, Response<GetPatientProfileResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response != null && response.body() != null && response.body().getCode() == Constants.ERROR_CODE_200) {
                    setData(response.body().getBaseUrl(), response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<GetPatientProfileResponse> call, Throwable t) {

            }
        });
    }


    private void getIds(View view) {
        mTxt_uname = (TextView) view.findViewById(R.id.txt_uname);
        mTxt_name = (EditText) view.findViewById(R.id.txt_name);
        mTxt_mob_no = (EditText) view.findViewById(R.id.txt_mob_no);
        mTxt_email = (EditText) view.findViewById(R.id.txt_email);
        mTxt_dob = (EditText) view.findViewById(R.id.txt_dob);
        txtGender = (EditText) view.findViewById(R.id.txt_gender);
        layoutGender = (LinearLayout) view.findViewById(R.id.layout_gender);

        profileImage = (CircleImageView) view.findViewById(R.id.profile_image);
        profileImage.setOnClickListener(this);

        progressBar = (ProgressBar) view.findViewById(R.id.progress_profile);

        rgGender = (RadioGroup) view.findViewById(R.id.rg_gender);
        rbMale = (RadioButton) view.findViewById(R.id.rb_male);
        rbFemale = (RadioButton) view.findViewById(R.id.rb_female);


        mTxt_dob.setOnClickListener(this);


        mTxt_name.setFocusable(false);
        mTxt_name.setFocusableInTouchMode(false);
        mTxt_name.setClickable(false);

        mTxt_mob_no.setFocusable(false);
        mTxt_mob_no.setFocusableInTouchMode(false);
        mTxt_mob_no.setClickable(false);

        btnEdit = (TextView) view.findViewById(R.id.btn_edit);
        btnEdit.setOnClickListener(this);

        btnUpdateProfile = (Button) view.findViewById(R.id.btn_update_profile);
        btnUpdateProfile.setOnClickListener(this);

        btnMyReports = (Button) view.findViewById(R.id.btn_my_reports);
        btnMyReports.setOnClickListener(this);
        btnMyReports.setVisibility(View.VISIBLE);

        selectedStartDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDOBDateLabel();
            }
        };
    }

    private void setData(String baseUrl, Data data) {
        mTxt_uname.setText(data.getPName());
        mTxt_name.setText(data.getPName());
        mTxt_mob_no.setText(data.getPPhone());
        mTxt_email.setText(data.getPEmail());

        layoutGender.setVisibility(View.GONE);
        txtGender.setVisibility(View.VISIBLE);
        btnMyReports.setVisibility(View.VISIBLE);

        if (data.getPPpPath() != null) {
            Picasso.with(mContext).load(baseUrl + data.getPPpPath()).error(R.drawable.profile).into(profileImage);
            ((DashboardActivity) getActivity()).setProfileImage(baseUrl + data.getPPpPath().toString());
        }

        if (data.getGender() != null) {
            String genderId = data.getGender();
            if (genderId.equals("1")) {
                rgGender.check(R.id.rb_male);
                txtGender.setText("Male");
            } else {
                rgGender.check(R.id.rb_female);
                txtGender.setText("Female");
            }
        }

        if (data.getDob() != null) {
            String date = data.getDob();
            SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat output = new SimpleDateFormat("MM-dd-yyyy");
            try {
                Date oneWayTripDate = input.parse(date);                 // parse input
                mTxt_dob.setText(output.format(oneWayTripDate));// format output


                String[] str = mTxt_dob.getText().toString().split("-");
                myCalendar.set(Integer.parseInt(str[2]), (Integer.parseInt(str[0]) - 1), Integer.parseInt(str[1]));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        ((DashboardActivity) getActivity()).setNavDrawerUserName(data.getPName().toString());

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update_profile:
                if (mTxt_name.getText().toString().trim().equals("")) {
                    mTxt_name.setError("Enter full name");
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    updateProfileData();
                }
                break;
            case R.id.btn_my_reports:
                showMyReportsFragment();
                break;
            case R.id.txt_dob:
                if (btnEdit.getText().toString().equalsIgnoreCase("Cancel")) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, R.style.DialogTheme, selectedStartDate, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));
                    myCalendar.set(myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                    datePickerDialog.show();
                }
                break;
            case R.id.btn_edit:
                if (btnEdit.getText().toString().equalsIgnoreCase("Edit")) {
                    btnUpdateProfile.setVisibility(View.VISIBLE);
                    btnMyReports.setVisibility(View.GONE);
                    btnEdit.setText("Cancel");

                    layoutGender.setVisibility(View.VISIBLE);
                    txtGender.setVisibility(View.GONE);

                    mTxt_name.setFocusable(true);
                    mTxt_name.setFocusableInTouchMode(true);
                    mTxt_name.setClickable(true);

                    mTxt_mob_no.setFocusable(true);
                    mTxt_mob_no.setFocusableInTouchMode(true);
                    mTxt_mob_no.setClickable(true);

                } else {
                    btnUpdateProfile.setVisibility(View.GONE);
                    btnMyReports.setVisibility(View.VISIBLE);
                    btnEdit.setText("Edit");

                    layoutGender.setVisibility(View.GONE);
                    txtGender.setVisibility(View.VISIBLE);

                    mTxt_name.setFocusable(false);
                    mTxt_name.setFocusableInTouchMode(false);
                    mTxt_name.setClickable(false);

                    mTxt_mob_no.setFocusable(false);
                    mTxt_mob_no.setFocusableInTouchMode(false);
                    mTxt_mob_no.setClickable(false);
                }
                break;
            case R.id.profile_image:
                Intent gallaryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallaryIntent, RESULT_SELECT_IMAGE);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void showMyReportsFragment() {
        Fragment fragment = null;
        Class fragmentClass = null;
        Prefs.setSharedPreferenceBoolean(mContext, Prefs.PREF_IS_FROM_REQUEST_APT, false);
        if (Connectivity.isNetworkConnected(MyApplication.getInstance())) {
            fragmentClass = ReportsFragment.class;
        } else {
            fragmentClass = NoInternetConnectionFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment, "").commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_SELECT_IMAGE && resultCode == RESULT_OK && data != null) {
            //set the selected image to image variable
            Uri image = data.getData();
            profileImage.setImageURI(image);
            imagePath = getRealPathFromURI(image);

            //get the current timeStamp and strore that in the time Variable
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp = tsLong.toString();

            String pid = Prefs.getSharedPreferenceString(mContext, Prefs.PREF_PID, "");
            Map<String, String> params = new HashMap<String, String>(2);
            params.put("pid", pid);

//            progressBar.setVisibility(View.VISIBLE);
            dialog = new Dialog(mContext);
            dialog.setContentView(R.layout.dialog_with_progress_text);
            dialog.setCancelable(false);
            TextView text = (TextView) dialog.findViewById(R.id.text);
            text.setText("Uploading profile photo...");
            dialog.show();
            String result = multipartRequest(SERVER, params, imagePath, "file", "image/*");
            Log.d("", result);
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getActivity(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }


    public String multipartRequest(final String urlTo, final Map<String, String> parmas, final String filepath, final String filefield, final String fileMimeType) {
        final HttpURLConnection[] connection = {null};
        final DataOutputStream[] outputStream = {null};
        final InputStream[] inputStream = {null};

        final String twoHyphens = "--";
        final String boundary = "*****" + Long.toString(System.currentTimeMillis()) + "*****";
        final String lineEnd = "\r\n";

        final String[] result = {""};

        final int[] bytesRead = new int[1];
        final int[] bytesAvailable = new int[1];
        final int[] bufferSize = new int[1];
        final byte[][] buffer = new byte[1][1];
        final int maxBufferSize = 1 * 1024 * 1024;

        final String[] q = filepath.split("/");
        final int idx = q.length - 1;

        try {

            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        File file = new File(filepath);
                        FileInputStream fileInputStream = new FileInputStream(file);

                        URL url = new URL(urlTo);
                        connection[0] = (HttpURLConnection) url.openConnection();

                        connection[0].setDoInput(true);
                        connection[0].setDoOutput(true);
                        connection[0].setUseCaches(false);

                        connection[0].setRequestMethod("POST");
                        connection[0].setRequestProperty("Connection", "Keep-Alive");
                        connection[0].setRequestProperty("User-Agent", "Android Multipart HTTP Client 1.0");
                        connection[0].setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

                        outputStream[0] = new DataOutputStream(connection[0].getOutputStream());
                        outputStream[0].writeBytes(twoHyphens + boundary + lineEnd);
                        outputStream[0].writeBytes("Content-Disposition: form-data; name=\"" + filefield + "\"; filename=\"" + q[idx] + "\"" + lineEnd);
                        outputStream[0].writeBytes("Content-Type: " + fileMimeType + lineEnd);
                        outputStream[0].writeBytes("Content-Transfer-Encoding: binary" + lineEnd);

                        outputStream[0].writeBytes(lineEnd);

                        bytesAvailable[0] = fileInputStream.available();
                        bufferSize[0] = Math.min(bytesAvailable[0], maxBufferSize);
                        buffer[0] = new byte[bufferSize[0]];

                        bytesRead[0] = fileInputStream.read(buffer[0], 0, bufferSize[0]);
                        while (bytesRead[0] > 0) {
                            outputStream[0].write(buffer[0], 0, bufferSize[0]);
                            bytesAvailable[0] = fileInputStream.available();
                            bufferSize[0] = Math.min(bytesAvailable[0], maxBufferSize);
                            bytesRead[0] = fileInputStream.read(buffer[0], 0, bufferSize[0]);
                        }

                        outputStream[0].writeBytes(lineEnd);

                        // Upload POST Data
                        Iterator<String> keys = parmas.keySet().iterator();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            String value = parmas.get(key);

                            outputStream[0].writeBytes(twoHyphens + boundary + lineEnd);
                            outputStream[0].writeBytes("Content-Disposition: form-data; name=\"" + key + "\"" + lineEnd);
                            outputStream[0].writeBytes("Content-Type: text/plain" + lineEnd);
                            outputStream[0].writeBytes(lineEnd);
                            outputStream[0].writeBytes(value);
                            outputStream[0].writeBytes(lineEnd);
                        }

                        outputStream[0].writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);


                        if (200 != connection[0].getResponseCode()) {
                            throw new Exception("Failed to upload code:" + connection[0].getResponseCode() + " " + connection[0].getResponseMessage());
                        }

                        inputStream[0] = connection[0].getInputStream();

                        result[0] = convertStreamToString(inputStream[0]);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    progressBar.setVisibility(View.GONE);
                                    dialog.dismiss();
                                    JSONObject jsonObj = new JSONObject(result[0]);
                                    if (jsonObj.has("errormessage")) {
                                        String errorMsg = jsonObj.getString("errormessage");
                                        errorMsg = errorMsg.replace("<p>", "").replace("</p>", "");
                                        dialogSuccessError(errorMsg);
                                    } else if (jsonObj.has("code")) {
                                        String code = jsonObj.getString("code");
                                        if (code.equals("200")) {
                                            String message = jsonObj.getString("message");
                                            dialogSuccessError("Profile picture has been successfully updated.");
                                        }
                                    }
                                } catch (Exception e) {
                                    dialog.dismiss();
                                }
                            }
                        });

                        fileInputStream.close();
                        inputStream[0].close();
                        outputStream[0].flush();
                        outputStream[0].close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();


            return result[0];
        } catch (Exception e) {
            Log.d("", "");
        }
        return "";
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private void updateProfileData() {

        progressBar.setVisibility(View.GONE);

        String strCurrentDate = mTxt_dob.getText().toString();
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        Date newDate = null;
        String dob = "";
        try {
            newDate = format.parse(strCurrentDate);
            format = new SimpleDateFormat("yyyy-MM-dd");
            dob = format.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        int selectedGender = 1;
        int selectedId = rgGender.getCheckedRadioButtonId();
        rbGenderType = (RadioButton) getActivity().findViewById(selectedId);

        if (rbGenderType.getText().toString().equalsIgnoreCase("male")) {
            selectedGender = 1;
        } else if (rbGenderType.getText().toString().equalsIgnoreCase("female")) {
            selectedGender = 2;
        }


        updatePatientProfileInterface.updatePatientProfile(Prefs.getSharedPreferenceString(mContext, Prefs.PREF_PID, ""),
                mTxt_name.getText().toString(), selectedGender + "", dob, mTxt_mob_no.getText().toString()).enqueue(new Callback<UpdatePatientProfileResponse>() {
            @Override
            public void onResponse(Call<UpdatePatientProfileResponse> call, Response<UpdatePatientProfileResponse> response) {
                if (response != null && response.body() != null && response.body().getCode() == Constants.ERROR_CODE_200) {
                    Toast.makeText(mContext, "Profile has been successfully updated.", Toast.LENGTH_SHORT).show();
                    btnUpdateProfile.setVisibility(View.GONE);
                    btnEdit.setText("Edit");
                    progressBar.setVisibility(View.VISIBLE);
                    fetchPatientProfileData();

                    mTxt_name.setFocusable(false);
                    mTxt_name.setFocusableInTouchMode(false);
                    mTxt_name.setClickable(false);

                    mTxt_mob_no.setFocusable(false);
                    mTxt_mob_no.setFocusableInTouchMode(false);
                    mTxt_mob_no.setClickable(false);
                }
            }

            @Override
            public void onFailure(Call<UpdatePatientProfileResponse> call, Throwable t) {

            }
        });
    }

    private void updateDOBDateLabel() {
        String myFormat = "MM-dd-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mTxt_dob.setText(sdf.format(myCalendar.getTime()));
        mTxt_dob.setError(null);
    }


    private void dialogSuccessError(String message) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Profile");
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                fetchPatientProfileData();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
}
