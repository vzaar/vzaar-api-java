vzaar API Java client
---
Lighter and faster vzaar API client for Java developers. Documentation provided below is valid for the library version 2.0 and above.

---

>vzaar is the go to video hosting platform for business. Affordable, customizable and secure. Leverage the power of online video and enable commerce with vzaar.

----

###Using the library


To start, make sure you have included vzaar-java-api.jar into your Project References.

```java
import com.vzaar.Vzaar;
```

Now you can create your vzaar client instance:

```java
Vzaar api = new Vzaar("VZAAR_USERNAME", "VZAAR_TOKEN");
```

In order to use vzaar API, you need to have a valid user name and API token that you can get from your vzaar dashboard at [http://vzaar.com/settings/third_party](http://vzaar.com/settings/third_party).

The very next thing you would want to do is to check if your account actually works and operational and you can do it by simple calling _whoAmI()_:

```java
    Vzaar api = new Vzaar(args[0], args[1]);
    String whoAmI = vzaarApi.whoAmI();
    if (whoAmI.length() != 0) 
    {
      System.out.println("WhoAmI - " + whoAmI + "\n");
    }
    else
    {
      System.out.println("Error\n");
    }
```

If it returns you your vzaar username, - we are good to go.

####User Details

>This API call returns the user's public details along with it's relevant metadata. It also contains vzaar Account ID that you can use in _api.getAccountDetails_ call.

```java
UserDetails details = api.getUserDetails(VZAAR_USERNAME);
```

Where _VZAAR_USERNAME_ is the vzaar username. Result of this call will be an object of UserDetails type.

####Account Details

>This API call returns the details and rights for each vzaar subscription account type along with it's relevant metadata. This will show the details of the packages available here: [http://vzaar.com/pricing](http://vzaar.com/pricing)

```java
AccountDetails details = api.getAccountDetails(VZAAR_ACCOUNT_ID);
```

Where _VZAAR_ACCOUNT_ID_ is the unique account id assigned by vzaar.

Result of this call will be an object of AccountDetails type.

####Video List

>This API call returns a list of the user's active videos along with it's relevant metadata. 20 videos are returned by default but this is customizable.

```java
VideoListQuery query = new VideoListQuery
{
	count = 10,
	page = 1
};

List<Video> list = api.getVideoList(query);
```

####Video Details

>This API call returns metadata about selected video, like its dimensions, thumbnail information, author, duration, play count and so on.

```java
api.getVideoDetails(VZAAR_VIDEO_ID);
```

Where _VZAAR_VIDEO_ID_ is unique vzaar video ID assigned to a video after its processing.

####Upload Signature

>In some cases you might need to not perform actual uploading from API but to use some third-party uploaders, like S3_Upload widget, or any other, so you would need to get only upload signature for it, so now you can have it as UploadSignature object, as XML string, as XmlDocument or as JSON string:

```java
String jsonStringSignature = api.getUploadSignature().toJson();
```

####Uploading video

>Upload video from local drive directly to Amazon S3 bucket. Use this method when you build desktop apps or when you upload videos to vzaar directly from your server.

```java
String guid = api.uploadVideo("PATH/TO/SOME_FILE");
```

Keep in mind that file uploaded to a Amazon S3 storage in chunks of 128Kb, you can adjust this chunk size this way:

```java
api.bufferSize = 262144; //256 kb
```

####Processing video

>This API call tells the vzaar system to process a newly uploaded video. This will encode it if necessary and then provide a vzaar video ID back.

```java
VideoProcessQuery processQuery = new VideoProcessQuery();
processQuery.guid = "vzcf7af7bc5a734c30a46ca3911e7f3458";
processQuery.title = "My awesome video";
processQuery.description = "The story about how easy to build awesome apps with vzaar API";
processQuery.profile = VideoProfile.ORIGINAL;
processQuery.labels = new String[]{"api", "tutorials"};

int x = api.processVideo(processQuery);
```

If you want to replace existing video with some newly uploaded, you can call _Process Video_ with adding _replaceId_ parameter equal to vzaar video ID of the video that needs to be replaced.

```java
VideoProcessQuery processQuery = new VideoProcessQuery();
processQuery.guid = "vzcf7af7bc5a734c30a46ca3911e7f3458";
processQuery.replaceId = 12345678; //vzaar Video ID of the video you want to replace
processQuery.title = "My awesome video";
processQuery.description = "The story about how easy to build awesome apps with vzaar API";
processQuery.profile = VideoProfile.ORIGINAL;
processQuery.labels = new String[]{"api", "tutorials"};

int x = api.processVideo(processQuery);
```

####Editing video

>This API call allows a user to edit or change details about a video in the system.

```java
VideoEditQuery editQuery = new VideoEditQuery();
editQuery.title = "My REALLY awesome video";
editQuery.description = "The story about how easy to build awesome apps with vzaar API";
editQuery.markAsPrivate = true;

Boolean x = api.editVideo(editQuery);
```

Notice _markAsPrivate_ property in _VideoEditQuery_ variable, you can pass there _true_ or _false_, and this property marks the video as private (if true) or public (if false).

####Deleting video
>This API call allows you to delete a video from your account. If deletion was successful it will return you _true_ otherwise _false_.

```java
Boolean result = api.deleteVideo(VZAAR_VIDEO_ID);
```

Where VZAAR_VIDEO_ID is unique vzaar video ID assigned to a video after its processing.
