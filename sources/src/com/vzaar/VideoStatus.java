package com.vzaar;

public class VideoStatus {
    public static final int PROCESSING = 1; //Processing not complete
    public static final int AVAILABLE = 2; //Available (processing complete, video ready)
    public static final int EXPIRED = 3; //Expired
    public static final int ON_HOLD = 4; //On Hold (waiting for encoding to be available)
    public static final int FAILED = 5; //Encoding Failed
    public static final int ENCODING_UNAVAILABLE = 6; //Encoding Unavailable
    public static final int NOT_AVAILABLE = 7; //n/a
    public static final int REPLACED = 8; //Replaced
    public static final int DELETED = 9; //Deleted

}
