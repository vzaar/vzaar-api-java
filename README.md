# Vzaar Java SDK

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.vzaar/vzaar-java-sdk/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.vzaar/vzaar-java-sdk)
[![Build Status](https://api.travis-ci.org/nine-lives/vzaar-sdk-java.png)](https://travis-ci.org/nine-lives/vzaar-sdk-java)
[![Code Quality](https://api.codacy.com/project/badge/grade/e37e10ecd34e4942acc11ebbb8aa2e3c)](https://www.codacy.com/app/nine-lives/vzaar-sdk-java)
[![Coverage](https://api.codacy.com/project/badge/coverage/e37e10ecd34e4942acc11ebbb8aa2e3c)](https://www.codacy.com/app/nine-lives/vzaar-sdk-java)

Vzaar Java SDK for Java 8+

## Getting Started

The vzaar API requires you to have a client ID and authentication token. 
To manage your API tokens login to https://app.vzaar.com/settings/api.

All API calls are rooted from the `Vzaar` object.

```
    Vzaar vzaar = Vzaar.make(clientId, authToken);
```

The sdk is hosted on maven central so you can include it as a dependency 
in your projects as follows:

### Gradle/Grails
```
    compile 'com.vzaar:vzaar-java-sdk:2.1.1'
```

### Apache Maven
```
    <dependency>
        <groupId>com.vzaar</groupId>
        <artifactId>vzaar-java-sdk</artifactId>
        <version>2.1.1</version>
    </dependency>
```

### Apache Ivy
```
    <dependency org="com.vzaar" name="vzaar-java-sdk" rev="2.1.1" />
```

## Paged Requests

All requests that return a list of entities extend a `PageableRequest`. It
allows the request to determine the page size, page number, sort column and
sort direction. All page request attributes are optional and the server will
use default values if they are not set. Page counting starts from 1.

The sort column, if available for sorting, is the attribute name in snake case. For example:

```
    Page<Video> page = vzaar.videos().list()
            .withPage(1)
            .withResultsPerPage(5)
            .withSortByAttribute("created_at")
            .withSortDirection(SortDirection.desc)
            .results();
```

Paged requests return typed `Page` responses, which are able to request the first,
next, previous pages.

```
    Page<Video> page = vzaar.videos().list().results();
    System.out.println("Total count = " + page.getTotalCount());
    System.out.println("Page count = " + page.getData().size());
    
    if (page.hasNext()) {
        Page<Video> nextPage = page.getNext();
        
        // This is equivalent for a default search to 
        nextPage = vzaar.videos().list().withPage(2).results();
    }
```

There is also a utility class called `Pages` to allow you to retrieve
all the items on a given page and subsequent pages as a single list or 
stream.

```
    // Collate all videos irrelevant of the number of pages 
    List<Video> videos = Pages.list(vzaar.videos().list().results());
    
    // An iterator that will return all the videos irrelevant
    // of the number of pages. Note that this calls subsequent pages
    // lazily so may be preferable to Pages.list if memory is an issue or 
    // there are early exit conditions from a loop
    Iterator<Video> videos = Pages.iterator(vzaar.videos().list().results());
    while(videos.hasNext()) {
        Video video = videos.next();
    }
    
    // An iterable wrapper around the iterator
    for (Video video : Pages.iterable(vzaar.videos().list().results()) {
    }
```

## Basic Video Functions

Searching for videos
```$java
    Page<Video> videos = vzaar.videos().list()
            .withEscapedQuery("[Vzaar]")
            .withResultsPerPage(2)
            .withSortByAttribute("title")
            .withSortDirection(SortDirection.asc)
            .results();

```

Fetching a video by id
```$java
    Video video = vzaar.videos().get(videoId);
```

Updating a video
```$java
    Video video = vzaar.videos().update(videoId)
        .withTitle("[Vzaar] New Title")
        .withDescription("Updated video description")
        .withPrivate(true)
        .withSeoUrl("http://www.vzaar.com/video.mp4")
        .result();
```

Deleting a video
```$java
    vzaar.videos().delete(videoId);
```

## Video Uploading
 
There are two simple ways to upload videos to Vzaar. The first is from
a local file.
 
```
    File videoFile = new File("myvideo.mp4");
    Video video = vzaar.videos().uploadWithFile()
          .withTitle("My video title")
          .withUploader("Jack Smith")
          .withFile(videoFile)
          .result();
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
    Video video = vzaar.videos().uploadWithLink()
        .withTitle("My video title")
        .withUploader("Jack Smith")
        .withUrl("https://www.example.com/my-video.com")
        .result();
```

If you need more control over your uploads you can call the `CustomUploader`
which will give you finer control over your uploads.

```
    File videoFile = new File("myvideo.mp4");

    CustomUploader uploader = vzaar.videos().getCustomUploader();

    // Create the upload signature
    Signature signature = uploader.signature()
            .withType(UploadType.multipart)
            .withFile(videoFile)
            .withUploader("Jack Smith")
            .withDesiredPartSizeInMb(64)
            .result();

    // Do one of a or b below
    // a) This will upload all the chunks sequentially before returning
    uploader.upload(signature, videoFile);
    
    // b) Alternatively you could call the uploading of chunks separately
    for (int i = 0; i < signature.getParts(); ++i) {
        uploader.uploadPart(signature, videoFile, i);
    }

    // Finally tell Vzaar that the video is uploaded
    Video video = uploader.createVideo()
            .withGuid(signature.getGuid())
            .withTitle("My Video Title")
            .result();
    

```

## Adding Subtitles

You can add subtitles as follows:

```$java
    vzaar.subtitles().create(video.getId())
        .withCode("en")
        .withContent(new SubRipSubtitles()
            .addCue("00:00:01,123", "00:00:11,321", "First subtitle")
            .addCue("00:01:02,123", "00:01:12,321", "Second subtitle")
            .addCue("01:02:03,123", "02:01:13,321", "Third subtitle")
        .result()
```

You may also use millisecond offsets from the start of the 
video rather than the string representation above.

```$java
    vzaar.subtitles().create(video.getId())
        .withCode("en")
        .withContent(new SubRipSubtitles()
            .addCue(1123, 11321, "First subtitle")
            .addCue(62123, 72321, "Second subtitle")
            .addCue(3723123, 7273321, "Third subtitle")
        .result()
```

## Utility Classes

There is an `Identifiables` class that allows you to collect ids, check if 
an id exists, find a domain entity by id or index the objects by id.

```
    List<EncodingPreset> presets = Page.list(vzaar.encodingPresets().list().results());

    // Collect the ids
    Set<Integer> presetIds = Identifiables.collect(presets);
     
    // Check if an id exists
    boolean exists = Identifiables.hasId(presets, 42);
         
    // Get the entity by id
    EncodingPreset preset = Identifiables.find(presets, 42);
         
    // Map by id
    Map<Integer, EncodingPreset> idMap = Identifiables.index(presets);     
```

To build out the category tree structure you can use the `CategoryTreeBuilder` utility
class. By default the children, including the root nodes are sorted by the category
name. You can change the sort order by passing in your own comparator.

```
    List<Category> categories = Page.list(vzaar.categories().list().results());
    
    // Build the tree, this returns all the root categories, 
    // i.e. those without a parent id
    List<CategoryNode> treeRoots = CategoryTreeBuilder.build(categories);
    
    CategoryNode firstRootNode = treeRoots.get(0);
    firstRootNode.getCategory();         // the category
    firstRootNode.hasChildren();         // are there any children?
    firstRootNode.getChildCount();       // the total count of direct children
    firstRootNode.getDescendantCount();  // the total count of all decendants
    firstRootNode.getChildren();         // get the direct children nodes 
    
```

## Custom Configuration

You can also use `RestClientConfiguration` to configure the SDK. Apart
from the the client id and auth token all the other values have defaults.

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


## Build

Once you have checked out the project you can build and test the project with the following command:

```
    gradlew check -x integrationTest -x jacocoTestReport
```

 
 

