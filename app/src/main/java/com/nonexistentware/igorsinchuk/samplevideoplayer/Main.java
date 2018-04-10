package com.nonexistentware.igorsinchuk.samplevideoplayer;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Main extends Activity {
	private Cursor videoCursor;
	private int videoColumnIndex;
	ListView videoList;
	int count;
	String[] thumbColumns = {MediaStore.Video.Thumbnails.DATA,
			MediaStore.Video.Thumbnails.VIDEO_ID};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		videoGallery();
	}

	private void videoGallery() {
		String[] videoData = {MediaStore.Video.Media._ID,
				MediaStore.Video.Media.DATA,
				MediaStore.Video.Media.DISPLAY_NAME,
				MediaStore.Video.Media.SIZE};
		videoCursor = getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
				videoData, null, null, null);
		count = videoCursor.getCount();
		videoList = (ListView) findViewById(R.id.PhoneVideoList);
		videoList.setAdapter(new VideoAdapter(getApplicationContext()));
		videoList.setOnItemClickListener(videogridlistener);
	}

	private OnItemClickListener videogridlistener = new OnItemClickListener() {
		public void onItemClick(AdapterView parent, View v, int position,
								long id) {
			videoColumnIndex = videoCursor
					.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
			videoCursor.moveToPosition(position);
			String filename = videoCursor.getString(videoColumnIndex);
			Intent intent = new Intent(Main.this,
					ViewVideo.class);
			intent.putExtra("VideoFile", filename);
			startActivity(intent);
		}
	};

	public class VideoAdapter extends BaseAdapter {
		private Context videoContext;

		public VideoAdapter(Context context) {
			videoContext = context;
		}

		public int getCount() {
			return count;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			String id = null;
			convertView = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(videoContext).inflate(
						R.layout.listitem, parent, false);
				holder = new ViewHolder();
				holder.txtTitle = (TextView) convertView
						.findViewById(R.id.txtTitle);
				holder.thumbImage = (ImageView) convertView
						.findViewById(R.id.imgIcon);

				videoColumnIndex = videoCursor
						.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
				videoCursor.moveToPosition(position);
				id = videoCursor.getString(videoColumnIndex);
				videoColumnIndex = videoCursor
						.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);
				videoCursor.moveToPosition(position);
				// id += " Size(KB):" +
				// videoCursor.getString(videoColumnIndex);
				holder.txtTitle.setText(id);

				String[] proj = {MediaStore.Video.Media._ID,
						MediaStore.Video.Media.DISPLAY_NAME,
						MediaStore.Video.Media.DATA};


				Cursor cursor = getContentResolver().query(
						MediaStore.Video.Media.EXTERNAL_CONTENT_URI, proj,
						MediaStore.Video.Media.DISPLAY_NAME + "=?",
						new String[]{id}, null);
				cursor.moveToFirst();
				long ids = cursor.getLong(cursor
						.getColumnIndex(MediaStore.Video.Media._ID));

				ContentResolver crThumb = getContentResolver();
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 1;
				Bitmap bitmap = MediaStore.Video.Thumbnails.getThumbnail(
						crThumb, ids, MediaStore.Video.Thumbnails.MICRO_KIND,
						options);
				holder.thumbImage.setImageBitmap(bitmap);
				bitmap = null;

			}
			return convertView;
		}
	}
	static class ViewHolder {

		TextView txtTitle;
		ImageView thumbImage;
	}
}