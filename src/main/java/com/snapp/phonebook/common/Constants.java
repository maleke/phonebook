package com.snapp.phonebook.common;

public class Constants {
    public static final String EXCHANGE_NAME = "githubRepository.topic";
    public static final String ROUTING_KEY_NAME = "githubRepository.*.*";
    public static final String INCOMING_QUEUE_NAME = "repoFetchQueue";
    public static final String DEAD_LETTER_QUEUE_NAME = "repoFetchQueueDlx";
}