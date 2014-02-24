
/**
 *  
 * @return Instance of Vzaar
 */
var VzaarPhoneGapPlugin = function() { 

}

/**
 * @param token API token key
 * @param secret Vzaar username
 * @param successCallback The callback which will be called when directory listing is successful
 * @param failureCallback The callback which will be called when directory listing encouters an error
 */
VzaarPhoneGapPlugin.prototype.whoami = function(token, secret, successCallback, failureCallback) {
	
	var jsonStr = '{"token":' + '"' + token + '",' + '"secret":' + '"'+ secret + '"}';	
    return PhoneGap.exec(successCallback,    //Callback which will be called when directory listing is successful
    					failureCallback,     //Callback which will be called when directory listing encounters an error
    					'VzaarPhoneGapPlugin',  //Telling PhoneGap that we want to run "DirectoryListing" Plugin
    					'whoami',              //Telling the plugin, which action we want to perform
    					[JSON.parse(jsonStr)]);        //Passing a list of arguments to the plugin, in this case this is the directory path
};

VzaarPhoneGapPlugin.prototype.getUserDetails = function(token, secret, userName, successCallback, failureCallback) {
	
	var jsonStr = '{"token":' + '"' + token + '",' + '"secret":' + '"'+ secret + '"'
				  +', "username":' + '"' + userName + '"}';
	
    return PhoneGap.exec(successCallback,    //Callback which will be called when directory listing is successful
    					failureCallback,     //Callback which will be called when directory listing encounters an error
    					'VzaarPhoneGapPlugin',  //Telling PhoneGap that we want to run "DirectoryListing" Plugin
    					'getuserdetails',              //Telling the plugin, which action we want to perform
    					[JSON.parse(jsonStr)]);        //Passing a list of arguments to the plugin, in this case this is the directory path
};

VzaarPhoneGapPlugin.prototype.getAccountDetails = function(token, secret, accountid, successCallback, failureCallback) {
	
	var jsonStr = '{"token":' + '"' + token + '",' + '"secret":' + '"'+ secret + '"'
				  +', "account":' + '"' + accountid + '"}';
	
    return PhoneGap.exec(successCallback,    //Callback which will be called when directory listing is successful
    					failureCallback,     //Callback which will be called when directory listing encounters an error
    					'VzaarPhoneGapPlugin',  //Telling PhoneGap that we want to run "DirectoryListing" Plugin
    					'getaccountdetails',              //Telling the plugin, which action we want to perform
    					[JSON.parse(jsonStr)]);        //Passing a list of arguments to the plugin, in this case this is the directory path
};

VzaarPhoneGapPlugin.prototype.getVideoList = function(token, secret, userName, auth, count, labels, status, successCallback, failureCallback) {
	
	var jsonStr = '{"token":' + '"' + token + '",' + '"secret":' + '"'+ secret + '"'
				  +', "username":' + '"' + userName + '"'
				  +', "auth":' + '"' + auth + '"'
				  +', "count":' + '"' + count + '"'
				  +', "labels":' + '"' + labels + '"'
				  +', "status":' + '"' + status + '"}';
	
    return PhoneGap.exec(successCallback,    //Callback which will be called when directory listing is successful
    					failureCallback,     //Callback which will be called when directory listing encounters an error
    					'VzaarPhoneGapPlugin',  //Telling PhoneGap that we want to run "DirectoryListing" Plugin
    					'getvideolist',              //Telling the plugin, which action we want to perform
    					[JSON.parse(jsonStr)]);        //Passing a list of arguments to the plugin, in this case this is the directory path
};

VzaarPhoneGapPlugin.prototype.searchVideoList = function(token, secret, username, auth , title , labels , count , page , sort
													  , successCallback, failureCallback) {
	
	var jsonStr = '{"token":' + '"' + token + '",' + '"secret":' + '"'+ secret + '"'
				  +', "username":' + '"' + userName + '"'
				  +', "auth":' + '"' + auth + '"'
				  +', "title":' + '"' + title + '"'
				  +', "labels":' + '"' + labels + '"'
				  +', "count":' + '"' + count + '"'
				  +', "page":' + '"' + page + '"'				  
				  +', "sort":' + '"' + sort + '"}';
	
    return PhoneGap.exec(successCallback,    //Callback which will be called when directory listing is successful
    					failureCallback,     //Callback which will be called when directory listing encounters an error
    					'VzaarPhoneGapPlugin',  //Telling PhoneGap that we want to run "DirectoryListing" Plugin
    					'searchvideolist',              //Telling the plugin, which action we want to perform
    					[JSON.parse(jsonStr)]);        //Passing a list of arguments to the plugin, in this case this is the directory path
};

VzaarPhoneGapPlugin.prototype.getVideoDetails = function(token, secret, videoid, auth, successCallback, failureCallback) {
	
	var jsonStr = '{"token":' + '"' + token + '",' + '"secret":' + '"'+ secret + '"'
				  +', "videoid":' + '"' + videoid + '"'
				  +', "auth":' + '"' + auth + '"}';
	
    return PhoneGap.exec(successCallback,    //Callback which will be called when directory listing is successful
    					failureCallback,     //Callback which will be called when directory listing encounters an error
    					'VzaarPhoneGapPlugin',  //Telling PhoneGap that we want to run "DirectoryListing" Plugin
    					'getvideodetails',              //Telling the plugin, which action we want to perform
    					[JSON.parse(jsonStr)]);        //Passing a list of arguments to the plugin, in this case this is the directory path
};

