/**
 * vzaar Java API library
 * @version 2.0
 * Skitsanos Inc., 2012.
 * http://skitsanos.com -- info@skitsanos.com -- @skitsanoscom
 */
package com.vzaar;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.RequestExpectContinue;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.type.TypeReference;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

public class Vzaar {
	public String username;
	public String token;
	public boolean enableFlashSupport = false;
	public String apiUrl = "https://vzaar.com/";
	public int bufferSize = 131072; //128Kb

	private OAuthConsumer consumer;

	/**
	 * This constructor creates a new Vzaar instance with the username and token appropriately set
	 *
	 * @param username API application username available at http://vzaar.com/settings/api
	 * @param token    User name
	 */
	public Vzaar(String username, String token) {
		if ((null != username) && (username.length() > 0) && (null != token) && (token.length() > 0)) {
			this.username = username;
			this.token = token;
		}
	}

	/**
	 * this method creates and initializes the OAuth Consumer
	 */
	private void setAuth() {
		consumer = new CommonsHttpOAuthConsumer("", "");
		consumer.setTokenWithSecret(username, token);
	}

	/**
	 * This api tells whoami.
	 *
	 * @return Username of the caller
	 * @throws VzaarException
	 */
	public String whoAmI() throws VzaarException {
		String _url = apiUrl + "api/test/whoami.json";
		String responseBody = getURLResponse(_url);
		String error = checkError(responseBody);
		if (null != error) throw new VzaarException(error);
		if (responseBody.length() > 0) {
			Object parsed = JSONValue.parse(responseBody);
			JSONObject map = (JSONObject) parsed;
			if (map.containsKey("vzaar_api")) {
				JSONObject innerMap = (JSONObject) map.get("vzaar_api");
				if (innerMap.containsKey("test")) {
					JSONObject secondInnermap = (JSONObject) innerMap.get("test");
					if (secondInnermap.containsKey("login")) {
						responseBody = (String) secondInnermap.get("login");
					}
				}
			}
		}
		return responseBody;
	}

	/**
	 * This api gets the user details from the user name
	 *
	 * @param userName It is the vzaar login name for the user. Note: This must be the userName and not the email address
	 * @return Returns object of type {@link com.vzaar.UserDetails}
	 * @throws {@link         VzaarException}
	 * @throws VzaarException
	 */
	public UserDetails getUserDetails(String userName) throws VzaarException {
		if ((null == userName) || (userName.length() == 0)) return null;
		String _url = apiUrl;
		String responseBody = getURLResponse(_url + "api/" + userName + ".json");
		//System.out.println(responseBody);
		String error = checkError(responseBody);
		if (null != error) throw new VzaarException(error);
		return UserDetails.fromJson(responseBody);
	}

	/**
	 * This API call returns the details and rights for each vzaar subscription account type along with it's relevant metadata.
	 * This will show the details of the packages available here: http://vzaar.com/pricing
	 *
	 * @param accountId Integer Account id of the user. It can be retrieved via the getUserDetails() api.
	 * @return Returns object of type {@link com.vzaar.AccountDetails}
	 * @throws {@link         VzaarException}
	 * @throws VzaarException
	 */
	public AccountDetails getAccountDetails(Integer accountId) throws VzaarException {
		String _url = apiUrl + "/api/accounts/" + accountId + ".json";
		String responseBody = getURLResponse(_url);
		String error = checkError(responseBody);
		if (null != error) throw new VzaarException(error);
		System.out.println(responseBody);
		return AccountDetails.fromJson(responseBody);
	}

