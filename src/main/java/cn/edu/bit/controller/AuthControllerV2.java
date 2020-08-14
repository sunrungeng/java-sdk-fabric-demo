package cn.edu.bit.controller;

import cn.edu.bit.gm.sm9.*;
import cn.edu.bit.utils.Hex;
import cn.edu.bit.utils.Init;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/authentication")
public class AuthControllerV2 {

    private static SM9Curve sm9Curve;
    private static KGC kgc;
    private static SM9 sm9;

    static {
        sm9Curve = new SM9Curve();
        kgc = new KGC(sm9Curve);
        sm9 = new SM9(sm9Curve);
    }

    MasterKeyPair masterKeyPair = kgc.genSignMasterKeyPair();

    @RequestMapping("/test")
    public String test() {
        return "hello world";
    }

    @RequestMapping("/init")
    public String init() throws Exception {
        Init.enroll();
        Init.register();
        return "Enroll And Register Success";
    }

    @RequestMapping("/registerTop")
    public String registerTop(String ID, String MPK) throws Exception {

        // Load a file system based wallet for managing identities.
        Path walletPath = Paths.get("wallet");
        Wallet wallet = Wallet.createFileSystemWallet(walletPath);
        // load a CCP
        Path networkConfigPath = Paths.get("..", "..", "first-network", "connection-org1.yaml");
        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, "user1").networkConfig(networkConfigPath).discovery(true);
        // create a gateway connection
        try (Gateway gateway = builder.connect()) {
            // get the network and contract
            Network network = gateway.getNetwork("mychannel");
            Contract contract = network.getContract("Authentication");
            contract.submitTransaction("registerTop", ID, MPK);
        }

        return "Success";

    }

    @RequestMapping("/registerNotTop")
    public String registerNotTop(String IDi, String IDi_1) throws Exception {
        // Load a file system based wallet for managing identities.
        Path walletPath = Paths.get("wallet");
        Wallet wallet = Wallet.createFileSystemWallet(walletPath);
        // load a CCP
        Path networkConfigPath = Paths.get("..", "..", "first-network", "connection-org1.yaml");
        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, "user1").networkConfig(networkConfigPath).discovery(true);
        // create a gateway connection
        try (Gateway gateway = builder.connect()) {
            // get the network and contract
            Network network = gateway.getNetwork("mychannel");
            Contract contract = network.getContract("Authentication");
            contract.submitTransaction("registerNotTop", IDi, IDi_1);
        }
        return "Success";
    }

    @RequestMapping("/queryMPK")
    public String queryMPK(String ID) throws Exception {

        // Load a file system based wallet for managing identities.
        Path walletPath = Paths.get("wallet");
        Wallet wallet = Wallet.createFileSystemWallet(walletPath);

        // load a CCP
        Path networkConfigPath = Paths.get("..", "..", "first-network", "connection-org1.yaml");
        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, "user1").networkConfig(networkConfigPath).discovery(true);

        // create a gateway connection
        byte[] result;

        try (Gateway gateway = builder.connect()) {
            // get the network and contract
            Network network = gateway.getNetwork("mychannel");
            Contract contract = network.getContract("Authentication");
            result = contract.evaluateTransaction("queryMPK", ID);
        }
        return new String(result);
    }

    @RequestMapping("/verifyAndUploadPara")
    public String verifyAndUploadPara(String IDi, String IDi_1, String message, String signature) throws Exception {
        // Load a file system based wallet for managing identities.
        Path walletPath = Paths.get("wallet");
        Wallet wallet = Wallet.createFileSystemWallet(walletPath);
        // load a CCP
        Path networkConfigPath = Paths.get("..", "..", "first-network", "connection-org1.yaml");
        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, "user1").networkConfig(networkConfigPath).discovery(true);
        // create a gateway connection
        try (Gateway gateway = builder.connect()) {
            // get the network and contract
            Network network = gateway.getNetwork("mychannel");
            Contract contract = network.getContract("Authentication");
            contract.submitTransaction("verifyAndUploadPara", IDi, IDi_1, message, signature);
        }
        return "Success";
    }

    /**
     * 得到簽名用的sk
     * @param ID
     * @param MSK
     * @return
     */
    @RequestMapping("/signature_key_generator")
    public String signature_key_generator(String ID, String MSK) {
        MasterPrivateKey masterPrivateKey = MasterPrivateKey.fromByteArray(Hex.decode(MSK));
        String hexStrSignatureKey = null;
        PrivateKey signatureKey;
        try {
            signatureKey = kgc.genPrivateKey(masterPrivateKey, ID, PrivateKeyType.KEY_SIGN);
            hexStrSignatureKey = Hex.encodeToString(signatureKey.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hexStrSignatureKey;
    }

    @RequestMapping("/signature")
    public String signature(String hexStrSignatureKey, String strMessage, String MPK){
        ResultSignature resultSignature;
        String hexStrResultSignature = null;
        PrivateKey signatureKey = PrivateKey.fromByteArray(sm9Curve, Hex.decode(hexStrSignatureKey));
        byte[] byteArrayMessage = strMessage.getBytes();
        MasterPublicKey masterPublicKey = MasterPublicKey.fromByteArray(sm9Curve, Hex.decode(MPK));
        try {
            resultSignature = sm9.sign(masterPublicKey, signatureKey, byteArrayMessage);
            hexStrResultSignature = Hex.encodeToString(resultSignature.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hexStrResultSignature;
    }

    @RequestMapping("/generator_masterkeypair")
    public String generator_masterkeypair() {
        MasterKeyPair masterKeyPair = kgc.genSignMasterKeyPair();
        return masterKeyPair.toString();
    }

}

