package com.freshworks.sds.PluginManagement;

import com.freshworks.sds.Response.EmailScoreResponse;
import com.freshworks.sds.Response.LearnResponse;

public class PluginManager {

    public EmailScoreResponse getEmailScore(String username, String message, String requestId, boolean isTemplate) {
        return new EmailScoreResponse();
    }

    public LearnResponse report(String username, String message, String requestId, String messageType) {
        return new LearnResponse();
    }
}
