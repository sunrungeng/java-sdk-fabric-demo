package cn.edu.bit.controller;

import cn.edu.bit.utils.Init;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@RestController
@RequestMapping("/auth")
public class AuthController {

    public static Path walletPath;
    public static Wallet wallet;
    public static Path networkConfigPath;
    public static Gateway.Builder builder;
    public static Gateway gateway;
    public static Network network;

    @RequestMapping("/initCert")
    public static void initCert() throws IOException {
        // Load a file system based wallet for managing identities.
        walletPath = Paths.get("wallet");
        wallet = Wallet.createFileSystemWallet(walletPath);

        // load a CCP
        networkConfigPath = Paths.get("..", "..", "first-network", "connection-org1.yaml");
        builder = Gateway.createBuilder();
        builder.identity(wallet, "user1").networkConfig(networkConfigPath).discovery(true);
        gateway = builder.connect();
        network = gateway.getNetwork("mychannel");
    }

    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
    }

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
        byte[] resultBytes;
        Contract contract = network.getContract("Authentication");
        resultBytes = contract.submitTransaction("registerTop", ID, MPK);
        return new String(resultBytes);
    }

    @RequestMapping("/registerNotTop")
    public String registerNotTop(String IDi, String IDi_1) throws Exception {
        byte[] resultBytes;
        Contract contract = network.getContract("Authentication");
        resultBytes = contract.submitTransaction("registerNotTop", IDi, IDi_1);
        return new String(resultBytes);
    }

    @RequestMapping("/queryMPK")
    public String queryMPK(String ID) throws Exception {
        byte[] result;
        Contract contract = network.getContract("Authentication");
        result = contract.evaluateTransaction("queryMPK", ID);
        return new String(result);
    }

    @RequestMapping("/verifyAndUploadMPK")
    public String verifyAndUploadMPK(String IDi, String IDi_1, String message, String signature) throws Exception {
        byte[] result;
        Contract contract = network.getContract("Authentication");
        result = contract.submitTransaction("verifyAndUpload", IDi, IDi_1, message, signature);
        return new String(result);
    }

    @RequestMapping("/verifyAndRegisterNotTop")
//    public String verifyAndRegisterNotTop(String IDi, String IDi_1, String message, String signature) throws Exception {
    public String verifyAndRegisterNotTop(String domain, String identity, String type, String time, String signature) throws Exception {
        byte[] result;
        Contract contract = network.getContract("Authentication");
        result = contract.submitTransaction("verifyAndRegisterNotTop", domain, identity, type, time, signature);
        return new String(result);
    }

    @RequestMapping("/updateMPK")
    public String updateMPK(String ID, String MPK) throws Exception {
        byte[] result;
        Contract contract = network.getContract("Authentication");
        result = contract.submitTransaction("verifyAndUpload", ID, MPK);
        return new String(result);
    }

    @RequestMapping("/updateInfo")
    public String updateInfo(String info) throws Exception {
        byte[] result;
        Contract contract = network.getContract("Authentication");
        result = contract.submitTransaction("update", info);
        return new String(result);
    }

    @RequestMapping("/queryInfo")
    public String queryInfo(String ID) throws Exception {
        byte[] result;
        Contract contract = network.getContract("Authentication");
        result = contract.evaluateTransaction("query", ID);
        return new String(result);
    }

    @RequestMapping("/queryZone")
    public String queryZone(String ID) throws Exception {
        byte[] result;
        Contract contract = network.getContract("Authentication");
        result = contract.evaluateTransaction("queryZone", ID);
        return new String(result);
    }

    @RequestMapping("/queryZoneMPK")
    public String queryZoneMPK(String ID) throws Exception {
        byte[] result;
        Contract contract = network.getContract("Authentication");
        result = contract.evaluateTransaction("queryZoneMPK", ID);
        return new String(result);
    }

    @RequestMapping("/queryZoneInfo")
    public String queryZoneInfo(String ID) throws Exception {
        byte[] result;
        Contract contract = network.getContract("Authentication");
        result = contract.evaluateTransaction("queryZoneInfo", ID);
        return new String(result);
    }

    @RequestMapping("/queryZoneMPKAndID")
    public String queryZoneMPKAndID(String ID) throws Exception {
        byte[] result;
        Contract contract = network.getContract("Authentication");
        result = contract.evaluateTransaction("queryZoneMPKAndID", ID);
        return new String(result);
    }

    @RequestMapping("/verifyDevice")
    public String verifyDevice(String accesserIdentity, String accessedDevice, String action, String time, String signature) throws Exception {
        byte[] result;
        Contract contract = network.getContract("Authentication");
        result = contract.evaluateTransaction("verifyDevice", accesserIdentity, accessedDevice, action, time, signature);
        return new String(result);
    }

