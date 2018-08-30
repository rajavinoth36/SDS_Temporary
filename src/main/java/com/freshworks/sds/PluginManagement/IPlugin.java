package com.freshworks.sds.PluginManagement;

import com.freshworks.sds.Response.EmailScoreResponse;
import com.freshworks.sds.Response.LearnResponse;

public interface IPlugin {

    // Get email score and tests run for the given MIME message
    EmailScoreResponse getEmailScore(String username, String message);

    // Get template score and tests run for the given MIME message
    // The message should start from "Subject" and should not contain any headers
    EmailScoreResponse getTemplateScore(String username, String message);

    // Learn/Forget for Bayesian Learning
    LearnResponse report(String username, String message, String messageType);
}
