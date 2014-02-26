package com.vzaar.phonegap;

import java.math.BigInteger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.phonegap.api.Plugin;
import com.phonegap.api.PluginResult;
import com.vzaar.AccountsType;
import com.vzaar.Profile;
import com.vzaar.UploadSignature;
import com.vzaar.User;
import com.vzaar.VideoDetails;
import com.vzaar.VideoList;
import com.vzaar.Vzaar;
import com.vzaar.VzaarException;

public class VzaarPhoneGapPlugin extends Plugin {
	
	public static final String WHOAMI = "whoami";
	public static final String GET_USER_DETAILS = "getuserdetails";
	public static final String GET_ACCOUNT_DETAILS = "getaccountdetails";
	public static final String GET_VIDEO_LIST = "getvideolist";
	public static final String SEARCH_VIDEO_LIST = "searchvideolist";
	public static final String GET_VIDEO_DETAILS = "getvideodetails";
	public static final String EDIT_VIDEO = "editvideo";
	public static final String DELETE_VIDEO = "deletevideo";
	public static final String PROCESS_VIDEO = "processvideo";
	public static final String GET_UPLOAD_SIGNATURE = "getuploadsignature";
	public static final String UPLOAD_VIDEO = "uploadvideo";

	@Override
	public PluginResult execute(String action, JSONArray data, String callbackId) {
		PluginResult result = null;
		JSONObject obj = null;
		Gson gson = new Gson();
		try {
			obj = data.getJSONObject(0);
			if (WHOAMI.equalsIgnoreCase(action)) {				
				result = new PluginResult(PluginResult.Status.OK, whoAmI(obj));
			} else if (GET_USER_DETAILS.equalsIgnoreCase(action)) {
				User user = getUserDetails(obj);				
				JSONObject ret = new JSONObject(gson.toJson(user, User.class));
				result = new PluginResult(PluginResult.Status.OK, ret);
			} else if (GET_ACCOUNT_DETAILS.equalsIgnoreCase(action)) {
				AccountsType account = getAccountDetails(obj);	
				JSONObject ret = new JSONObject(gson.toJson(account, AccountsType.class));
				result = new PluginResult(PluginResult.Status.OK, ret);
			} else if (GET_VIDEO_LIST.equalsIgnoreCase(action)) {
				VideoList videoList = getVideoList(obj);	
				JSONArray ret = new JSONArray(gson.toJson(videoList, VideoList.class));
				result = new PluginResult(PluginResult.Status.OK, ret);
			} else if (SEARCH_VIDEO_LIST.equalsIgnoreCase(action)) {
				VideoList videoList = searchVideoList(obj);	
				JSONArray ret = new JSONArray(gson.toJson(videoList, VideoList.class));
				result = new PluginResult(PluginResult.Status.OK, ret);
			} else if (GET_VIDEO_DETAILS.equalsIgnoreCase(action)) {
				VideoDetails details = getVideoDetails(obj);	
				JSONObject ret = new JSONObject(gson.toJson(details, VideoDetails.class));
				result = new PluginResult(PluginResult.Status.OK, ret);
			} else if (EDIT_VIDEO.equalsIgnoreCase(action)) {
				result = new PluginResult(PluginResult.Status.OK, editVideo(obj));
			} else if (DELETE_VIDEO.equalsIgnoreCase(action)) {
				result = new PluginResult(PluginResult.Status.OK, deleteVideo(obj));
			} else if (PROCESS_VIDEO.equalsIgnoreCase(action)) {
				result = new PluginResult(PluginResult.Status.OK, processVideo(obj));
			} else if (GET_UPLOAD_SIGNATURE.equalsIgnoreCase(action)) {
				UploadSignature signature = getUploadSignature(obj);
				JSONObject ret = new JSONObject(gson.toJson(signature, UploadSignature.class));
				result = new PluginResult(PluginResult.Status.OK, ret);
			} else if (UPLOAD_VIDEO.equalsIgnoreCase(action)) {
				result = new PluginResult(PluginResult.Status.OK, uploadVideo(obj));
			} 
		} catch (JSONException e) {
			return new PluginResult(PluginResult.Status.JSON_EXCEPTION, gson.toJson(e, JSONException.class));
		} catch (VzaarException e) {
			return new PluginResult(PluginResult.Status.ERROR, gson.toJson(e, VzaarException.class));
		} catch (Exception e) {
			return new PluginResult(PluginResult.Status.ERROR, gson.toJson(e, Exception.class));
		}
		
		return result;
	}
	
	private Vzaar getVzaar(JSONObject obj) throws JSONException, VzaarException {
		Vzaar api = null;
		String token = obj.getString("token");
		String secret = obj.getString("secret");
		api = new Vzaar(token, secret);		
		return api;
	}
	
