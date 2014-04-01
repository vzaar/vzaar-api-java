package com.vzaar.examples.android.upload;

import android.content.Context;
import android.os.AsyncTask;
import com.vzaar.Vzaar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class processVideoActivity extends Activity {
	
	public EditText txtTitle;
	public EditText txtDesc;
    public EditText txtLabels;
    public String guid;
    
    private String token;
    private String secret;
    private Vzaar vzaarApi;
    
    private String videoId;
    
    public Button btnProcessVideo;
     
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.processvideo);
        assignControls();
        addListeneres();
        
        Intent intent = getIntent();
        guid = intent.getStringExtra("guid");
        token = intent.getStringExtra("token");
        secret = intent.getStringExtra("secret");
    }
    
    private void addListeneres() {
		btnProcessVideo.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				if ((token.length() > 0) && (secret.length() > 0)) {
					processFile();
					finish();
					return;
				}
			}
		});
		
	}

	private void assignControls() {
    	txtTitle = (EditText)findViewById(R.id.txtTitle);
    	txtDesc = (EditText)findViewById(R.id.txtDesc);
    	txtLabels = (EditText)findViewById(R.id.txtLabels);
    	btnProcessVideo = (Button)findViewById(R.id.processBtn);
    }

    private class ProcessVideoTask extends AsyncTask<String, Integer, String> {
        private Context context;
        public ProcessVideoTask(Context context) {
            super();
            this.context = context;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            if (null == vzaarApi)
                vzaarApi = new Vzaar(token, secret);
            if (guid.length() > 0) {
                videoId = vzaarApi.processVideo(guid, txtTitle.getText().toString(), txtDesc.getText().toString(), txtLabels.getText().toString());
            }

            return guid;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Intent retIntent = new Intent();
            retIntent.putExtra("video_id", videoId);
            setResult(RESULT_OK, retIntent);
            Toast.makeText(getApplicationContext(), "Video Processed\nVideo Id - " + videoId, 40).show();

        }

        @Override
        protected void onProgressUpdate(Integer... progressPercent) {

        }
    }

    private void processFile(){
        ProcessVideoTask task = new ProcessVideoTask(this);
        task.execute();

    }

}
