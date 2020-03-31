package cn.textwar.protocol.security;

public class PairKeys {

    private String publicKey;

    private String privateKey;

    public PairKeys(String publicKey, String privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }
}
