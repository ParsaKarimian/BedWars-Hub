package com.bedwarshub.app;
import android.app.Activity;import android.app.DownloadManager;import android.content.Intent;import android.net.Uri;import android.os.Bundle;import android.os.Environment;import android.webkit.*;
public class MainActivity extends Activity{
 static final String SITE="https://karimal.ir",HOST="karimal.ir"; // change to your domain
 WebView w;ValueCallback<Uri[]> fcb;
 @Override protected void onCreate(Bundle b){super.onCreate(b);
  w=new WebView(this);setContentView(w);
  WebSettings s=w.getSettings();s.setJavaScriptEnabled(true);s.setDomStorageEnabled(true);s.setDatabaseEnabled(true);s.setMediaPlaybackRequiresUserGesture(false);s.setAllowFileAccess(false);
  CookieManager.getInstance().setAcceptCookie(true);CookieManager.getInstance().setAcceptThirdPartyCookies(w,true);
  w.setWebViewClient(new WebViewClient(){@Override public boolean shouldOverrideUrlLoading(WebView v,WebResourceRequest r){Uri u=r.getUrl();String h=u.getHost();if(h!=null&&(h.equals(HOST)||h.endsWith("."+HOST)))return false;startActivity(new Intent(Intent.ACTION_VIEW,u));return true;}});
  w.setWebChromeClient(new WebChromeClient(){@Override public boolean onShowFileChooser(WebView v,ValueCallback<Uri[]> cb,FileChooserParams p){if(fcb!=null)fcb.onReceiveValue(null);fcb=cb;try{startActivityForResult(p.createIntent(),1);}catch(Exception e){fcb=null;return false;}return true;}});
  w.setDownloadListener((url,ua,cd,mime,len)->{DownloadManager.Request r=new DownloadManager.Request(Uri.parse(url));r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,URLUtil.guessFileName(url,cd,mime));((DownloadManager)getSystemService(DOWNLOAD_SERVICE)).enqueue(r);});
  if(b==null)w.loadUrl(SITE);else w.restoreState(b);}
 @Override protected void onActivityResult(int c,int r,Intent d){if(c==1&&fcb!=null){fcb.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(r,d));fcb=null;}}
 @Override public void onBackPressed(){if(w.canGoBack())w.goBack();else super.onBackPressed();}
 @Override protected void onSaveInstanceState(Bundle b){super.onSaveInstanceState(b);w.saveState(b);}
}
