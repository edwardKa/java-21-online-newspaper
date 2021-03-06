package com.online.newspaper.repository;

import com.online.newspaper.model.entities.User;
import com.online.newspaper.model.entities.UserSession;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserSessionRepository {

    private static Map<String, UserSession> sessions = new HashMap<>();

    public UserSession create(String sessionId, User user) {
        UserSession userSession = UserSession
                .builder()
                .sessionId(sessionId)
                .user(user)
                .isValid(true)
                .build();
        sessions.putIfAbsent(sessionId, userSession);

        return userSession;
    }

    public UserSession getBySessionId(String sessionId) {
        UserSession userSession = sessions.get(sessionId);
        if (userSession == null || !userSession.getIsValid()) {
            return null;
        }


        return userSession;
    }

    public void invalidateSession(String sessionId) {
        UserSession userSession = sessions.get(sessionId);
        if (userSession == null) {
            return;
        }
        userSession.setIsValid(false);
        sessions.put(sessionId, userSession);
    }
}
