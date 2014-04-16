package com.vzaar.examples.android.whoami;

import com.vzaar.Vzaar;
import com.vzaar.VzaarException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class whoamiActivity extends Activity {
    /** Called when the activity is first created. */
	
	 TextView lblWhoAmi;
     
     EditText txtToken;
     EditText txtSecret;
     
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button btnWhoAmi = (Button)findViewById(R.id.whoAmIBtn);
        lblWhoAmi = (TextView)findViewById(R.id.whoAmILbl);
        lblWhoAmi.setText(R.string.empty);
        txtToken = (EditText)findViewById(R.id.txtToken);
        txtSecret = (EditText)findViewById(R.id.txtSecret);
        
        
        btnWhoAmi.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String response = getWhoAmI();
				if (response.length() > 0)
					lblWhoAmi.setText(response);
				else {
					Toast.makeText(getApplicationContext(), "Invalid Username / Api Token!.", 10).show();
					lblWhoAmi.setText("Who AM I - Invalid inputs!");
				}
				
			}
		});        
        
    }
    
    private String getWhoAmI() {
    	String token = txtToken.getText().toString();
    	String userName = txtSecret.getText().toString();
    	
    	if ((token.length() > 0) && (userName.length() > 0)) {
    		Vzaar api = new Vzaar(userName, token);
    		String whoami;
			try {
				whoami = api.whoAmI();
				if (whoami.length() > 0) {
	    			return "Who Am I - " + whoami;    			
	    		}
			} catch (VzaarException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	} else {
    		Toast.makeText(getApplicationContext(), "Invalid Username / Api Token!.", 10).show();
    	}
    	return "";
    	
    }
}