//    submitTransaction上链 evaluateTransaction查询

//    @RequestMapping("/insert")
//    public String insert(AuthUser user) throws Exception {
//        Genson genson = new Genson();
//        // Load a file system based wallet for managing identities.
//        Path walletPath = Paths.get("wallet");
//        Wallet wallet = Wallet.createFileSystemWallet(walletPath);
//
//        // load a CCP
//        Path networkConfigPath = Paths.get("..", "..", "first-network", "connection-org1.yaml");
//        Gateway.Builder builder = Gateway.createBuilder();
//        builder.identity(wallet, "user1").networkConfig(networkConfigPath).discovery(true);
//
//        byte[] result;
//        try (Gateway gateway = builder.connect()) {
//            // get the network and contract
//            Network network = gateway.getNetwork("mychannel");
//            Contract contract = network.getContract("Authentication");
//            result = contract.submitTransaction("insert", genson.serialize(user));
//        }
//        return new String(result);
//    }
//
//    @RequestMapping("/update")
//    public String update(AuthUser user) throws Exception{
//        Genson genson = new Genson();
//        // Load a file system based wallet for managing identities.
//        Path walletPath = Paths.get("wallet");
//        Wallet wallet = Wallet.createFileSystemWallet(walletPath);
//
//        // load a CCP
//        Path networkConfigPath = Paths.get("..", "..", "first-network", "connection-org1.yaml");
//        Gateway.Builder builder = Gateway.createBuilder();
//        builder.identity(wallet, "user1").networkConfig(networkConfigPath).discovery(true);
//
//        byte[] result;
//        try (Gateway gateway = builder.connect()) {
//            // get the network and contract
//            Network network = gateway.getNetwork("mychannel");
//            Contract contract = network.getContract("Authentication");
//            result = contract.submitTransaction("update", genson.serialize(user));
//        }
//        return new String(result);
//    }
//
//    @RequestMapping("/query")
//    public String query(AuthUser user) throws Exception{
//        Genson genson = new Genson();
//        // Load a file system based wallet for managing identities.
//        Path walletPath = Paths.get("wallet");
//        Wallet wallet = Wallet.createFileSystemWallet(walletPath);
//
//        // load a CCP
//        Path networkConfigPath = Paths.get("..", "..", "first-network", "connection-org1.yaml");
//        Gateway.Builder builder = Gateway.createBuilder();
//        builder.identity(wallet, "user1").networkConfig(networkConfigPath).discovery(true);
//
//        byte[] result;
//        try (Gateway gateway = builder.connect()) {
//            // get the network and contract
//            Network network = gateway.getNetwork("mychannel");
//            Contract contract = network.getContract("Authentication");
//            result = contract.submitTransaction("query", user.getIdentify());
//        }
//        return new String(result);
//    }
//
//    @RequestMapping("/queryMPK2")
//    public String queryMPK2(AuthUser user) throws Exception{
//        Genson genson = new Genson();
//        // Load a file system based wallet for managing identities.
//        Path walletPath = Paths.get("wallet");
//        Wallet wallet = Wallet.createFileSystemWallet(walletPath);
//
//        // load a CCP
//        Path networkConfigPath = Paths.get("..", "..", "first-network", "connection-org1.yaml");
//        Gateway.Builder builder = Gateway.createBuilder();
//        builder.identity(wallet, "user1").networkConfig(networkConfigPath).discovery(true);
//
//        byte[] result;
//        try (Gateway gateway = builder.connect()) {
//            // get the network and contract
//            Network network = gateway.getNetwork("mychannel");
//            Contract contract = network.getContract("Authentication");
//            result = contract.submitTransaction("query", user.getIdentify());
//            JSONObject object = JSONObject.fromObject(new String(result));
//            AuthUser userTemp = genson.deserialize((String) object.get("data"), AuthUser.class);
//            object.put("data", userTemp.getPublicParameter());
//            return object.toString();
//        }
//    }

//    @RequestMapping("/testGenson")
//    public String testGenson() {
//        Genson genson = new Genson();
//        AuthUser user = new AuthUser("sdf", "sfsd", "dfsdf", 0, 0, "sdfsd", "afad");
//        return genson.serialize(user);
//    }
}

