package com.kevin.share;

import java.io.*;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
    public static File getRobotCacheFile(Context context) throws IOException {
//        File cacheFile = new File(context.getCacheDir(), "logo.jpg");
//        File cacheFile = new File("a.jpg");
        File dir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File cacheFile = new File(dir, "/logo.jpg");
        try {
            InputStream inputStream = context.getAssets().open("logo.jpg");
            try {
                FileOutputStream outputStream = new FileOutputStream(cacheFile);
                try {
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = inputStream.read(buf)) > 0) {
                        outputStream.write(buf, 0, len);
                    }
                } finally {
                    outputStream.close();
                }
            } finally {
                inputStream.close();
            }
        } catch (IOException e) {
            throw new IOException("Could not open robot png", e);
        }
        return cacheFile;
    }
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

        File file = new File("test");
        try {
            file = getRobotCacheFile(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try {
////		File file = new File(dir, "/logo.jpg");
//		AssetManager am = getAssets();
//        InputStream inputStream = null;
//
//            inputStream = am.open("logo.jpg");
//
//        File file = createFileFromInputStream(inputStream);


		switch (item.getItemId()) {
		case R.id.action_share1:
			System.out.println(file.toURI());
			
			shareToFriend(file);
			return true;
		case R.id.action_share2:
//            this.getAssets().open("logo.jpg");
			System.out.println(file.toURI());
			shareToTimeLine(file);
			
			return true;
		case R.id.action_share3:
			File video = new File("test");
            try {
                file = getRobotCacheFile(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
			shareToFriendVideo(video);

			return true;
		default:
			return super.onOptionsItemSelected(item);

		}


	}

	private void shareToFriend(File file) {
		
		Intent intent = new Intent();
		ComponentName comp = new ComponentName("com.tencent.mm",
				"com.tencent.mm.ui.tools.ShareImgUI");
		intent.setComponent(comp);
		intent.setAction("android.intent.action.SEND");
		intent.setType("image/*");
		//intent.setFlags(0x3000001);
		intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
		startActivity(intent);
	}
	
	private void shareToFriendVideo(File file) {
		
		Intent intent = new Intent();
		ComponentName comp = new ComponentName("com.tencent.mm",
				"com.tencent.mm.ui.tools.ShareImgUI");
		intent.setComponent(comp);
		intent.setAction("android.intent.action.SEND");
		intent.setType("video/*");
		//intent.setFlags(0x3000001);
		intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
		startActivity(intent);
	}
	
	private void shareToTimeLine(File file) {
		Intent intent = new Intent();
		ComponentName comp = new ComponentName("com.tencent.mm",
				"com.tencent.mm.ui.tools.ShareToTimeLineUI");
		intent.setComponent(comp);
		intent.setAction("android.intent.action.SEND");
		intent.setType("image/*");
		//intent.setFlags(0x3000001);
		intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
		startActivity(intent);
	}
}