VzaarPhoneGapPlugin.prototype.editVideo = function(token, secret, videoid, title, description, isPrivate, seoUrl
	  , successCallback, failureCallback) {

	var jsonStr = '{"token":' + '"' + token + '",' + '"secret":' + '"'+ secret + '"'
				+', "videoid":' + '"' + videoid + '"'				
				+', "title":' + '"' + title + '"'
				+', "description":' + '"' + description + '"'
				+', "isprivate":' + '"' + isPrivate + '"'						  
				+', "seourl":' + '"' + seoUrl + '"}';
	
	return PhoneGap.exec(successCallback,    //Callback which will be called when directory listing is successful
						failureCallback,     //Callback which will be called when directory listing encounters an error
						'VzaarPhoneGapPlugin',  //Telling PhoneGap that we want to run "DirectoryListing" Plugin
						'editvideo',              //Telling the plugin, which action we want to perform
						[JSON.parse(jsonStr)]);        //Passing a list of arguments to the plugin, in this case this is the directory path
};

VzaarPhoneGapPlugin.prototype.deleteVideo = function(token, secret, videoid, successCallback, failureCallback) {

	var jsonStr = '{"token":' + '"' + token + '",' + '"secret":' + '"'+ secret + '"'
				+', "videoid":' + '"' + videoid + '"}';
	
	return PhoneGap.exec(successCallback,    //Callback which will be called when directory listing is successful
						failureCallback,     //Callback which will be called when directory listing encounters an error
						'VzaarPhoneGapPlugin',  //Telling PhoneGap that we want to run "DirectoryListing" Plugin
						'deletevideo',              //Telling the plugin, which action we want to perform
						[JSON.parse(jsonStr)]);        //Passing a list of arguments to the plugin, in this case this is the directory path
};

VzaarPhoneGapPlugin.prototype.processVideo = function(token, secret, guid, title, description, labels, profile, transcoding , replace 
	  , successCallback, failureCallback) {

	var jsonStr = '{"token":' + '"' + token + '",' + '"secret":' + '"'+ secret + '"'
				+', "guid":' + '"' + guid + '"'				
				+', "title":' + '"' + title + '"'
				+', "description":' + '"' + description + '"'
				+', "labels":' + '"' + labels + '"'
				+', "profile":' + '"' + profile + '"'
				+', "transcoding":' + '"' + transcoding + '"'
				+', "replace":' + '"' + replace + '"}';
	
	return PhoneGap.exec(successCallback,    //Callback which will be called when directory listing is successful
						failureCallback,     //Callback which will be called when directory listing encounters an error
						'VzaarPhoneGapPlugin',  //Telling PhoneGap that we want to run "DirectoryListing" Plugin
						'processvideo',              //Telling the plugin, which action we want to perform
						[JSON.parse(jsonStr)]);        //Passing a list of arguments to the plugin, in this case this is the directory path
};

VzaarPhoneGapPlugin.prototype.getUploadSignature = function(token, secret, redirecturl, successCallback, failureCallback) {

	var jsonStr = '{"token":' + '"' + token + '",' + '"secret":' + '"'+ secret + '"'
				+', "redirecturl":' + '"' + redirecturl + '"}';
	
	return PhoneGap.exec(successCallback,    //Callback which will be called when directory listing is successful
						failureCallback,     //Callback which will be called when directory listing encounters an error
						'VzaarPhoneGapPlugin',  //Telling PhoneGap that we want to run "DirectoryListing" Plugin
						'getuploadsignature',              //Telling the plugin, which action we want to perform
						[JSON.parse(jsonStr)]);        //Passing a list of arguments to the plugin, in this case this is the directory path
};

VzaarPhoneGapPlugin.prototype.uploadVideo = function(token, secret, filepath, successCallback, failureCallback) {

	var jsonStr = '{"token":' + '"' + token + '",' + '"secret":' + '"'+ secret + '"'
				+', "path":' + '"' + filepath + '"}';
	
	return PhoneGap.exec(successCallback,    //Callback which will be called when directory listing is successful
						failureCallback,     //Callback which will be called when directory listing encounters an error
						'VzaarPhoneGapPlugin',  //Telling PhoneGap that we want to run "DirectoryListing" Plugin
						'uploadvideo',              //Telling the plugin, which action we want to perform
						[JSON.parse(jsonStr)]);        //Passing a list of arguments to the plugin, in this case this is the directory path
};

PhoneGap.addConstructor(function() {
	//Register the javascript plugin with PhoneGap
	PhoneGap.addPlugin('vzaar', new VzaarPhoneGapPlugin());
	
	//Register the native class of plugin with PhoneGap
	PluginManager.addService("VzaarPhoneGapPlugin","com.vzaar.phonegap.VzaarPhoneGapPlugin");
});