# Vzaar Java SDK

[![Build Status](https://api.travis-ci.org/nine-lives/vzaar-sdk-java.png)](https://travis-ci.org/nine-lives/vzaar-sdk-java)
[![Code Quality](https://api.codacy.com/project/badge/grade/e37e10ecd34e4942acc11ebbb8aa2e3c)](https://www.codacy.com/app/nine-lives/vzaar-sdk-java)
[![Coverage](https://api.codacy.com/project/badge/coverage/e37e10ecd34e4942acc11ebbb8aa2e3c)](https://www.codacy.com/app/nine-lives/vzaar-sdk-java)

## Getting Started

The vzaar API requires you to have a client ID and authentication token. 
To manage your API tokens login to https://app.vzaar.com/settings/api.

All API calls are rooted from the `Vzaar` object.

```
    Vzaar vzaar = Vzaar.make(clientId, authToken);
```

## Paged Requests

All requests that return a list of entities extend a `PageableRequest`. It
allows the request to determine the page size, page number, sort column and
sort direction. All page request attributes are optional and the server will
use default values if they are not set. Page counting starts from 1.

The sort column, if available for sorting, is the attribute name in snake case. For example:

```
    Page<Video> page = vzaar.videos(new VideoPageRequest()
            .withPage(1)
            .withResultsPerPage(5)
            .withSortByAttribute("created_at")
            .withSortDirection(SortDirection.desc));
```

Paged requests return typed `Page` responses, which are able to request the first,
next, previous pages.

```
    Page<Video> page = vzaar.videos(new VideoPageRequest());
    System.out.println("Total count = " + page.getTotalCount());
    System.out.println("Page count = " + page.getData().size());
    
    if (page.hasNext()) {
        Page<Video> nextPage = page.getNext();
        
        // This is equivalent for a default search to 
        nextPage = vzaar.videos(new VideoPageRequest().withPage(2))
    }
```

There is also a utility class called `Pages` to allow you to retrieve
all the items on a given page and subsequent pages as a single list or 
stream.

```
    // This will collate all videos irrelevant of the number of pages 
    List<Video> videos = Pages.list(vzaar.videos(new VideoPageRequest());
    
    // This will produce an iterator that will return all the videos
    // irrelevant of the number of pages. Note that calls the pages
    // lazily so may be preferable if memory is an issue
    Iterator<Video> videos = Pages.iterator(vzaar.videos(new VideoPageRequest());
    while(videos.hasNext()) {
        Video video = videos.next();
    }
    
    // This produces an iterable wrapper around the iterator
    for (Video video : Pages.iterable(vzaar.videos(new VideoPageRequest())) {
    }
```

## Video Uploading
 
There are two simple ways to upload videos to Vzaar. The first is from
a local file.
 
```
    File videoFile = new File("myvideo.mp4");
    Video video = vzaar.upload(new VideoUploadRequest()
          .withTitle("My video title")
          .withUploader("Jack Smith")
          .withFile(videoFile));
```

Note that this method will automatically decide whether the file should
be uploaded as a single upload or a multipart upload. The current default 
boundary is set to 1GB, that is if the file is larger than 1GB then it
will be sent in 128MB chunks.

If you want to configure the boundary for determining when the video
should be sent as a multipart upload or the default chunk size this can
be done using the `RestClientConfiguration` and initialising the Vzaar
object with it.

```
    Vzaar vzaar = Vzaar.make(new RestClientConfiguration()
        .withClientId(clientId)
        .withAuthToken(authToken)
        .withUseMultipartWhenFileSizeInMbOver(128)
        .withDefaultDesiredChunkSizeInMb(64));
```

You can also upload your video from a URL.

```
    Video video = vzaar.upload(new CreateLinkUploadRequest()
        .withTitle("My video title")
        .withUploader("Jack Smith")
       .withUrl("https://www.example.com/my-video.com"));
```

If you need more control over your uploads you can call the `CustomUploader`
which will give you finer control over your uploads.

```
    File videoFile = new File("myvideo.mp4");

    CustomUploader uploader = vzaar.getCustomUploader();

    // Create the upload signature
    UploadRequest uploadRequest = uploader.signature(UploadType.multipart, new CreateSignatureRequest()
            .withFile(videoFile)
            .withUploader("Jack Smith")
            .withDesiredPartSizeInMb(64);

    // Do one of a or b below
    // a) This will upload all the chunks sequentially before returning
    uploader.upload(uploadRequest, videoFile);
    
    // b) Alternatively you could call the uploading of chunks separately
    for (int i = 0; i < uploadRequest.getUploadSignature().getParts(); ++i) {
        uploader.uploadPart(uploadRequest, videoFile, i);
    }

    // Finally tell Vzaar that the video is uploaded
    return uploader.createVideo(new CreateVideoRequest()
            .withGuid(uploadRequest.getUploadSignature().getGuid())
            .withTitle("My Video Title"));
    

```

## Custom Configuration

You can also use the `RestClientConfiguration` to configure the SDK. Apart
from the the client id and auth token all the other values have defaults
show below.

```
    Vzaar vzaar = Vzaar.make(new RestClientConfiguration()
        .withClientId(clientId)
        .withAuthToken(authToken)
        .withEndpoint("https://api.vzaar.com/api/v2")
        .withMaxConnectionsPerRoute(20)
        .withUserAgent("vzaar-sdk-java 2.0.0")
        .withBlockTillRateLimitReset(false)
        .withUseMultipartWhenFileSizeInMbOver(1024)
        .withDefaultDesiredChunkSizeInMb(128));
```

| Configuration Attribute | Description |
| ----------------------- | ----------- |
| Endpoint | The base api url. Defaults to https://api.vzaar.com/api/v2 |
| MaxConnectionsPerRoute | The effective maximum number of concurrent connections in the pool. Connections try to make use of the keep-alive directive. Defaults to 20
| UserAgent | The user agent string sent in the request
| BlockTillRateLimitReset | If set to true then the client will block if the rate limit has been reached until the reset timestamp has expired. Defaults to false
| UseMultipartWhenFileSizeInMbOver | The boundary condition of file size to determine when multipart upload should be used. Defaults to 1024MB
| DefaultDesiredChunkSizeInMb | The default desired chunk size for multipart uploads when upload type auto-selection is being used. Defaults to 128MB 




