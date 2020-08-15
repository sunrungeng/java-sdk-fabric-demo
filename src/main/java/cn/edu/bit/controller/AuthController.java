package cn.edu.bit.controller;

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
@RequestMapping("/auth")
public class AuthController {

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
            result = contract.submitTransaction("registerTop", ID, MPK);
        }
        return new String(result);
    }

    @RequestMapping("/registerNotTop")
    public String registerNotTop(String IDi, String IDi_1) throws Exception {
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
            result = contract.submitTransaction("registerNotTop", IDi, IDi_1);
        }
        return new String(result);
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
            result = contract.submitTransaction("verifyAndUploadPara", IDi, IDi_1, message, signature);
        }
        return new String(result);
    }


}

