package com.vzaar.client

import spock.lang.Specification
import spock.lang.Unroll

class FileStreamingBodySpec extends Specification {
    @Unroll
    def "The input stream is constrained to the content length"() {
        given:
        ByteArrayInputStream input = new ByteArrayInputStream("12345678901234567890".bytes)
        FileStreamingBody fsb = new FileStreamingBody(input, "file", contentLength, bufferSize);

        when:
        ByteArrayOutputStream output = new ByteArrayOutputStream()
        fsb.writeTo(output)

        then:
        output.size() == contentLength
        output.toString() == '1234567890'
        fsb.contentLength == 10

        where:
        bufferSize | contentLength
        5          | 10
        6          | 10
        10         | 10
        11         | 10
        100        | 10
    }

    def "The default buffer size constructor works"() {
        given:
        ByteArrayInputStream input = new ByteArrayInputStream("12345678901234567890".bytes)
        FileStreamingBody fsb = new FileStreamingBody(input, "file", 10);

        when:
        ByteArrayOutputStream output = new ByteArrayOutputStream()
        fsb.writeTo(output)
        fsb.contentLength == 10

        then:
        output.size() == 10
        output.toString() == '1234567890'
        fsb.contentLength == 10
    }
}
