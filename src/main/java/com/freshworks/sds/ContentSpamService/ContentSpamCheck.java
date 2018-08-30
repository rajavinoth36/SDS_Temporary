package com.freshworks.sds.ContentSpamService;

import com.freshworks.sds.Response.ContentScoreResponse;

public class ContentSpamCheck {

    public String filter(String sentence) {
        //sentence.split("[\\p{Punct}\\s]+").(word => stopWords.contains(word.toLowerCase))
        return null;
    }


    public ContentScoreResponse getContentScore(String username, String message, String accountCreationDate, int level, String requestId) {
        return new ContentScoreResponse();
    }
}
