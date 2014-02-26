/**
 * vzaar Java API library
 * @version 2.0
 * Skitsanos Inc., 2012.
 * http://skitsanos.com -- info@skitsanos.com -- @skitsanoscom
 */
package com.vzaar;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.ContentEncodingHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.RequestExpectContinue;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Vzaar
{

	private String _token;
	private String _secret;

	public static final boolean enableFlashSupport = false;

	private OAuthConsumer consumer;

	public static final String URL_LIVE = "http://vzaar.com/";
	public static final String AMAZON_S3_URL = ".s3.amazonaws.com/";

	public String token()
	{
		return _token;
	}

	public void token(String _token)
	{
		this._token = _token;
	}

	public String secret()
	{
		return _secret;
	}

	public void secret(String _secret)
	{
		this._secret = _secret;
	}

	/**
	 * This constructor creates a new Vzaar instance with the token and secret appropriately set
	 *
	 * @param token  API application token available at http://vzaar.com/settings/api
	 * @param secret User name
	 */
	public Vzaar(String token, String secret)
	{
		if ((null != token) && (token.length() > 0) && (null != secret) && (secret.length() > 0))
		{
			this._token = token;
			this._secret = secret;
		}
	}

	/**
	 * this method creates and initializes the OAuth Consumer
	 */
	private void setAuth()
	{
		consumer = new CommonsHttpOAuthConsumer("", "");
		consumer.setTokenWithSecret(_secret, _token);
	}

	/**
	 * This api tells whoami.
	 *
	 * @return Username of the caller
	 * @throws VzaarException
	 */
	public String whoAmI() throws VzaarException
	{
		String _url = Vzaar.URL_LIVE + "api/test/whoami.json";
		if (null == consumer) setAuth();
		String responseBody = getURLResponse(_url, true);
		String error = checkError(responseBody);
		if (null != error) throw new VzaarException(error);
		if (responseBody.length() > 0)
		{
			Object parsed = JSONValue.parse(responseBody);
			JSONObject map = (JSONObject) parsed;
			if (map.containsKey("vzaar_api"))
			{
				JSONObject innerMap = (JSONObject) map.get("vzaar_api");
				if (innerMap.containsKey("test"))
				{
					JSONObject secondInnermap = (JSONObject) innerMap.get("test");
					if (secondInnermap.containsKey("login"))
					{
						responseBody = (String) secondInnermap.get("login");
					}
				}
			}
		}
		return responseBody;
	}

	/**
	 * This api gets the account details from the account id.
	 *
	 * @param account Integer Account id of the user. It can be retrieved via the getUserDetails() api.
	 * @return Returns object of type {@link AccountsType}
	 * @throws {@link		 VzaarException}
	 * @throws VzaarException
	 */
	public AccountsType getAccountDetails(Integer account) throws VzaarException
	{
		String _url = Vzaar.URL_LIVE;
		String responseBody = getURLResponse(_url + "api/accounts/" + account + ".json", false);
		String error = checkError(responseBody);
		if (null != error) throw new VzaarException(error);
		//System.out.println(responseBody);
		return AccountsType.fromJson(responseBody);
	}

	/**
	 * This api gets the user details from the user name
	 *
	 * @param userName It is the vzaar login name for the user. Note: This must be the userName and not the email address
	 * @return Returns object of type {@link User}
	 * @throws {@link		 VzaarException}
	 * @throws VzaarException
	 */
	public User getUserDetails(String userName) throws VzaarException
	{
		if ((null == userName) || (userName.length() == 0)) return null;
		String _url = Vzaar.URL_LIVE;
		String responseBody = getURLResponse(_url + "api/" + userName + ".json", false);
		//System.out.println(responseBody);
		String error = checkError(responseBody);
		if (null != error) throw new VzaarException(error);

		return User.fromJson(responseBody);
	}


	/**
	 * This API call returns a list of the user's active videos along with it's
	 * relevant metadata
	 * http://vzaar.com/api/vzaar/videos.xml?title=vzaar
	 *
	 * @param userName It is the vzaar login name for the user. Note: This must be the userName and not the email address
	 * @param auth	 Use authenticated request if true
	 * @param count	Specifies the number of videos to retrieve per page. Default is 20. Maximum is 100
	 * @param labels   Labels to be queried
	 * @param status   Status to be queried
	 * @return Returns object of type {@link VideoList}
	 * @throws {@link		 VzaarException}
	 * @throws VzaarException
	 */

	public VideoList getVideoList(String userName, boolean auth, int count, String labels, String status) throws VzaarException
	{
		if (null == userName) return null;

		String _url = Vzaar.URL_LIVE + "api/" + userName + "/videos.json?count=" + count;
		if ((null != labels) && (labels.length() > 0)) _url += "&labels=" + labels;
		if ((null != status) && (status.length() > 0)) _url += "&status=" + status;

		String responseBody = getURLResponse(_url, auth);
		return VideoList.fromJson(responseBody);
	}

	/**
	 * This API call returns a list of the user's active videos along with it's
	 * relevant metadata
	 * http://vzaar.com/api/vzaar/videos.xml?title=vzaar
	 *
	 * @param userName It is the vzaar login name for the user. Note: This must be the userName and not the email address
	 * @param auth	 Use authenticated request if true
	 * @param count	Specifies the number of videos to retrieve per page. Default is 20. Maximum is 100
	 * @param labels   Labels to be queried
	 * @return Returns object of type {@link VideoList}
	 * @throws {@link		 VzaarException}
	 * @throws VzaarException
	 */
	public VideoList getVideoList(String userName, boolean auth, int count, String labels) throws VzaarException
	{
		return getVideoList(userName, auth, count, labels, "");
	}

	/**
	 * This API call returns a list of the user's active videos along with it's
	 * relevant metadata
	 * http://vzaar.com/api/vzaar/videos.xml?title=vzaar
	 *
	 * @param userName It is the vzaar login name for the user. Note: This must be the userName and not the email address
	 * @param auth	 Use authenticated request if true
	 * @param count	Specifies the number of videos to retrieve per page. Default is 20. Maximum is 100
	 * @return Returns object of type {@link VideoList}
	 * @throws {@link		 VzaarException}
	 * @throws VzaarException
	 */
	public VideoList getVideoList(String userName, boolean auth, int count) throws VzaarException
	{
		return getVideoList(userName, auth, count, "");
	}

	/**
	 * This API call returns a list of the user's active videos along with it's
	 * relevant metadata
	 * http://vzaar.com/api/vzaar/videos.xml?title=vzaar
	 *
	 * @param userName It is the vzaar login name for the user. Note: This must be the userName and not the email address
	 * @param auth	 Use authenticated request if true
	 * @return Returns object of type {@link VideoList}
	 * @throws {@link		 VzaarException}
	 * @throws VzaarException
	 */
	public VideoList getVideoList(String userName, boolean auth) throws VzaarException
	{
		return getVideoList(userName, auth, 20, "");
	}

	/**
	 * This API call returns a list of the user's active videos along with it's
	 * relevant metadata
	 * http://vzaar.com/api/vzaar/videos.xml?title=vzaar
	 *
	 * @param userName It is the vzaar login name for the user. Note: This must be the userName and not the email address
	 * @return Returns object of type {@link VideoList}
	 * @throws {@link		 VzaarException}
	 * @throws VzaarException
	 */
	public VideoList getVideoList(String userName) throws VzaarException
	{
		return getVideoList(userName, false);
	}

	/**
	 * This API call returns a list of the user's active videos along with it's relevant metadata
	 *
	 * @param username It is the vzaar login name for the user. Note: This must be the userName and not the email address
	 * @param auth	 Use authenticated request if true
	 * @param title	Title name of the video to be queried
	 * @param labels   Labels to be queried
	 * @param count	Specifies the number of videos to retrieve per page. Default is 20. Maximum is 100
	 * @param page	 Specifies the page number to retrieve. Default is 1
	 * @param sort	 Values can be asc (least_recent) or desc (most_recent). Defaults to desc
	 * @return Returns object of type {@link VideoList}
	 * @throws {@link		 VzaarException}
	 * @throws VzaarException
	 */

	public VideoList searchVideoList(String username, boolean auth, String title, String labels, int count, int page, String sort) throws VzaarException
	{
		if (null == username) return null;
		if ((!sort.equalsIgnoreCase("asc")) && (!sort.equalsIgnoreCase("desc")))
		{
			sort = "desc";
		}
		String _url = Vzaar.URL_LIVE + "api/" + username + "/videos.json?count=" + count + "&page=" + page + "&sort=" + sort;
		if ((null != labels) && (labels.length() > 0)) _url += "&labels=" + labels;
		if ((null != title) && (title.length() > 0))
		{
			try
			{
				_url += "&title=" + URLEncoder.encode(title, "UTF-8");
			} catch (UnsupportedEncodingException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String responseBody = getURLResponse(_url, auth);
//		System.out.println(responseBody);		
		return VideoList.fromJson(responseBody);
	}

	/**
	 * This API call returns a list of the user's active videos along with it's relevant metadata
	 *
	 * @param username It is the vzaar login name for the user. Note: This must be the userName and not the email address
	 * @param auth	 Use authenticated request if true
	 * @param title	Title name of the video to be queried
	 * @param labels   Labels to be queried
	 * @param count	Specifies the number of videos to retrieve per page. Default is 20. Maximum is 100
	 * @param page	 Specifies the page number to retrieve. Default is 1
	 * @return Returns object of type {@link VideoList}
	 * @throws {@link VzaarException}
	 */
	public VideoList searchVideoList(String username, boolean auth, String title, String labels, int count, int page) throws VzaarException
	{
		return searchVideoList(username, auth, title, labels, count, page, "desc");
	}

	/**
	 * This API call returns a list of the user's active videos along with it's relevant metadata
	 *
	 * @param username It is the vzaar login name for the user. Note: This must be the userName and not the email address
	 * @param auth	 Use authenticated request if true
	 * @param title	Title name of the video to be queried
	 * @param labels   Labels to be queried
	 * @param count	Specifies the number of videos to retrieve per page. Default is 20. Maximum is 100
	 * @return Returns object of type {@link VideoList}
	 * @throws {@link VzaarException}
	 */
	public VideoList searchVideoList(String username, boolean auth, String title, String labels, int count) throws VzaarException
	{
		return searchVideoList(username, auth, title, labels, count, 1);
	}

	/**
	 * This API call returns a list of the user's active videos along with it's relevant metadata
	 *
	 * @param username It is the vzaar login name for the user. Note: This must be the userName and not the email address
	 * @param auth	 Use authenticated request if true
	 * @param title	Title name of the video to be queried
	 * @param labels   Labels to be queried
	 * @return Returns object of type {@link VideoList}
	 * @throws {@link VzaarException}
	 */
	public VideoList searchVideoList(String username, boolean auth, String title, String labels) throws VzaarException
	{
		return searchVideoList(username, auth, title, labels, 20);
	}

	/**
	 * This API call returns a list of the user's active videos along with it's relevant metadata
	 *
	 * @param username It is the vzaar login name for the user. Note: This must be the userName and not the email address
	 * @param auth	 Use authenticated request if true
	 * @param title	Title name of the video to be queried
	 * @return Returns object of type {@link VideoList}
	 * @throws {@link VzaarException}
	 */
	public VideoList searchVideoList(String username, boolean auth, String title) throws VzaarException
	{
		return searchVideoList(username, auth, title, "");
	}

	/**
	 * This API call returns a list of the user's active videos along with it's relevant metadata
	 *
	 * @param username It is the vzaar login name for the user. Note: This must be the userName and not the email address
	 * @param auth	 Use authenticated request if true
	 * @return Returns object of type {@link VideoList}
	 * @throws {@link VzaarException}
	 */
	public VideoList searchVideoList(String username, boolean auth) throws VzaarException
	{
		return searchVideoList(username, auth, "");
	}

	/**
	 * This API call returns a list of the user's active videos along with it's relevant metadata
	 *
	 * @param username It is the vzaar login name for the user. Note: This must be the userName and not the email address
	 * @return Returns object of type {@link VideoList}
	 * @throws {@link VzaarException}
	 */
	public VideoList searchVideoList(String username) throws VzaarException
	{
		return searchVideoList(username, false);
	}

	/**
	 * vzaar uses the oEmbed open standard for allowing 3rd parties to
	 * integrated with the vzaar. You can use the vzaar video URL to easily
	 * obtain the appropriate embed code for that video
	 *
	 * @param id   It is the vzaar video number for that video available in {@link Video}
	 * @param auth Use authenticated request if true
	 * @return Returns object of type {@link VideoDetails}
	 * @throws {@link VzaarException}
	 */
	public VideoDetails getVideoDetails(BigInteger id, boolean auth) throws VzaarException
	{
		String _url = Vzaar.URL_LIVE + "api/videos/" + id + ".json";
		String responseBody = getURLResponse(_url, auth);
		//System.out.println(responseBody);
		return VideoDetails.fromJson(responseBody);
	}

	/**
	 * vzaar uses the oEmbed open standard for allowing 3rd parties to
	 * integrated with the vzaar. You can use the vzaar video URL to easily
	 * obtain the appropriate embed code for that video
	 *
	 * @param id It is the vzaar video number for that video available in {@link Video}
	 * @return Returns object of type {@link VideoDetails}
	 * @throws {@link VzaarException}
	 */
	public VideoDetails getVideoDetails(BigInteger id) throws VzaarException
	{
		return getVideoDetails(id, false);
	}

	/**
	 * Edit video by its id
	 *
	 * @param id		  It is the vzaar video number for that video available in {@link Video}
	 * @param title	   This is the title of the video
	 * @param description This is the description of the video
	 * @param isPrivate
	 * @param seoUrl
	 * @return Returns the response in string
	 */
	public String editVideo(BigInteger id, String title, String description, boolean isPrivate, String seoUrl)
	{
		String _url = Vzaar.URL_LIVE + "api/videos/" + id + ".xml";
		StringBuilder postData = new StringBuilder();
		postData.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		postData.append("<vzaar-api><_method>put</_method>");
		if (null != title) postData.append("<video><title>").append(title).append("</title>");
		if (null != description) postData.append("<description>").append(description).append("</description>");
		postData.append("<private>").append(isPrivate).append("</private>");
		if (null != seoUrl) postData.append("<seo_url>").append(seoUrl).append("</seo_url>");
		postData.append("</video></vzaar-api>");

		//System.out.println(postData.toString());
		return getURLResponse(_url, true, "POST", postData.toString());
	}

	/**
	 * Edit video by its id
	 *
	 * @param id		  It is the vzaar video number for that video available in {@link Video}
	 * @param title	   This is the title of the video
	 * @param description This is the description of the video
	 * @param isPrivate
	 * @return Returns the response in string
	 */
	public String editVideo(BigInteger id, String title, String description, boolean isPrivate)
	{
		return editVideo(id, title, description, isPrivate, "");
	}

	/**
	 * Edit video by its id
	 *
	 * @param id		  It is the vzaar video number for that video available in {@link Video}
	 * @param title	   This is the title of the video
	 * @param description This is the description of the video
	 * @return Returns the response in string
	 */
	public String editVideo(BigInteger id, String title, String description)
	{
		return editVideo(id, title, description, false, "");
	}

	/**
	 * Delete video by its id
	 *
	 * @param id It is the vzaar video number for that video available in {@link Video}
	 * @return Returns the response in string
	 */
	public String deleteVideo(BigInteger id)
	{
		String _url = Vzaar.URL_LIVE + "api/videos/" + id + ".xml";
		StringBuilder postData = new StringBuilder();
		postData.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		postData.append("<vzaar-api><_method>delete</_method></video></vzaar-api>");

		System.out.println(_url + "\n" + postData.toString());
		return getURLResponse(_url, true, "DELETE", postData.toString());
	}


	/**
	 * This API call tells the vzaar system to process a newly uploaded video. This will encode it if necessary and
	 * then provide a vzaar video idea back.
	 * http://developer.vzaar.com/docs/version_1.0/uploading/process
	 *
	 * @param guid		Specifies the guid (returned after calling {@link #uploadVideo(String, ProgressListener)} to operate on
	 * @param title	   Specifies the tile of the video
	 * @param description Specifies the description for the video
	 * @param labels
	 * @param profile	 Specifies the size for the video to be encoded in. If not specified, this will use the vzaar default
	 * @param transcoding If True forces vzaar to transcode the video, false makes vzaar use the original source file (available only for mp4 and flv files)
	 * @param replace	 Specifies the video ID of an existing video that you wish to replace with the new video.
	 * @return Returns the videoId in string
	 */

	public String processVideo(String guid, String title, String description, String labels, int profile, boolean transcoding, String replace)
	{
		String _url = Vzaar.URL_LIVE + "api/videos";
		String videoId = null;
		StringBuilder postData = new StringBuilder();
		postData.append("<vzaar-api><video>");
		if ((null != replace) && (replace.length() > 0))
			postData.append("<replace_id>").append(replace).append("</replace_id>");
		postData.append("<guid>").append(guid).append("</guid>");
		if (null != title) postData.append("<title>").append(title).append("</title>");
		if (null != description) postData.append("<description>").append(description).append("</description>");
		if (null != labels) postData.append("<labels>").append(labels).append("</labels>");
		postData.append("<profile>").append(profile).append("</profile>");
		if (transcoding) postData.append("<transcoding>true</transcoding>");
		postData.append("</video></vzaar-api>");
		//System.out.println(postData.toString());
		String responseBody = getURLResponse(_url, true, "POST", postData.toString());
		//System.out.println(responseBody);
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(false);
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(responseBody));
		Document document;
		try
		{
			document = domFactory.newDocumentBuilder().parse(is);
			XPath xpath = XPathFactory.newInstance().newXPath();
			videoId = (String) xpath.compile("/vzaar-api/video/text()").evaluate(document, XPathConstants.STRING);
		} catch (SAXException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XPathExpressionException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return videoId;
	}

	/**
	 * This API call tells the vzaar system to process a newly uploaded video. This will encode it if necessary and
	 * then provide a vzaar video idea back.
	 * http://developer.vzaar.com/docs/version_1.0/uploading/process
	 *
	 * @param guid		Specifies the guid (returned after calling {@link #uploadVideo(String, ProgressListener)} to operate on
	 * @param title	   Specifies the tile of the video
	 * @param description Specifies the description for the video
	 * @param labels
	 * @param profile	 Specifies the size for the video to be encoded in. If not specified, this will use the vzaar default
	 * @param transcoding If True forces vzaar to transcode the video, false makes vzaar use the original source file (available only for mp4 and flv files)
	 * @return Returns the videoId in string
	 */
	public String processVideo(String guid, String title, String description, String labels, int profile, boolean transcoding)
	{
		return processVideo(guid, title, description, labels, profile, transcoding, "");
	}

	/**
	 * This API call tells the vzaar system to process a newly uploaded video. This will encode it if necessary and
	 * then provide a vzaar video idea back.
	 * http://developer.vzaar.com/docs/version_1.0/uploading/process
	 *
	 * @param guid		Specifies the guid (returned after calling {@link #uploadVideo(String, ProgressListener)} to operate on
	 * @param title	   Specifies the tile of the video
	 * @param description Specifies the description for the video
	 * @param labels
	 * @param profile	 Specifies the size for the video to be encoded in. If not specified, this will use the vzaar default
	 * @return Returns the videoId in string
	 */

	public String processVideo(String guid, String title, String description, String labels, int profile)
	{
		return processVideo(guid, title, description, labels, profile, false);
	}

	/**
	 * This API call tells the vzaar system to process a newly uploaded video. This will encode it if necessary and
	 * then provide a vzaar video idea back.
	 * http://developer.vzaar.com/docs/version_1.0/uploading/process
	 *
	 * @param guid		Specifies the guid (returned after calling {@link #uploadVideo(String, ProgressListener)} to operate on
	 * @param title	   Specifies the tile of the video
	 * @param description Specifies the description for the video
	 * @param labels
	 * @return Returns the videoId in string
	 */
	public String processVideo(String guid, String title, String description, String labels)
	{
		return processVideo(guid, title, description, labels, Profile.Medium);
	}

	/**
	 * Get Upload Signature
	 *
	 * @param redirectUrl In case if you are using redirection after your upload, specify redirect URL
	 * @return Returns object of type {@link UploadSignature}
	 */
	public UploadSignature getUploadSignature(String redirectUrl)
	{
		String _url = Vzaar.URL_LIVE + "api/videos/signature";
		UploadSignature signature = null;
		if (Vzaar.enableFlashSupport)
		{
			_url += "?flash_request=true";
		}
		if ((null != redirectUrl) && (redirectUrl.length() > 0))
		{
			if (Vzaar.enableFlashSupport)
			{
				_url += "&success_action_redirect=" + redirectUrl;
			} else
			{
				_url += "?success_action_redirect=" + redirectUrl;
			}
		}

		String responseBody = getURLResponse(_url, true);
//		System.out.println(responseBody);
		signature = UploadSignature.fromXml(responseBody);
		return signature;
	}

	/**
     * Upload video from InputStream directly to Amazon S3 bucket
     *
     * @param in     InputStream of the file to be uploaded
     * @param fileName Name of the file
     * @param contentLength Content Length
     * @param listener Object implementing {@link ProgressListener} interface, to get the upload status
     * @return string GUID of the file uploaded
     * @throws {@link Exception]}
     */
    public String uploadVideo(InputStream in, String fileName, long contentLength, ProgressListener listener) throws Exception {
        UploadSignature signature = getUploadSignature(null);
        String _url = "https://" + signature.bucket() + ".s3.amazonaws.com/";
        String guid = null;
        if (null == consumer)
            setAuth();
        DefaultHttpClient httpClient = new DefaultHttpClient();        
        httpClient.removeRequestInterceptorByClass( RequestExpectContinue.class );
        String responseBody = new String();

        HttpPost request = new HttpPost(_url);

        if ((null != in) && (null != fileName) && (1 <= contentLength))  {
            ContentBody body = new FileStreamingBody(in, fileName, contentLength);

            request.addHeader("User-agent", "Vzaar API Client");
            request.addHeader("x-amz-acl", signature.acl());
            request.addHeader("Enclosure-Type", "multipart/form-data");

            CountingMultiPartEntity entity = new CountingMultiPartEntity(listener);
            entity.addPart("AWSAccessKeyId", new StringBody(signature.accesskeyid()));
            entity.addPart("Signature", new StringBody(signature.signature()));
            entity.addPart("acl", new StringBody(signature.acl()));
            entity.addPart("bucket", new StringBody(signature.bucket()));
            entity.addPart("policy", new StringBody(signature.policy()));
            entity.addPart("success_action_status", new StringBody("201"));
            entity.addPart("key", new StringBody(signature.key()));
            entity.addPart("file", body);

            request.setEntity(entity);
            
            HttpResponse response = httpClient.execute(request);
            responseBody = EntityUtils.toString(response.getEntity());
            //System.out.println(responseBody);

            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setNamespaceAware(false);
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(responseBody));
            Document document = domFactory.newDocumentBuilder().parse(is);

            XPath xpath = XPathFactory.newInstance().newXPath();
            String postResponseKey = (String) xpath.compile("/PostResponse/Key/text()").evaluate(document, XPathConstants.STRING);
            if (postResponseKey.length() > 0) {
                String[] exploded = postResponseKey.split("/");
                guid = exploded[exploded.length - 2];
                //System.out.println("Parsed GUID - " + guid);
            } else {
                throw new VzaarException(responseBody);
            }
        }
        return guid;
    }

    /**
     * Upload video from local drive directly to Amazon S3 bucket
     *
     * @param path     Path of the video file to be uploaded
     * @param listener Object implementing {@link ProgressListener} interface, to get the upload status
     * @return string GUID of the file uploaded
     * @throws {@link Exception]}
     */
    public String uploadVideo(String path, ProgressListener listener) throws Exception {
        File file = new File(path);
        if (file.exists()) {
            long contentLength = file.length();
            return uploadVideo(new FileInputStream(file), path, contentLength, listener);
        }
        return null;
    }


	private String getURLResponse(String url, boolean auth)
	{
		return getURLResponse(url, auth, "GET", null);
	}

	private String getURLResponse(String url, boolean auth, String method, String data)
	{
		if (null == consumer) setAuth();
		HttpClient httpClient = new DefaultHttpClient();
		String responseBody = "";
		try
		{
			if (method.equalsIgnoreCase("GET"))
			{
				HttpGet request = new HttpGet(url);
				if (auth) consumer.sign(request);
				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				responseBody = httpClient.execute(request, responseHandler);
			} else if (method.equalsIgnoreCase("POST"))
			{
				HttpPost request = new HttpPost(url);
				StringEntity postData = new StringEntity(data);
				request.setEntity(postData);
				request.addHeader("User-agent", "Vzaar OAuth Client");
				request.addHeader("Connection", "close");
				request.addHeader("Content-Type", "application/xml");
				if (auth) consumer.sign(request);
				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				responseBody = httpClient.execute(request, responseHandler);
			} else if (method.equalsIgnoreCase("DELETE"))
			{
				HttpDelete request = new HttpDelete(url);
				request.addHeader("User-agent", "Vzaar OAuth Client");
				request.addHeader("Connection", "close");
				request.addHeader("Content-Type", "application/xml");
				if (auth) consumer.sign(request);
				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				responseBody = httpClient.execute(request, responseHandler);
			}
		} catch (OAuthMessageSignerException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthExpectationFailedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthCommunicationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally
		{
			httpClient.getConnectionManager().shutdown();
		}
		return responseBody;

	}

	private String checkError(String responseBody)
	{
		Object parsed = JSONValue.parse(responseBody);
		JSONObject map = (JSONObject) parsed;
		if (map.containsKey("error"))
		{
			return (String) map.get("error");
		}
		return null;
	}
}