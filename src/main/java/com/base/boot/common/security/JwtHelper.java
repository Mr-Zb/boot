package com.base.boot.common.security;

import com.base.boot.common.utils.AESSecretUtil;
import com.base.boot.common.utils.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zbang
 * @Date: 2020/4/27 5:34 上午
 */
@Component
public class JwtHelper {
    private static Logger logger = LoggerFactory.getLogger(JwtHelper.class);

    public String generateJWT(String userId, String userName, String role) {
        //签名算法，选择SHA-256
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //获取当前系统时间
        long nowTimeMillis = System.currentTimeMillis();
        Date now = new Date(nowTimeMillis);
        //将BASE64SECRET常量字符串使用base64解码成字节数组
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(Constants.BASE64SECRET);
        //使用HmacSHA256签名算法生成一个HS256的签名秘钥Key
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        //添加构成JWT的参数
        long expMillis = nowTimeMillis + Constants.EXPIRESSECOND;
        Date expDate = new Date(expMillis);
        Map<String, Object> headMap = new HashMap<>();
        headMap.put("alg", SignatureAlgorithm.HS256.getValue());
        headMap.put("typ", "JWT");
        JwtBuilder builder = Jwts.builder().setHeader(headMap)
                //加密后的客户编号
                .claim("userId", AESSecretUtil.encryptToStr(userId, Constants.DATAKEY))
                //客户名称
                .claim("userName", userName)
                .claim("role", role)
                //Signature
                .signWith(signatureAlgorithm, signingKey)
                //添加Token过期时间
                .setExpiration(expDate).setNotBefore(now);
        return builder.compact();
    }


    /**
     * @param jsonWebToken - JWT
     * @Description: 解析JWT
     * 返回Claims对象
     * @Modified By:
     */
    public Claims parseJWT(String jsonWebToken) {
        Claims claims = null;
        try {
            if (StringUtils.isNotBlank(jsonWebToken)) {
                //解析jwt
                claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(Constants.BASE64SECRET))
                        .parseClaimsJws(jsonWebToken).getBody();
            } else {
                logger.warn("json web token 为空");
            }
        } catch (Exception e) {
            logger.error("JWT解析异常：可能因为token已经超时或非法token");
        }
        return claims;
    }

}
