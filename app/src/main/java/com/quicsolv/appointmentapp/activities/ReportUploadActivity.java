package com.quicsolv.appointmentapp.activities;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.quicsolv.appointmentapp.R;
import com.quicsolv.appointmentapp.retrofit.RetrofitClient;
import com.quicsolv.appointmentapp.retrofit.RetrofitConstants;
import com.quicsolv.appointmentapp.retrofit.models.interfaces.GetReportTypesListInterface;
import com.quicsolv.appointmentapp.retrofit.models.interfaces.UploadReportInterface;
import com.quicsolv.appointmentapp.retrofit.models.pojo.reporttype.List;
import com.quicsolv.appointmentapp.retrofit.models.pojo.reporttype.ReportTypeListResponse;
import com.quicsolv.appointmentapp.utils.Prefs;

import org.json.JSONException;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportUploadActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private Button btnChooseDocs, btnUploadDocs;
    private Spinner spinnerReportType;
    public static final int PICK_IMAGE = 100;
    private UploadReportInterface uploadReportInterface;
    private static final int RESULT_SELECT_IMAGE = 1;
    public ImageView imageView;
    public String SERVER = "http://qtademo.com/bookingapp/api/upload_report",
            timestamp;
    public String imagePath;

    private static final String TAG = MainActivity.class.getSimpleName();
    private GetReportTypesListInterface getReportTypesListInterface;
    private String reportTypeId;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_upload);

        mContext = ReportUploadActivity.this;

        uploadReportInterface = RetrofitClient.getClient(RetrofitConstants.BASE_URL).create(UploadReportInterface.class);
        getReportTypesListInterface = RetrofitClient.getClient(RetrofitConstants.BASE_URL).create(GetReportTypesListInterface.class);


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        getIds();
        fetchReportTypeList();
    }


    private void getIds() {
        spinnerReportType = (Spinner) findViewById(R.id.spinner_report_type);

        imageView = (ImageView) findViewById(R.id.imageView);

        progressBar = (ProgressBar) findViewById(R.id.progress_upload_docs);

        btnChooseDocs = (Button) findViewById(R.id.btn_choose_docs);
        btnUploadDocs = (Button) findViewById(R.id.btn_upload_docs);

        btnChooseDocs.setOnClickListener(this);
        btnUploadDocs.setOnClickListener(this);
    }


    private void fetchReportTypeList() {
        getReportTypesListInterface.getReportTypesList().enqueue(new Callback<ReportTypeListResponse>() {
            @Override
            public void onResponse(Call<ReportTypeListResponse> call, Response<ReportTypeListResponse> response) {
                if (response.body() != null) {
                    final ArrayList<List> reportTypeList = new ArrayList<>();
                    List rtList = null;
                    if (response.body().getList() != null && response.body().getList().size() > 0) {
                        for (int i = 0; i < response.body().getList().size(); i++) {
                            rtList = new List();
                            rtList.setRtId(response.body().getList().get(i).getRtId());
                            rtList.setRtName(response.body().getList().get(i).getRtName());
                            reportTypeList.add(rtList);
                        }
                    }

                    ArrayAdapter<List> adapter =
                            new ArrayAdapter<List>(mContext, R.layout.speciality_spinner_item, reportTypeList);
                    adapter.setDropDownViewResource(R.layout.speciality_spinner_item);

                    spinnerReportType.setAdapter(adapter);


                    spinnerReportType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                            // your code here
                            reportTypeId = reportTypeList.get(position).getRtId();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {
                            // your code here
                        }

                    });

                }
            }

            @Override
            public void onFailure(Call<ReportTypeListResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_choose_docs:
                selectImage();
                break;
            case R.id.btn_upload_docs:
                //setup params
                progressBar.setVisibility(View.VISIBLE);
                String pid = Prefs.getSharedPreferenceString(mContext, Prefs.PREF_PID, "");
                Map<String, String> params = new HashMap<String, String>(2);
                params.put("pid", pid);
                params.put("rt_id", reportTypeId);

                String result = multipartRequest(SERVER, params, imagePath, "file", "image/*");
//                try {
//                    JSONObject jsonObj = new JSONObject(result);
//                    String code = jsonObj.getString("code");
//                    progressBar.setVisibility(View.GONE);
//                    if (code.equals("200")) {
//                        String message = jsonObj.getString("message");
//                        Toast.makeText(mContext, ""+message, Toast.LENGTH_SHORT).show();
//                        finish();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                break;
        }
    }

    //function to select a image
    private void selectImage() {
        //open album to select image
        Intent gallaryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallaryIntent, RESULT_SELECT_IMAGE);
    }


    /*
 * This function is called when we pick some image from the album
 * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_SELECT_IMAGE && resultCode == RESULT_OK && data != null) {
            //set the selected image to image variable
            Uri image = data.getData();
            imageView.setImageURI(image);
            imagePath = getRealPathFromURI(image);

            //get the current timeStamp and strore that in the time Variable
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp = tsLong.toString();
        }
    }

//    private String hashMapToUrl(HashMap<String, String> params) throws UnsupportedEncodingException {
//        StringBuilder result = new StringBuilder();
//        boolean first = true;
//        for (Map.Entry<String, String> entry : params.entrySet()) {
//            if (first)
//                first = false;
//            else
//                result.append("&");
//
//            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
//            result.append("=");
//            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
//        }
//
//        return result.toString();
//    }
//
//
//    //async task to upload image
//    private class Upload extends AsyncTask<Void, Void, String> {
//        private Bitmap image;
//        private String name;
//
//        public Upload(Bitmap image, String name) {
//            this.image = image;
//            this.name = name;
//        }
//
//        @Override
//        protected String doInBackground(Void... params) {
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            //compress the image to jpg format
//            image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//            /*
//            * encode image to base64 so that it can be picked by saveImage.php file
//            * */
//            String encodeImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
//
//            //generate hashMap to store encodedImage and the name
//            HashMap<String, String> detail = new HashMap<>();
//            detail.put("file", name);
//            detail.put("image", encodeImage);
//            detail.put("pid", "3");
//            detail.put("rt_id", "2");
//
//            try {
//                //convert this HashMap to encodedUrl to send to php file
//                String dataToSend = hashMapToUrl(detail);
//                //make a Http request and send data to saveImage.php file
//                String response = Request.post(SERVER, dataToSend);
//
//                //return the response
//                return response;
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                Log.e(TAG, "ERROR  " + e);
//                return null;
//            }
//        }
//
//
//        @Override
//        protected void onPostExecute(String s) {
//            //show image uploaded
//            Toast.makeText(getApplicationContext(), "Image Uploaded", Toast.LENGTH_SHORT).show();
//        }
//    }

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

            progressBar.setVisibility(View.GONE);
            finish();

            return result[0];
        } catch (Exception e) {
            Log.d("", "");
        }
        return result[0];
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
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

}