	private String whoAmI(JSONObject obj) throws JSONException, VzaarException {
		Vzaar api = getVzaar(obj);	
		String whoami = api.whoAmI();
		return whoami;
	}
	
	private User getUserDetails(JSONObject obj) throws JSONException, VzaarException {		
		String userName = null;
		Vzaar api = getVzaar(obj);
		userName = obj.getString("username");
		return api.getUserDetails(userName);		
	}
	
	private AccountsType getAccountDetails(JSONObject obj) throws JSONException, VzaarException {		
		int account = 0;
		Vzaar api = getVzaar(obj);
		account = obj.getInt("account");
		return api.getAccountDetails(account);		
	}
	
	private VideoList getVideoList(JSONObject obj) throws JSONException, VzaarException {		
		String userName;
		boolean auth = false;
		int count = 20;		
		String labels = "";
		String status = "";
		
		Vzaar api = getVzaar(obj);
		userName = obj.getString("username");
		if (obj.has("auth"))
			auth = obj.getBoolean("auth");
		if (obj.has("count"))
			count = obj.getInt("count");
		if (obj.has("labels"))
			labels = obj.getString("labels");
		if (obj.has("status"))
			status = obj.getString("status");
		
		return api.getVideoList(userName, auth, count, labels, status);
	}
	
	private VideoList searchVideoList(JSONObject obj) throws JSONException, VzaarException {		
		String userName;
		boolean auth = false;
		int count = 20;		
		String labels = "";		
		String title = "";
		int page = 1;
		String sort = "DESC";
		
		Vzaar api = getVzaar(obj);
		userName = obj.getString("username");
		if (obj.has("auth"))
			auth = obj.getBoolean("auth");
		if (obj.has("count"))
			count = obj.getInt("count");
		if (obj.has("page"))
			page = obj.getInt("page");
		if (obj.has("labels"))
			labels = obj.getString("labels");
		if (obj.has("title"))
			title = obj.getString("title");
		if (obj.has("sort"))
			title = obj.getString("sort");
		
		return api.searchVideoList(userName, auth, title, labels, count, page, sort);
	}
	
	private VideoDetails getVideoDetails(JSONObject obj) throws JSONException, VzaarException {		
		BigInteger id;
		boolean auth = false;
		Vzaar api = getVzaar(obj);
		id = new BigInteger(obj.getString("videoid"));
		
		if (obj.has("auth"))
			auth = obj.getBoolean("auth");
		
		return api.getVideoDetails(id, auth);		
	}
	
	private String editVideo(JSONObject obj) throws JSONException, VzaarException {
		BigInteger id;
		String title = "";
		boolean isprivate = false;			
		String description = "";
		String seoUrl = "";
		
		Vzaar api = getVzaar(obj);
		id = new BigInteger(obj.getString("videoid"));
		title = obj.getString("title");
		description = obj.getString("description");		
		if (obj.has("isprivate"))
			isprivate = obj.getBoolean("isprivate");		
		if (obj.has("seourl"))
			seoUrl = obj.getString("seourl");
		
		return api.editVideo(id, title, description, isprivate, seoUrl);
	}
	
	private String deleteVideo(JSONObject obj) throws JSONException, VzaarException {
		BigInteger id;
		Vzaar api = getVzaar(obj);
		id = new BigInteger(obj.getString("videoid"));		
		
		return api.deleteVideo(id);
	}
	
	private String processVideo(JSONObject obj) throws JSONException, VzaarException {
		String guid = "";
		String title = "";
		String description = "";
		String labels = "";
		int profile = Profile.Medium;
		boolean transcoding = false;
		String replace = "";
		Vzaar api = getVzaar(obj);
				
		guid = obj.getString("guid");
		labels = obj.getString("labels");	
		title = obj.getString("title");
		description = obj.getString("description");
		
		if(obj.has("profile"))
			profile = obj.getInt("profile");
		if(obj.has("transcoding"))
			transcoding = obj.getBoolean("transcoding");
		if (obj.has("replace"))
			replace = obj.getString("replace");		
		
		return api.processVideo(guid, title, description, labels, profile, transcoding, replace);
	}
	
	private UploadSignature getUploadSignature(JSONObject obj) throws JSONException, VzaarException {
		String redirectUrl = "";
		Vzaar api = getVzaar(obj);
		
		if (obj.has("redirecturl"))
			redirectUrl = obj.getString("redirecturl");
		
		return api.getUploadSignature(redirectUrl);
	}
	
	private String uploadVideo(JSONObject obj) throws JSONException, VzaarException, Exception {
		String path = "";
		Vzaar api = getVzaar(obj);
		
		path = obj.getString("path");
		
		return api.uploadVideo(path, null);
	}
    
}
