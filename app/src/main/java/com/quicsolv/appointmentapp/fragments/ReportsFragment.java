package com.quicsolv.appointmentapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.quicsolv.appointmentapp.R;
import com.quicsolv.appointmentapp.activities.DashboardActivity;
import com.quicsolv.appointmentapp.adapters.UploadedFilesListAdapter;
import com.quicsolv.appointmentapp.retrofit.RetrofitClient;
import com.quicsolv.appointmentapp.retrofit.RetrofitConstants;
import com.quicsolv.appointmentapp.retrofit.models.interfaces.UploadReportInterface;
import com.quicsolv.appointmentapp.retrofit.models.interfaces.UploadedReportsListInterface;
import com.quicsolv.appointmentapp.retrofit.models.pojo.uploadedfilelist.Datum;
import com.quicsolv.appointmentapp.retrofit.models.pojo.uploadedfilelist.UploadedReportsListResponse;
import com.quicsolv.appointmentapp.retrofit.models.pojo.uploadreport.UploadReportResponse;
import com.quicsolv.appointmentapp.utils.Constants;
import com.quicsolv.appointmentapp.utils.Prefs;

import java.io.File;
import java.util.List;
import java.util.StringTokenizer;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReportsFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "ReportsFragment";
    private Context mContext;
    private ListView listviewReports;
    private ProgressBar progressbar;
    private LinearLayout layoutNoRecord;
    private Button btnUploadDocs;
    private UploadedReportsListInterface uploadedReportsListInterface;
    private List<Datum> listReports;
    File file1;
    String uploadedFileName, first, file_1;
    StringTokenizer tokens;
    int serverResponseCode;
    private static final String IMAGE_DIRECTORY = "/appointmentapp";
    private String imagePath;
    private File file;
    String mediaPath;
    private UploadReportInterface uploadReportInterface;

    public ReportsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reports, container, false);

        ((DashboardActivity) getActivity()).setToolBarTitle("Reports");
        uploadedReportsListInterface = RetrofitClient.getClient(RetrofitConstants.BASE_URL).create(UploadedReportsListInterface.class);
        uploadReportInterface = RetrofitClient.getClient(RetrofitConstants.BASE_URL).create(UploadReportInterface.class);

        mContext = getActivity();

        getIds(view);

        progressbar.setVisibility(View.VISIBLE);
        getReportsList();

        return view;
    }

    private void getReportsList() {
//        String pid = Prefs.getSharedPreferenceString(mContext, Prefs.PREF_PID, "");
        String pid = "1";
        uploadedReportsListInterface.getReportsList(pid).enqueue(new Callback<UploadedReportsListResponse>() {
            @Override
            public void onResponse(Call<UploadedReportsListResponse> call, Response<UploadedReportsListResponse> response) {
                progressbar.setVisibility(View.GONE);
                if (response != null && response.body().getCode() == Constants.ERROR_CODE_200) {

                    Prefs.setSharedPreferenceString(mContext, Prefs.PREF_UPLOADED_FILE_BASE_URL, response.body().getBaseUrl());
                    if (response.body().getData() != null) {
                        listReports = response.body().getData();

                        UploadedFilesListAdapter customAdapter = new UploadedFilesListAdapter(mContext, R.layout.row_appointment_history, listReports);
                        listviewReports.setAdapter(customAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<UploadedReportsListResponse> call, Throwable t) {

            }
        });
    }


    private void getIds(View view) {
        listviewReports = (ListView) view.findViewById(R.id.listview_reports);
        progressbar = (ProgressBar) view.findViewById(R.id.progress_reports);
        layoutNoRecord = (LinearLayout) view.findViewById(R.id.layout_no_reports);

        btnUploadDocs = (Button) view.findViewById(R.id.btn_upload_docs);
        btnUploadDocs.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_upload_docs:
                showFileChooser();
                break;
        }
    }

    private void showFileChooser() {
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("application/*");
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//
//        try {
//            startActivityForResult(
//                    Intent.createChooser(intent, "Select a File to Upload"),
//                    1);
//        } catch (android.content.ActivityNotFoundException ex) {
//            Toast.makeText(getActivity(), "Please install a File Manager.",
//                    Toast.LENGTH_SHORT).show();
//        }

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 0);
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // TODO Auto-generated method stub
//        super.onActivityResult(requestCode, resultCode, data);
////        if (requestCode == 1) {
////            if (resultCode == Activity.RESULT_OK) {
////                Uri selectedFileURI = data.getData();
////                File file = new File(selectedFileURI.getPath().toString());
////
////
////                if (data != null) {
////                    Uri contentURI = data.getData();
////                    try {
////                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
////
////                        /*************************************************************************
////                         * TODO compress Bitmap file
////                         * *********************************************************************/
////                        // Initialize a new ByteArrayStream
////                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
////                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
////                        //compress bitmap
////                        byte[] byteArray = stream.toByteArray();
////                        Bitmap compressedBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
////                        imagePath = saveImage(compressedBitmap);
////                        Log.e("IMAGEAPTH", "" + imagePath);
////
////                        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
////                            Toast.makeText(mContext, "No Permission", Toast.LENGTH_SHORT).show();
////                        } else {
////                            PostDataAsyncTask asyncTask = new PostDataAsyncTask();
////                            asyncTask.execute(imagePath);
////                        }
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                        Toast.makeText(getActivity(), "Image uploading Failed!", Toast.LENGTH_SHORT).show();
////                    }
////                }
////            }
////        }
//        try {
//            // When an Image is picked
//            if (resultCode == 0 && null != data) {
//
//                // Get the Image from data
//                Uri selectedImage = data.getData();
//                String[] filePathColumn = {MediaStore.Images.Media.DATA};
//
//                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//                assert cursor != null;
//                cursor.moveToFirst();
//
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                mediaPath = cursor.getString(columnIndex);
//                // Set the Image in ImageView for Previewing the Media
////                imgView.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
//                cursor.close();
//
//                uploadFile();
//
//            } else {
//                Toast.makeText(mContext, "Not picked Image", Toast.LENGTH_LONG).show();
//            }
//        } catch (Exception e) {
//            Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_LONG).show();
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {

                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                // Set the Image in ImageView for Previewing the Media
                imgView.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();

            } else {
                Toast.makeText(this, "You haven't picked Image/Video", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }


    // Uploading Image/Video
    private void uploadFile() {
//        progressDialog.show();

        // Map is used to multipart the file using okhttp3.RequestBody
        File file = new File(mediaPath);

        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        uploadReportInterface.uploadReportFile(fileToUpload, filename, "3", "2").enqueue(new Callback<UploadReportResponse>() {
            @Override
            public void onResponse(Call<UploadReportResponse> call, Response<UploadReportResponse> response) {
                Log.d("", "");
            }

            @Override
            public void onFailure(Call<UploadReportResponse> call, Throwable t) {
                Log.d("", "");
            }
        });


//        ApiService getResponse = ApiClient.getClient().create(ApiService.class);
//        Call<ServerResponse> call = getResponse.uploadFile(fileToUpload, filename);
//        call.enqueue(new Callback<ServerResponse>() {
//            @Override
//            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
//                ServerResponse serverResponse = response.body();
//                if (serverResponse != null) {
//                    if (serverResponse.getSuccess()) {
//                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    assert serverResponse != null;
//                    Log.v("Response", serverResponse.toString());
//                }
//                progressDialog.dismiss();
//            }
//
//            @Override
//            public void onFailure(Call<ServerResponse> call, Throwable t) {
//
//            }
//        });
    }
//
//    public String saveImage(Bitmap myBitmap) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//        File wallpaperDirectory = new File(
//                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
//        // have the object build the directory structure, if needed.
//        if (!wallpaperDirectory.exists()) {
//            wallpaperDirectory.mkdirs();
//        }
//
//        try {
//            File f = new File(wallpaperDirectory, Calendar.getInstance()
//                    .getTimeInMillis() + ".jpg");
//            f.createNewFile();
//            FileOutputStream fo = new FileOutputStream(f);
//            fo.write(bytes.toByteArray());
//            MediaScannerConnection.scanFile(getActivity(),
//                    new String[]{f.getPath()},
//                    new String[]{"image/jpeg"}, null);
//            fo.close();
//            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());
//
//            return f.getAbsolutePath();
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
//        return "";
//    }
//
//
//    public class PostDataAsyncTask extends AsyncTask<String, String, String> {
//
//        ProgressDialog dialog;
//
//        protected void onPreExecute() {
//            super.onPreExecute();
//            dialog = new ProgressDialog(getActivity());
//            dialog.setCancelable(false);
//            dialog.setMessage("Please wait ...");
//            dialog.show();
//        }
//
//        @Override
//        protected String doInBackground(String... strparams) {
//            try {
//                String imagePath = strparams[0];
//
//                file = new File(imagePath);
//
//                String fileName = imagePath;
//                HttpURLConnection conn = null;
//                OutputStream os = null;
//                int bytesRead, bytesAvailable, bufferSize;
//                byte[] buffer;
//                int maxBufferSize = 1 * 1024 * 1024;
//                File sourceFile = new File(imagePath);
//
//                if (!sourceFile.isFile()) {
//
//                    if (dialog != null && dialog.isShowing()) {
//                        dialog.dismiss();
//                    }
//
//                    Log.e("uploadFile", "Source File not exist :" + imagePath);
//
//
//                } else {
//                    try {
//
//                        // open a URL connection to the Servlet
//                        FileInputStream fileInputStream = new FileInputStream(sourceFile);
//                        URL url = new URL("http://qtademo.com/bookingapp/api/upload_report");
//
//                        // Open a HTTP  connection to  the URL
//                        conn = (HttpURLConnection) url.openConnection();
//                        conn.setDoInput(true); // Allow Inputs
//                        conn.setDoOutput(true); // Allow Outputs
//                        conn.setUseCaches(false); // Don't use a Cached Copy
//                        conn.setRequestMethod("POST");
////                        conn.setRequestProperty("Connection", "Keep-Alive");
//                        conn.setRequestProperty("ENCTYPE", "multipart/form-data");
//                        conn.setRequestProperty("Content-Type", "multipart/form-data");
//                        conn.setRequestProperty("file", fileName);
////                        conn.setRequestProperty("pid","3");
////                        conn.setRequestProperty("rt_id","2");
//
//
//                        List<NameValuePair> params = new ArrayList<NameValuePair>();
////                        params.add(new BasicNameValuePair("file", fileName));
//                        params.add(new BasicNameValuePair("pid", "3"));
//                        params.add(new BasicNameValuePair("rt_id", "2"));
//
//                        os = new DataOutputStream(conn.getOutputStream());
//                        BufferedWriter writer = new BufferedWriter(
//                                new OutputStreamWriter(os, "UTF-8"));
//                        writer.write(getQuery(params));
//
////                        // create a buffer of  maximum size
////                        bytesAvailable = fileInputStream.available();
////
////                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
////                        buffer = new byte[bufferSize];
////
////                        // read file and write it into form...
////                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
////
////                        while (bytesRead > 0) {
////
////                            os.write(buffer, 0, bufferSize);
////                            bytesAvailable = fileInputStream.available();
////                            bufferSize = Math.min(bytesAvailable, maxBufferSize);
////                            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
////
////                        }
//
//
//                        // Responses from the server (code and message)
//                        serverResponseCode = conn.getResponseCode();
//                        String serverResponseMessage = conn.getResponseMessage();
//
//                        Log.i("uploadFile", "HTTP Response is : "
//                                + serverResponseMessage + ": " + serverResponseCode);
//
//
//                        //close the streams //
//                        fileInputStream.close();
//                        os.flush();
//                        os.close();
//
//                    } catch (MalformedURLException ex) {
//
//                        if (dialog != null && dialog.isShowing()) {
//                            dialog.dismiss();
//                        }
//                        ex.printStackTrace();
//
//                        Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
//                    } catch (Exception e) {
//
//                        if (dialog != null && dialog.isShowing()) {
//                            dialog.dismiss();
//                        }
//                        e.printStackTrace();
//
//
//                    }
//
//                    if (dialog != null && dialog.isShowing()) {
//                        dialog.dismiss();
//
//                    }
//                }
//
//            } catch (NullPointerException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            Log.e("", "RESULT : " + result);
//
//        }
//    }
//
//    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException {
//        StringBuilder result = new StringBuilder();
//        boolean first = true;
//
//        for (NameValuePair pair : params) {
//            if (first)
//                first = false;
//            else
//                result.append("&");
//
//            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
//            result.append("=");
//            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
//        }
//
//        return result.toString();
//    }
}
