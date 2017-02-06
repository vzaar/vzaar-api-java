package com.vzaar

import com.vzaar.client.RestClientConfiguration
import com.vzaar.util.ObjectMapperFactory
import spock.lang.Specification;

public class BaseIntegrationSpec extends Specification {
    protected static Vzaar vzaar
    protected static File smallVideo;
    protected static File mediumVideo;
    protected static File notAVideo;
    private static boolean firstRun = true

    def setupSpec() {
        ObjectMapperFactory.setFailOnUnknownProperties(true)
        vzaar = Vzaar.make(new RestClientConfiguration()
                .withClientId(System.getProperty("vzaarClientId") ?: System.getenv("vzaarClientId"))
                .withAuthToken(System.getProperty("vzaarAuthToken") ?: System.getenv("vzaarAuthToken"))
                .withBlockTillRateLimitReset(true))

        smallVideo = new File(getClass().classLoader.getResource("videos/small.mp4").getFile());
        mediumVideo = new File(getClass().classLoader.getResource("videos/video.mp4").getFile());
        notAVideo = new File(getClass().classLoader.getResource("com/vzaar/Vzaar.class").getFile())
        if (firstRun) {
            cleanupAll()
            setupBaseTestData()
            firstRun = false
        }
    }

    def cleanupSpec() {
        cleanupAll()
    }

    def cleanupAll() {
        for (Video video : Pages.list(vzaar.videos(new VideoPageRequest()))) {
            if (!video.title.contains("[DND]") && video.state != VideoState.deleted) {
                vzaar.videoDelete(video.id)
            }
        }
//        for (Video video : Pages.list(vzaar.videos(new VideoPageRequest().withState(VideoState.failed)))) {
//            if (!video.title.contains("[DND]")) {
//                vzaar.videoDelete(video.id)
//            }
//        }
        for (Category category : Pages.list(vzaar.categories(new CategoryPageRequest()))) {
            if (!category.name.contains("[DND]") && category.parentId == null) {
                vzaar.categoryDelete(category.id)
            }
        }

        for (IngestRecipe recipe : Pages.list(vzaar.recipes(new IngestRecipePageRequest()))) {
            if (!recipe.name.contains("[DND]") && !recipe.default) {
                vzaar.recipeDelete(recipe.id)
            }
        }
    }

    private setupBaseTestData() {
        Page<Video> videos = vzaar.videos(new VideoPageRequest());
        for (int i = videos.totalCount; i < 3; ++i) {
            vzaar.upload(buildVideoUploadRequest(smallVideo))
        }

        while (vzaar.videos(new VideoPageRequest()).totalCount < 3) {
            sleep(10000)
        }
    }

    protected VideoUploadRequest buildVideoUploadRequest(File file) {
        String uuid = UUID.randomUUID().toString();
        new VideoUploadRequest()
                .withTitle("[DND] Integration Test Video ${uuid}")
                .withDescription("Base integration data set video ${uuid} [DND]")
                .withUploader("Integration test")
                .withFile(file);
    }
}
