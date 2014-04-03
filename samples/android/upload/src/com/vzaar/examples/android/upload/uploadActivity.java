package com.vzaar.examples.android.upload;
import java.io.File;
import com.vzaar.ProgressListener;
import com.vzaar.Vzaar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class uploadActivity extends Activity{
	
	 public EditText txtToken;
     public EditText txtSecret;
     public Button btnSelectFile;
     public Button btnUploadFile;
     public Vzaar vzaarApi;
     private String filePath;
     public ProgressBar progress;
     private long contentLength;
     public String guid; 
     public String videoId;
     private static final int SELECT_VIDEO_DIALOG = 1;
     private static final int PROCESS_VIDEO_DIALOG = 2;
     
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);        
        assignControls();
        addListeneres();
    }
    
    private void assignControls() {
    	txtToken = (EditText)findViewById(R.id.txtToken);
        txtSecret = (EditText)findViewById(R.id.txtSecret);
        btnSelectFile = (Button)findViewById(R.id.selectBtn);
        btnUploadFile = (Button)findViewById(R.id.uploadBtn);
        progress = (ProgressBar)findViewById(R.id.uploadProgress);
        btnUploadFile.setEnabled(false);
    }
    
    private void addListeneres() {
    	btnSelectFile.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				selectFile();				
			}
		});
    	
    	btnUploadFile.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				uploadFile();
			}
		});
    }
    
    private class uploadVideoTask extends AsyncTask<String, Integer, String> {
    	private String token;
    	private String secret;
    	private Context context;
    	public uploadVideoTask(Context context) {
    		super();
    		this.context = context;
    	}
    	@Override
    	protected void onPreExecute() {
    		super.onPreExecute();
    		token = txtToken.getText().toString();
    		secret = txtSecret.getText().toString();
    		btnSelectFile.setEnabled(false);
    		btnUploadFile.setEnabled(false);
    		progress.setMax(100);
			progress.setVisibility(ProgressBar.VISIBLE);
		}
    	
		@Override
		protected String doInBackground(String... params) {
			System.out.println("Length " + params.length + "\nFile - " + params[0]);
			for (int i = 0; i < params.length; i++){	    		
	    		if ((token.length() > 0) && (secret.length() > 0)) {
	    			if (null == vzaarApi)
	    				vzaarApi = new Vzaar(token, secret);	    			
	    			contentLength = new File(params[i]).length();
					try {
						Looper.prepare();
						guid = vzaarApi.uploadVideo(params[i], new ProgressListener() {							
							public void update(long bytesRead) {
								//Number progressPercent = (bytesRead/contentLength);
								Integer percent = (int) (((float) bytesRead / (float) contentLength) * 100);
								System.out.println("Progress Percent " + percent +"% Bytes Read "+ bytesRead + " Content Length " + contentLength);
								//progress.setProgress(progressPercent);
								
								
								publishProgress(percent);
							}
						});		
						System.out.println("GUID " + guid);
					} catch (Exception e) {
						Toast.makeText(getApplicationContext(), "Exception - " + e.getMessage(), 40).show();
					}
	    		}
			}
			return guid;
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			progress.setVisibility(ProgressBar.INVISIBLE);
			btnSelectFile.setEnabled(false);
    		btnUploadFile.setEnabled(false);
    		System.out.println("REsult GUID - " + result);
    		Intent processIntent = new Intent(context, processVideoActivity.class);
    		processIntent.putExtra("guid", result);
    		processIntent.putExtra("token", txtToken.getText().toString());
    		processIntent.putExtra("secret", txtSecret.getText().toString());
    		startActivityForResult(processIntent, PROCESS_VIDEO_DIALOG);
		}
		
		@Override
	    protected void onProgressUpdate(Integer... progressPercent) {
	      super.onProgressUpdate( progressPercent[0]);  
	      progress.setProgress( progressPercent[0]);
	      
	    }
    }
        
    private void uploadFile(){		
    	uploadVideoTask task = new uploadVideoTask(this);
    	task.execute(filePath);
    }
    
    private void selectFile() {    	
    	Intent intent = new Intent();
    	intent.setType("*/*");
    	intent.addCategory(Intent.CATEGORY_OPENABLE);
    	intent.setAction(Intent.ACTION_GET_CONTENT);    	
    	startActivityForResult(Intent.createChooser(intent, "Select Video"), SELECT_VIDEO_DIALOG);
    	
    	
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_VIDEO_DIALOG) {            	
                Uri data = result.getData();
                filePath = getRealPathFromURI(data);
                Toast.makeText(getApplicationContext(), "File path - " + filePath, 10).show();
                btnUploadFile.setEnabled(true);
            } else if (requestCode == PROCESS_VIDEO_DIALOG) {
            	videoId = result.getStringExtra("video_id");
            	Toast.makeText(getApplicationContext(), "Video Processed\nVideo Id - " + videoId, 40).show();
            }
        }
    }
    
 // And to convert the image URI to the direct file system path of the image file
    public String getRealPathFromURI(Uri contentUri) {

            // can post image
            String [] proj={MediaStore.Video.Media.DATA};
            Cursor cursor = managedQuery( contentUri,
                            proj, // Which columns to return
                            null,       // WHERE clause; which rows to return (all rows)
                            null,       // WHERE clause selection arguments (none)
                            null); // Order-by clause (ascending by name)
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();

            return cursor.getString(column_index);
    }

//	@Override
//	public void update(long bytesRead) {		
//		int progressPercent = (int) ((bytesRead/contentLength) * 100);
//		progress.setProgress(progressPercent);
//	}
}
