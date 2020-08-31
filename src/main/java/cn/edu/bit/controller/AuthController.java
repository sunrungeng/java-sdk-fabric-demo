package cn.edu.bit.controller;

import cn.edu.bit.utils.Init;
import org.hyperledger.fabric.gateway.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/auth")
public class AuthController {

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

//        Genson genson = new Genson();
        Path walletPath = Paths.get("wallet");
        Wallet wallet = Wallet.createFileSystemWallet(walletPath);

        Path networkConfigPath = Paths.get("..", "..", "first-network", "connection-org1.yaml");
        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, "user1").networkConfig(networkConfigPath).discovery(true);

        byte[] result;
        try (Gateway gateway = builder.connect()) {
            Network network = gateway.getNetwork("mychannel");
            Contract contract = network.getContract("Authentication");
            result = contract.submitTransaction("registerTop", ID, MPK);
        }
        return new String(result);
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

        byte[] resultBytes;
        try (Gateway gateway = builder.connect()) {
            Network network = gateway.getNetwork("mychannel");
            Contract contract = network.getContract("Authentication");

            resultBytes = contract.submitTransaction("registerNotTop", IDi, IDi_1);
            return new String(resultBytes);
        }
    }

    @RequestMapping("/queryMPK")
    public String queryMPK(String ID) throws Exception {
        byte[] result;

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
            result = contract.evaluateTransaction("queryMPK", ID);
        }
        return new String(result);
    }

    @RequestMapping("/verifyAndUploadPara")
    public String verifyAndUploadPara(String IDi, String IDi_1, String message, String signature) throws Exception {
        byte[] result;

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
            result = contract.submitTransaction("verifyAndUpload", IDi, IDi_1, message, signature);
        }
        return new String(result);
    }

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

