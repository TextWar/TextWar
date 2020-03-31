package cn.textwar.protocol.security;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

public class RSA {

    public static final String TYPE = "RSA";


    public static PairKeys genKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(TYPE);
        keyPairGen.initialize(512,new SecureRandom());
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
        String publicKeyString = new String(Base64.getEncoder().encode(publicKey.getEncoded()));
        String privateKeyString = new String(Base64.getEncoder().encode((privateKey.getEncoded())));
        return new PairKeys(publicKeyString,privateKeyString);
    }
    /**
     * RSA公钥加密
     *
     */
    public static byte[] encrypt( String str, String publicKey ) throws Exception{
        //base64编码的公钥
        byte[] decoded = Base64.getDecoder().decode(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance(TYPE).generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance(TYPE);
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return Base64.getEncoder().encode(cipher.doFinal(str.getBytes()));
    }

    /**
     * RSA私钥解密
     */
    public static byte[] decrypt(String str, String privateKey) throws Exception{
        byte[] inputByte = Base64.getDecoder().decode(str.getBytes());
        byte[] decoded = Base64.getDecoder().decode(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance(TYPE).generatePrivate(new PKCS8EncodedKeySpec(decoded));
        Cipher cipher = Cipher.getInstance(TYPE);
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        return cipher.doFinal(inputByte);
    }
}
