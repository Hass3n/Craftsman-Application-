package astbina.sanetna.PostsWork;


import android.app.Activity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import astbina.sanetna.R;

public class WebviewActivity extends AppCompatActivity {
    WebView webView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        progressBar = findViewById(R.id.progressbar);
        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new CustomWebView(progressBar));
        final Activity activity = this;
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
        Toast.makeText(activity, getIntent().getStringExtra("uri"), Toast.LENGTH_SHORT).show();
        webView.loadUrl(getIntent().getStringExtra("uri"));
        webView.getSettings().getJavaScriptEnabled();
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
    }

    public class CustomWebView extends WebViewClient {
        private ProgressBar progressBar;

        public CustomWebView(ProgressBar progressBar) {
            this.progressBar = progressBar;
            webView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            webView.getSettings().getJavaScriptEnabled();
            webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
