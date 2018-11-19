package com.vzaar;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SubtitleStoreRequest<T> {
    private final transient Class<T> type;
    private String code;
    private String title;
    private String content;
    private transient File file;

    SubtitleStoreRequest(Class<T> type) {
        this.type = type;
    }

    /**
     * Set the language code. Required.
     * <p>
     * vzaar supports ISO 639-1 two-letter codes as well as the following additional languages:
     *
     * <ul>
     * <li>zh-hans: Chinese Simplified</li>
     * <li>zh-hant: Chinese Traditional</li>
     * <li>fr-ca: French (Canadian)</li>
     * <li>pt-br: Portuguese (Brazilian)</li>
     * </ul>
     * See <a href="https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes">https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes</a>
     *
     * @param code the language code
     * @return this
     */
    public T withCode(String code) {
        this.code = code;
        return type.cast(this);
    }

    /**
     * Set the name for the subtitle file
     *
     * @param title the title
     * @return this
     */
    public T withTitle(String title) {
        this.title = title;
        return type.cast(this);
    }

    /**
     * The subtitle content. You must provide one (but not both) of file or content. Content
     * newlines must be represented with \n to be valid JSON.
     *
     * @param content the content in SRT or VTT format
     * @return this
     */
    public T withContent(String content) {
        this.content = content;
        return type.cast(this);
    }

    /**
     * The subtitle file. You must provide one (but not both) of file or content and if sending content then newlines
     * must be represented with \n to be valid JSON.
     *
     * @param file the file in SRT or VTT format
     * @return this
     */
    public T withFile(File file) {
        this.file = file;
        return type.cast(this);
    }

    boolean hasFile() {
        return file != null;
    }

    Map<String, Object> asMap() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("code", code);
        if (title != null && !title.isEmpty()) {
            payload.put("title", title);
        }
        payload.put("file", file);
        return payload;
    }
}
