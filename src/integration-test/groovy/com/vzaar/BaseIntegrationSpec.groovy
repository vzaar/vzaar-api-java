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
        for (Video video : Pages.list(vzaar.videos().list().results())) {
            if (!video.title.contains("[DND]") && video.state != VideoState.deleted) {
                vzaar.videos().delete(video.id)
            }
        }

        for (Category category : Pages.list(vzaar.categories().list().results())) {
            if (!category.name.contains("[DND]") && category.parentId == null) {
                vzaar.categories().delete(category.id)
            }
        }

        for (IngestRecipe recipe : Pages.list(vzaar.recipes().list().results())) {
            if (!recipe.name.contains("[DND]") && !recipe.default) {
                vzaar.recipes().delete(recipe.id)
            }
        }

        for (Playlist playlist : Pages.list(vzaar.playlists().list().results())) {
            if (!playlist.title.contains("[DND]")) {
                vzaar.recipes().delete(playlist.id)
            }
        }
    }

    private static setupBaseTestData() {
        Page<Video> videos = vzaar.videos().list().results()
        for (int i = videos.totalCount; i < 3; ++i) {
            String uuid = UUID.randomUUID().toString()
            vzaar.videos().uploadWithFile()
                    .withTitle("[DND] Integration Test Video ${uuid}")
                    .withDescription("Base integration data set video ${uuid} [DND]")
                    .withUploader("Integration test")
                    .withFile(smallVideo)
                    .result();
        }

        while (vzaar.videos().list().results().totalCount < 3) {
            sleep(10000)
        }
    }
}
