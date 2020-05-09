package com.zp.common.core.util;

import com.alibaba.fastjson.JSONObject;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.zp.common.core.exception.RRException;

import java.util.Date;
import java.util.UUID;

public class JwtUtil {


    private static String secret = "234234234jhk22834298347283487023473276482354826342ksdfbsadfsadkfjsalkjdfbsadfbsakdbkb342340923904";

    public static String createSecret(){
        return UUID.randomUUID().toString();

    }

    public static String createToken(JSONObject jsonObject,String secret){
        String idToken = null;
        try {
            JWSSigner jwsSigner = new MACSigner(JwtUtil.secret);
            JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS256).build();

            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject("用户token")
                    .issuer("翟盼个人系统")
                    .audience("Audience")
                    .claim("payloadText", jsonObject)
                    .expirationTime(new Date(new Date().getTime() + 604800000)) //七天
                    .issueTime(new Date())
                    .build();
            SignedJWT signedJWT = new SignedJWT(header, claimsSet);
            signedJWT.sign(jwsSigner);
            idToken = signedJWT.serialize();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return idToken;

    }

    public static JSONObject analysisToken(String idToken,String secret) throws Exception{

        //校验 id_token
        SignedJWT parseJWT = null;
        try {
            parseJWT = SignedJWT.parse(idToken);
        }catch (Exception e){
            e.getStackTrace();
            throw new RRException("token验证失败");
        }

        JWSVerifier verifier = new MACVerifier(JwtUtil.secret);

        boolean verify = parseJWT.verify(verifier);

        if(!verify){
            throw new RRException("token验证失败");
        }

        JWTClaimsSet jwtClaimsSet = parseJWT.getJWTClaimsSet();

        if(jwtClaimsSet.getExpirationTime().getTime()<new Date().getTime()){
            throw new RRException("登录已过期,请重新登录");
        }
        return JSONObject.parseObject(jwtClaimsSet.getClaim("payloadText").toString());

    }

    public static void main(String[] args) throws Exception {

        String secret = createSecret();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId","admin");
        String token = JwtUtil.createToken(jsonObject,secret);
        Thread.sleep(1000);
        JSONObject jsonObject1 = JwtUtil.analysisToken(token,secret);


    }


}
