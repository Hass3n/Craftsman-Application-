package astbina.sanetna.Basic;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import astbina.sanetna.R;

public class WebSite extends AppCompatActivity {

    private WebView myWebView;
    ProgressBar progressBar;
    public static String ul_name="";
    // ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        myWebView = (WebView)findViewById(R.id.webView);
        progressBar = findViewById(R.id.progressbar);
        myWebView.setWebViewClient(new CustomWebView(progressBar));
        // isReadStoragePermissionGranted();
        // isWriteStoragePermissionGranted();
        //  onRequestPermissionsResult();
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        myWebView.loadUrl(ul_name);
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.setDownloadListener(new DownloadListener()
        {
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength)
            {
                //for downloading directly through download manager
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "download");
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
            }
        });
        myWebView.setDownloadListener(new DownloadListener()
        {
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength)
            {
                //download file using web browser
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });



    }
    public class CustomWebView extends WebViewClient {
        private ProgressBar progressBar;

        public CustomWebView(ProgressBar progressBar) {
            this.progressBar = progressBar;
            myWebView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            myWebView.getSettings().getJavaScriptEnabled();
            myWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
            myWebView.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onBackPressed() {
        if(myWebView.canGoBack()) {
            myWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}
