package com.cloud.user.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cloud.user.Enum.ResponseEnum;
import com.cloud.user.exception.BusinessException;
import com.cloud.user.pojo.User;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtHelper {
    private static final String SECRET = "7786df7fc3a34e26a61c034d5ec8245d";
    private static final String iss = "guxin";

    public static String getToken(User user) throws BusinessException {
        try {
            user.setPassword("");
            Date date = new Date(System.currentTimeMillis() + 1 * 60 * 1000);
            Map map = new HashMap();
            map.put("type", "HS256");
            map.put("alg", "JWT");
            return JWT.create().withHeader(map).withClaim("user", new Gson().toJson(user)).withIssuer(iss).withExpiresAt(date).sign(Algorithm.HMAC256(SECRET)).toString();
        } catch (UnsupportedEncodingException e) {
            throw new BusinessException(ResponseEnum.USER_ERROR_TOKEN.getCode(), ResponseEnum.USER_ERROR_TOKEN.getMessage());
        }
    }

    public static String verifyToken(String token) throws BusinessException {
        Algorithm algorithm = null;
        try {
            algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(iss).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            Claim claim = decodedJWT.getClaim("user");
            return claim.asString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new BusinessException(ResponseEnum.USER_ERROR_TOKEN.getCode(), ResponseEnum.USER_ERROR_TOKEN.getMessage());
        }
    }

}