	/**
	 * This API call returns a list of the user's active videos along with it's relevant metadata.
	 * 20 videos are returned by default but this is customisable.
	 *
	 * @param query
	 * @return {@link java.util.List
	 * @throws {@link VzaarException}
	 */
	public List<Video> getVideoList(VideoListQuery query) throws VzaarException {
		String _url = apiUrl + "api/" + username + "/videos.json?count=" + query.count;

		if ((null != query.labels) && (query.labels.length > 0)) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String label : query.labels) {
				stringBuilder.append(label).append(",");
			}
			_url += "&labels=" + stringBuilder.substring(0, stringBuilder.length() - 1);
		}

		if ((null != query.status) && (query.status.length() > 0))
			_url += "&status=" + query.status;

		if (query.sort == VideoListSorting.ASCENDING)
			_url += "&sort=asc";
		else
			_url += "&sort=desc";

		if ((null != query.title) && (query.title.length() > 0)) {
			try {
				_url += "&title=" + URLEncoder.encode(query.title, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		String responseBody = getURLResponse(_url);
		System.out.println(responseBody);
		return Video.fromJson(new TypeReference<List<Video>>() {
		}, responseBody);
	}

	/**
	 * vzaar uses the oEmbed open standard for allowing 3rd parties to
	 * integrated with the vzaar. You can use the vzaar video URL to easily
	 * obtain the appropriate embed code for that video
	 *
	 * @param videoId It is the vzaar video number for that video available in {@link Video}
	 * @return Returns object of type {@link VideoDetails}
	 * @throws {@link VzaarException}
	 */
	public VideoDetails getVideoDetails(Long videoId) throws VzaarException {
		String _url = apiUrl + "/api/videos/" + videoId + ".json";
		String responseBody = getURLResponse(_url);
		String error = checkError(responseBody);
		if (null != error) throw new VzaarException(error);
		System.out.println(responseBody);
		VideoDetails videoDetails = VideoDetails.fromJson(responseBody);
		videoDetails.poster = "http://view.vzaar.com/" + videoId + "/image";
		return videoDetails;
	}

	/**
	 * Edit video by its id
	 *
	 * @param videoEditQuery
	 * @return Returns the response in string
	 */
	public boolean editVideo(VideoEditQuery videoEditQuery) {
		String _url = apiUrl + "api/videos/" + videoEditQuery.id + ".xml";
		StringBuilder postData = new StringBuilder();
		postData.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		postData.append("<vzaar-api><_method>put</_method>");
		if (null != videoEditQuery.title)
			postData.append("<video><title>").append(videoEditQuery.title).append("</title>");
		if (null != videoEditQuery.description)
			postData.append("<description>").append(videoEditQuery.description).append("</description>");
		postData.append("<private>").append(videoEditQuery.markAsPrivate).append("</private>");
		if (null != videoEditQuery.seoUrl)
			postData.append("<seo_url>").append(videoEditQuery.seoUrl).append("</seo_url>");
		postData.append("</video></vzaar-api>");

		//System.out.println(postData.toString());
		String responseBody = getURLResponse(_url, true, "POST", postData.toString());

		if ((null == responseBody) || (responseBody.length() == 0))
			return false;

		return true;
	}

	/**
	 * Delete video by its id
	 *
	 * @param id It is the vzaar video number for that video available in {@link Video}
	 * @return Returns the response in string
	 */
	public boolean deleteVideo(Long id) {
		String _url = apiUrl + "api/videos/" + id + ".xml";
		StringBuilder postData = new StringBuilder();
		postData.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		postData.append("<vzaar-api><_method>delete</_method></video></vzaar-api>");

		System.out.println(_url + "\n" + postData.toString());
		String responseBody = getURLResponse(_url, true, "DELETE", postData.toString());

		System.out.println(responseBody);

		int videoStatusId = 0;
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(false);
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(responseBody));
		Document document;
		try {
			document = domFactory.newDocumentBuilder().parse(is);
			XPath xpath = XPathFactory.newInstance().newXPath();
			videoStatusId = Integer.parseInt((String) xpath.compile("/oembed/video_status_id/text()").evaluate(document, XPathConstants.STRING));
		} catch (SAXException | IOException | ParserConfigurationException | XPathExpressionException e) {
			e.printStackTrace();
		}

		return VideoStatus.DELETED.value == videoStatusId;
	}


	/**
	 * This API call tells the vzaar system to process a newly uploaded video. This will encode it if necessary and
	 * then provide a vzaar video idea back.
	 * http://developer.vzaar.com/docs/version_1.0/uploading/process
	 *
	 * @param videoProcessQuery
	 * @return Returns the videoId in string
	 */

	public Long processVideo(VideoProcessQuery videoProcessQuery) {
		String _url = apiUrl + "api/videos";
		Long videoId = null;
		StringBuilder postData = new StringBuilder();
		postData.append("<vzaar-api><video>");
		if ((null != videoProcessQuery.replaceId) && (videoProcessQuery.replaceId.length() > 0))
			postData.append("<replace_id>").append(videoProcessQuery.replaceId).append("</replace_id>");
		if ((null != videoProcessQuery.guid) && (videoProcessQuery.guid.length() > 0))
			postData.append("<guid>").append(videoProcessQuery.guid).append("</guid>");
		if (null != videoProcessQuery.title)
			postData.append("<title>").append(videoProcessQuery.title).append("</title>");
		if (null != videoProcessQuery.description)
			postData.append("<description>").append(videoProcessQuery.description).append("</description>");
		if ((null != videoProcessQuery.labels) && (videoProcessQuery.labels.length > 0)) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String label : videoProcessQuery.labels)
				stringBuilder.append(label).append(",");
			postData.append("<labels>")
					.append(stringBuilder.substring(0, stringBuilder.length() - 1))
					.append("</labels>");
		}
		postData.append("<profile>").append(videoProcessQuery.profile).append("</profile>");
		if (videoProcessQuery.transcode)
			postData.append("<transcoding>true</transcoding>");
		postData.append("</video></vzaar-api>");
		//System.out.println(postData.toString());
		String responseBody = getURLResponse(_url, true, "POST", postData.toString());
		//System.out.println(responseBody);
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(false);
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(responseBody));
		Document document;
		try {
			document = domFactory.newDocumentBuilder().parse(is);
			XPath xpath = XPathFactory.newInstance().newXPath();
			videoId = Long.valueOf((String) xpath.compile("/vzaar-api/video/text()").evaluate(document, XPathConstants.STRING));
		} catch (SAXException | IOException | ParserConfigurationException | XPathExpressionException e) {
			e.printStackTrace();
		}

		return videoId;
	}

	/**
	 * Upload subtitle
	 *
	 * @param query SubtitleQuery object
	 * @return boolean upload success
	 * @throws {@link Exception]}
	 */
	public boolean uploadSubtitle(SubtitleQuery query) throws Exception {
		String _url = "http://vzaar.com/api/subtitle/upload.xml";
		StringBuilder postData = new StringBuilder();
		postData.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><vzaar-api><subtitle><language>")
				.append(query.language)
				.append("</language><video_id>")
				.append(query.videoId)
				.append("</video_id><body>")
				.append(query.body)
				.append("</body></subtitle></vzaar-api>");

		System.out.println(postData.toString());
		String responseBody = getURLResponse(_url, true, "POST", postData.toString());
		System.out.println(responseBody);
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(false);
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(responseBody));
		Document document;
		try {
			document = domFactory.newDocumentBuilder().parse(is);
			XPath xpath = XPathFactory.newInstance().newXPath();
			String status = (String) xpath.compile("//status/text()").evaluate(document, XPathConstants.STRING);
			if (status.equalsIgnoreCase("accepted"))
				return true;
		} catch (SAXException | IOException | ParserConfigurationException | XPathExpressionException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Get Upload Signature
	 *
	 * @return Returns object of type {@link UploadSignature}
	 */
	public UploadSignature getUploadSignature() {
		String _url = apiUrl + "api/videos/signature";
		UploadSignature signature = null;
		if (enableFlashSupport) {
			_url += "?flash_request=true";
		}

		String responseBody = getURLResponse(_url);
//		System.out.println(responseBody);
		signature = new UploadSignature(responseBody);
		return signature;
	}

	/**
	 * Get Upload Signature
	 *
	 * @param redirectUrl In case if you are using redirection after your upload, specify redirect URL
	 * @return Returns object of type {@link UploadSignature}
	 */
	public UploadSignature getUploadSignature(String redirectUrl) {
		String _url = apiUrl + "api/videos/signature";
		UploadSignature signature = null;
		if (enableFlashSupport) {
			_url += "?flash_request=true";
		}
		if ((null != redirectUrl) && (redirectUrl.length() > 0)) {
			if (enableFlashSupport) {
				_url += "&success_action_redirect=" + redirectUrl;
			} else {
				_url += "?success_action_redirect=" + redirectUrl;
			}
		}

		String responseBody = getURLResponse(_url);
//		System.out.println(responseBody);
		signature = new UploadSignature(responseBody);
		return signature;
	}

	/**
	 * Upload video from InputStream directly to Amazon S3 bucket
	 *
	 * @param in            InputStream of the file to be uploaded
	 * @param fileName      Name of the file
	 * @param contentLength Content Length
	 * @param listener      Object implementing {@link ProgressListener} interface, to get the upload status
	 * @return string GUID of the file uploaded
	 * @throws {@link Exception]}
	 */


	public String uploadVideo(InputStream in, String fileName, long contentLength, ProgressListener listener) throws Exception {
		UploadSignature signature = getUploadSignature();
		String _url = "https://" + signature.bucket + ".s3.amazonaws.com/";
		String guid = null;
		if (null == consumer)
			setAuth();
		DefaultHttpClient httpClient = new DefaultHttpClient();
		httpClient.removeRequestInterceptorByClass(RequestExpectContinue.class);
		String responseBody = new String();

		HttpPost request = new HttpPost(_url);

		if ((null != in) && (null != fileName) && (1 <= contentLength)) {
			ContentBody body = new FileStreamingBody(in, fileName, contentLength, bufferSize);

			request.addHeader("User-agent", "Vzaar API Client");
			request.addHeader("x-amz-acl", signature.acl);
			request.addHeader("Enclosure-Type", "multipart/form-data");

			CountingMultiPartEntity entity = new CountingMultiPartEntity(listener);

			entity.addPart("AWSAccessKeyId", new StringBody(signature.accessKeyId));
			entity.addPart("Signature", new StringBody(signature.signature));
			entity.addPart("acl", new StringBody(signature.acl));
			entity.addPart("bucket", new StringBody(signature.bucket));
			entity.addPart("policy", new StringBody(signature.policy));
			entity.addPart("success_action_status", new StringBody("201"));
			entity.addPart("key", new StringBody(signature.key));
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

	/**
	 * Upload video from local drive directly to Amazon S3 bucket
	 *
	 * @param path Path of the video file to be uploaded
	 * @return string GUID of the file uploaded
	 * @throws {@link Exception]}
	 */
	public String uploadVideo(String path) throws Exception {
		File file = new File(path);
		if (file.exists()) {
			long contentLength = file.length();
			return uploadVideo(new FileInputStream(file), path, contentLength, null);
		}
		return null;
	}

	public String generateThumbnail(Long videoId, int thumbTime) throws Exception {
		String _url = apiUrl + "api/videos/" + videoId + "/generate_thumb.xml";
		//System.out.println(_url);
		StringBuilder postData = new StringBuilder();
		postData.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
				.append("<vzaar-api>")
				.append("<video>")
				.append("<thumb_time>").append(thumbTime).append("</thumb_time>")
				.append("</video>")
				.append("</vzaar-api>");

		//System.out.println(postData.toString());
		String responseBody = getURLResponse(_url, true, "POST", postData.toString());
		//System.out.println(responseBody);

		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(false);
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(responseBody));
		Document document;
		try {
			document = domFactory.newDocumentBuilder().parse(is);
			XPath xpath = XPathFactory.newInstance().newXPath();
			String status = (String) xpath.compile("//status/text()").evaluate(document, XPathConstants.STRING);
			return status;

		} catch (SAXException | IOException | ParserConfigurationException | XPathExpressionException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param videoId It is the vzaar video number for that video available in {@link Video}
	 * @param path    Path of the video file to be uploaded
	 */
	public String uploadThumbnail(Long videoId, String path) throws Exception {

		String _url = apiUrl + "api/videos/" + videoId + "/upload_thumb.json";

		File file = new File(path);
		String responseBody = new String();

		if (file.exists()) {
			if (consumer == null) setAuth();
			DefaultHttpClient httpClient = new DefaultHttpClient();
			httpClient.removeRequestInterceptorByClass(RequestExpectContinue.class);

			try {
				HttpPost request = new HttpPost(_url);
				FileBody body = new FileBody(file, "image/jpeg");

				request.addHeader("User-agent", "Vzaar API Client");
				//request.addHeader("Connection", "close");
				request.addHeader("Enclosure-Type", "multipart/form-data");
				consumer.sign(request);

				MultipartEntity entity = new MultipartEntity();
				entity.addPart("vzaar-api[thumbnail]", body);
				request.setEntity(entity);

				HttpResponse response = httpClient.execute(request);
				responseBody = EntityUtils.toString(response.getEntity());

				if (responseBody.length() > 0) {
					Object parsed = JSONValue.parse(responseBody);
					JSONObject map = (JSONObject) parsed;
					if (map.containsKey("vzaar-api")) {
						JSONObject innerMap = (JSONObject) map.get("vzaar-api");
						if (innerMap.containsKey("status")) {
							responseBody = (String) innerMap.get("status");
						}
					}
				}

			} catch (IOException | OAuthMessageSignerException | OAuthExpectationFailedException | OAuthCommunicationException e) {
				e.printStackTrace();
			} finally {
				httpClient.getConnectionManager().shutdown();
			}
		}
		return responseBody;
	}

	private String getURLResponse(String url) {
		return getURLResponse(url, true, "GET", null);
	}

	private String getURLResponse(String url, boolean auth, String method, String data) {
		if (null == consumer) setAuth();
		HttpClient httpClient = new DefaultHttpClient();
		String responseBody = "";
		try {
			if (method.equalsIgnoreCase("GET")) {
				HttpGet request = new HttpGet(url);
				if (auth) consumer.sign(request);
				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				responseBody = httpClient.execute(request, responseHandler);
			} else if (method.equalsIgnoreCase("POST")) {
				HttpPost request = new HttpPost(url);
				StringEntity postData = new StringEntity(data);
				request.setEntity(postData);
				request.addHeader("User-agent", "Vzaar OAuth Client");
//				request.addHeader("Connection", "close");
				request.addHeader("Content-Type", "application/xml");
				if (auth) consumer.sign(request);
				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				responseBody = httpClient.execute(request, responseHandler);
			} else if (method.equalsIgnoreCase("DELETE")) {
				HttpDelete request = new HttpDelete(url);
				request.addHeader("User-agent", "Vzaar OAuth Client");
//				request.addHeader("Connection", "close");
				request.addHeader("Content-Type", "application/xml");
				if (auth) consumer.sign(request);
				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				responseBody = httpClient.execute(request, responseHandler);
			}
		} catch (IOException | OAuthMessageSignerException | OAuthExpectationFailedException | OAuthCommunicationException e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return responseBody;

	}

	private String checkError(String responseBody) {
		Object parsed = JSONValue.parse(responseBody);
		JSONObject map = (JSONObject) parsed;
		if (map.containsKey("error")) {
			return (String) map.get("error");
		}
		return null;
	}
}