package com.quicsolv.appointmentapp.adapters;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quicsolv.appointmentapp.R;
import com.quicsolv.appointmentapp.activities.FullscreenActivity;
import com.quicsolv.appointmentapp.retrofit.models.pojo.uploadedfilelist.Datum;
import com.quicsolv.appointmentapp.utils.Prefs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  23 Apr 2018
 ***********************************************************************/

public class UploadedFilesListAdapter extends ArrayAdapter<Datum> {

    public Context mContext;

    public UploadedFilesListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public UploadedFilesListAdapter(Context context, int resource, List<Datum> items) {
        super(context, resource, items);
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.row_uploaded_files, null);
        }

        final Datum p = getItem(position);

        if (p != null) {
            TextView txtReportName = (TextView) v.findViewById(R.id.txt_report_name);
            TextView txtReportDate = (TextView) v.findViewById(R.id.txt_report_date);
            RelativeLayout btnViewReport = (RelativeLayout) v.findViewById(R.id.btn_view_report);
            RelativeLayout btnDownloadReport = (RelativeLayout) v.findViewById(R.id.btn_download_report);

            if (txtReportName != null && p.getRtName() != null) {
                txtReportName.setText(p.getRtName().toString());
            }

            if (txtReportDate != null && p.getCreatedDatetime() != null) {

                SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

                // Create a calendar object that will convert the date and time value in milliseconds to date.
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(Long.parseLong(p.getCreatedDatetime()));

                try {
                    String date = formatter.format(calendar.getTime());
                    txtReportDate.setText(date);
                } catch (Exception e) {

                }
            }

            btnViewReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, FullscreenActivity.class);
                    intent.putExtra("ReportImage", Prefs.getSharedPreferenceString(mContext, Prefs.PREF_UPLOADED_FILE_BASE_URL, "") + p.getLrPath());
                    mContext.startActivity(intent);
                }
            });

            btnDownloadReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(mContext, "No Permission", Toast.LENGTH_SHORT).show();
                    } else {
                        MyAsyncTask asyncTask = new MyAsyncTask();
                        asyncTask.execute(Prefs.getSharedPreferenceString(mContext, Prefs.PREF_UPLOADED_FILE_BASE_URL, "") + p.getLrPath());
                    }
                }
            });
        }

        return v;
    }


    class MyAsyncTask extends AsyncTask<String, Void, Void> {

        ProgressDialog mProgressDialog = new ProgressDialog(mContext);


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.setMessage("Downloading");
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setMax(100);
            mProgressDialog.setCancelable(true);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                String imagePath = params[0];
                String imageName = imagePath.substring(imagePath.indexOf("reports"), imagePath.length()).replace("reports/", "");
                URL url = new URL(imagePath);
                //create the new connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //set up some things on the connection
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoOutput(true);
                //and connect!
                urlConnection.connect();
                //set the path where we want to save the file in this case, going to save it on the root directory of the sd card.
                File SDCardRoot = Environment.getExternalStorageDirectory();
                //create a new file, specifying the path, and the filename which we want to save the file as.
                File file = new File(SDCardRoot, imageName);
                //this will be used to write the downloaded data into the file we created
                FileOutputStream fileOutput = new FileOutputStream(file);
                //this will be used in reading the data from the internet
                InputStream inputStream = urlConnection.getInputStream();
                //this is the total size of the file
                int totalSize = urlConnection.getContentLength();
                //variable to store total downloaded bytes
                long downloadedSize = 0;
                byte[] buffer = new byte[1024];
                int bufferLength = 0; //used to store a temporary size of the buffer
                //now, read through the input buffer and write the contents to the file
                while ((bufferLength = inputStream.read(buffer)) != -1) {
                    //add the data in the buffer to the file in the file output stream (the file on the sd card
                    fileOutput.write(buffer, 0, bufferLength);
                    //add up the size so we know how much is downloaded
                    downloadedSize += bufferLength;

//                    publishProgress((int)(downloadedSize * 100 / totalSize));
//                    fileOutput.write(buffer, 0, bufferLength);
                    //this is where you would do something to report the prgress, like this maybe
                    //updateProgress(downloadedSize, totalSize);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

//        private void publishProgress(int i) {
//            mProgressDialog.setProgress(i);
//            if (mProgressDialog.getProgress() == mProgressDialog.getMax()) {
//                mProgressDialog.dismiss();
//                Toast.makeText(mContext, "File Downloaded", Toast.LENGTH_SHORT).show();
//            }
//        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mProgressDialog.dismiss();
            Toast.makeText(mContext, "File Downloaded", Toast.LENGTH_SHORT).show();
        }
    }
}
