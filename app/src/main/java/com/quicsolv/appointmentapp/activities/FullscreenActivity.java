package com.quicsolv.appointmentapp.activities;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.quicsolv.appointmentapp.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {

    private static final String TAG = "FullscreenActivity";
    private Context mContext;
    private ImageView ivBack;
    private WebView ivReport;
    private ProgressBar progressBar;
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();

    // We can be in one of these 3 states
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    // Remember some things for zooming
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;
    String savedItemClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        mContext = FullscreenActivity.this;

        getIds();

    }

    private void getIds() {
        ivReport = (WebView) findViewById(R.id.webview_report);
        ivReport.getSettings().setJavaScriptEnabled(true);
        ivReport.getSettings().setLoadWithOverviewMode(true);
        ivReport.getSettings().setUseWideViewPort(true);
        ivReport.getSettings().setBuiltInZoomControls(true);

        ivBack = (ImageView) findViewById(R.id.iv_back);

        progressBar = (ProgressBar) findViewById(R.id.progress_report);

        progressBar.setVisibility(View.VISIBLE);
        if (getIntent() != null && getIntent().getExtras() != null) {
            String imgPath = getIntent().getExtras().getString("ReportImage");

            progressBar.setVisibility(View.VISIBLE);

            ivReport.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    progressBar.setVisibility(View.GONE);

                }
            });
            if (imgPath.contains("pdf")) {
                ivReport.loadUrl("https://docs.google.com/viewer?url=" + imgPath);
            } else {
                ivReport.loadUrl(imgPath);
            }


        }

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
