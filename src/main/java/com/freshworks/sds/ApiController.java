package com.freshworks.sds;

import com.freshworks.sds.ContentSpamService.ContentSpamCheck;
import com.freshworks.sds.PluginManagement.PluginManager;
import com.freshworks.sds.Response.ContentScoreResponse;

import com.freshworks.sds.Response.EmailScoreResponse;
import com.freshworks.sds.Response.LearnResponse;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class ApiController {
    private static final Logger logger = LogManager.getLogger("ApiController");
    private PluginManager pluginManager = new PluginManager();

    @RequestMapping(value = "/", method = POST)
    public String index() {
        logger.info("Visited Homepage");
        return "Greetings from Spring Boot!";
    }

    @RequestMapping(value = "/health", method = GET)
    public ResponseEntity healthPing() {
        logger.info("Received health ping");
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/get_content_score", method = POST)
    public ContentScoreResponse getContentScore(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "message") String message,
            @RequestParam(name = "account_creation_date", required = false) String accountCreationDate,
            @RequestParam(name = "level", required = false, defaultValue = "3") int level,
            @RequestParam(name = "request_id", required = false) String requestId
    ) {
        ContentScoreResponse response = new ContentSpamCheck().getContentScore(username, message, accountCreationDate, level, requestId);
        logger.info("Method: getContentScore Username: " + username +
                " Message: " + message + " Result: " + response + ((requestId != null) ? requestId : ""));
        return response;
    }

    @RequestMapping(value = {"/get_email_score", "get_template_score"}, method = POST)
    public EmailScoreResponse getEmailScore(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "message") String message,
            @RequestParam(name = "request_id", required = false) String requestId,
            @RequestParam(name = "isTemplate", required = false, defaultValue = "false") boolean isTemplate
    ) {
        EmailScoreResponse response = new PluginManager().getEmailScore(username, message, requestId, isTemplate);
        logger.info("Method: " + (isTemplate ? "getTemplateScore" : "getEmailScore") +
                " Username: " + username + " Result: " + response + ((requestId != null) ? requestId : ""));
        return response;
    }

//    @RequestMapping(value = "/get_template_score", method = POST)
//    public EmailScoreResponse getTemplateScore(
//            @RequestParam(name="username") String username,
//            @RequestParam(name="message") String message,
//            @RequestParam(name="request_id", required = false) String requestId,
//            @RequestParam(name="isTemplate", required=false, defaultValue = "false") boolean isTemplate
//    ) {
//        EmailScoreResponse response = new PluginManager().getEmailScore(username, message, requestId, isTemplate);
//        logger.info("Method: getTemplateScore Username: " + username + " Result: " + response);
//        return response;
//    }

    @RequestMapping(value = "/learn_spam", method = POST)
    public LearnResponse learnSpam(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "message") String message,
            @RequestParam(name = "request_id", required = false) String requestId
    ) {
        LearnResponse response = new PluginManager().report(username, message, requestId, "spam");
        logger.info("Method: LearnSpam Username: " + username + " Result: " + response + ((requestId != null) ? requestId : ""));
        return response;
    }

    @RequestMapping(value = "/learn_ham", method = POST)
    public LearnResponse learnHam(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "message") String message,
            @RequestParam(name = "request_id", required = false) String requestId
    ) {
        LearnResponse response = new PluginManager().report(username, message, requestId, "ham");
        logger.info("Method: LearnHam Username: " + username + " Result: " + response + ((requestId != null) ? requestId : ""));
        return response;
    }

    @RequestMapping(value = "/forget", method = POST)
    public LearnResponse forget(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "message") String message,
            @RequestParam(name = "request_id", required = false) String requestId
    ) {
        LearnResponse response = new PluginManager().report(username, message, requestId, "forget");
        logger.info("Method: LearnHam Username: " + username + " Result: " + response + ((requestId != null) ? requestId : ""));
        return response;
    }
}
