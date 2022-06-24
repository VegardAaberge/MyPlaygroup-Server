package com.myplaygroup.server.chat.repository;

public class MessageQuery {

    private static final String BASE_QUERY = "SELECT DISTINCT " +
                "    message.id, " +
                "    message.client_id as clientId, " +
                "    message.created, " +
                "    message.message, " +
                "    app_user.username as createdBy, " +
                "    app_user.profile_name as profileName, " +
                "COALESCE( ( " +
                "            SELECT " +
                "                concat(app_user_read.username) " +
                "            FROM " +
                "                message_read_by " +
                "            JOIN " +
                "                app_user as app_user_read " +
                "                    ON app_user_read.id = message_read_by.read_by_id " +
                "            WHERE " +
                "                message_read_by.message_id = message.id " +
                "), '') as readBy, " +
                "COALESCE( ( " +
                "            SELECT " +
                "                concat(app_user_receivers.username) " +
                "            FROM " +
                "                message_receivers " +
                "            JOIN " +
                "                app_user as app_user_receivers " +
                "                    ON app_user_receivers.id = message_receivers.receivers_id " +
                "            WHERE " +
                "                message_receivers.message_id = message.id " +
                "), '') as Receivers " +
                "FROM message " +
                "    JOIN message_receivers  " +
                "        ON message.id = message_receivers.message_id " +
                "    JOIN app_user " +
                "        ON app_user.id = message.app_user_id ";

    public static final String QUERY_ALL = BASE_QUERY;

    public static final String QUERY_OWNER_AND_RECEIVER = BASE_QUERY +
            "WHERE " +
            "    message.app_user_id = ?1 " +
            "    OR message_receivers.receivers_id = ?1";

    public static final String QUERY_BY_ID = BASE_QUERY +
            "WHERE " +
            "    message.id = ?1 ";
}
