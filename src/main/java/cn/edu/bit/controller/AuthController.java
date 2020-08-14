package cn.edu.bit.controller;

import cn.edu.bit.gm.sm9.*;
import cn.edu.bit.utils.CertUtils;
import cn.edu.bit.utils.Hex;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.Properties;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {



    @RequestMapping("/test")
    public String test() {
        return "hello world";
    }

    @RequestMapping("/initLedger")
    public void initLedger() throws Exception {
        HFClient client = HFClient.createNewInstance();
        Channel channel = initChannel(client);
        TransactionProposalRequest req = client.newTransactionProposalRequest();
        ChaincodeID cid = ChaincodeID.newBuilder().setName("Authentication").build();
        req.setChaincodeID(cid);
        req.setChaincodeLanguage(TransactionRequest.Type.JAVA);
        req.setFcn("initLedger");

        // 发送proprosal
        Collection<ProposalResponse> resps = channel.sendTransactionProposal(req);
        // 提交给orderer节点
        channel.sendTransaction(resps);
    }

    @RequestMapping("/registerTop")
    public String registerTop(String ID, String MPK) throws Exception {
        HFClient client = HFClient.createNewInstance();
        Channel channel = initChannel(client);
        TransactionProposalRequest req = client.newTransactionProposalRequest();
        ChaincodeID cid = ChaincodeID.newBuilder().setName("Authentication").build();
        req.setChaincodeID(cid);
        req.setChaincodeLanguage(TransactionRequest.Type.JAVA);
        req.setFcn("registerTop");
        req.setArgs(new String[]{ID, MPK});

        // 发送proprosal
        Collection<ProposalResponse> resps = channel.sendTransactionProposal(req);
        // 提交给orderer节点
        channel.sendTransaction(resps);
        return "SUCCESS";
    }

    @RequestMapping("/registerNotTop")
    public String registerNotTop(String IDi, String IDi_1) throws Exception {
        HFClient client = HFClient.createNewInstance();
        Channel channel = initChannel(client);
        TransactionProposalRequest req = client.newTransactionProposalRequest();
        ChaincodeID cid = ChaincodeID.newBuilder().setName("Authentication").build();
        req.setChaincodeID(cid);
        req.setChaincodeLanguage(TransactionRequest.Type.JAVA);
        req.setFcn("registerNotTop");
        req.setArgs(new String[]{IDi, IDi_1});

        // 发送proprosal
        Collection<ProposalResponse> resps = channel.sendTransactionProposal(req);
        // 提交给orderer节点
        channel.sendTransaction(resps);
        return "SUCCESS";
    }

    @RequestMapping("/queryMPK")
    public String queryMPK(String ID) throws Exception {
        HFClient client = HFClient.createNewInstance();
        Channel channel = initChannel(client);

        // 构建proposal
        QueryByChaincodeRequest req = client.newQueryProposalRequest();
        // 指定要调用的chaincode
        ChaincodeID cid = ChaincodeID.newBuilder().setName("Authentication").build();
        req.setChaincodeID(cid);
        req.setChaincodeLanguage(TransactionRequest.Type.JAVA);
        req.setFcn("queryMPK");
        req.setArgs(ID);

        String payload = "";
        // 发送proprosal
        Collection<ProposalResponse> resps = channel.queryByChaincode(req);
        for (ProposalResponse resp : resps) {
            payload = new String(resp.getChaincodeActionResponsePayload());
            System.out.println("response: " + payload);
        }
        return payload;
    }

    @RequestMapping("/verifyAndUploadPara")
    public String verifyAndUploadPara(String IDi, String IDi_1, String message, String signature) throws Exception {
        HFClient client = HFClient.createNewInstance();
        Channel channel = initChannel(client);
        TransactionProposalRequest req = client.newTransactionProposalRequest();
        ChaincodeID cid = ChaincodeID.newBuilder().setName("Authentication").build();
        req.setChaincodeID(cid);
        req.setChaincodeLanguage(TransactionRequest.Type.JAVA);
        req.setFcn("verifyAndUploadPara");
        req.setArgs(new String[]{IDi, IDi_1, message, signature});

        // 发送proprosal
        Collection<ProposalResponse> resps = channel.sendTransactionProposal(req);
        // 提交给orderer节点
        channel.sendTransaction(resps);
        return "SUCCESS";
    }

    /**
     * 用户注册, 保存证书和私钥
     *
     * @param username Fabric CA Admin用户的用户名
     * @param password Fabric CA Admin用户的密码
     * @param certDir  目录名, 用来保存证书和私钥
     * @throws Exception
     */
    @RequestMapping("/enroll")
    private static void enroll(String username, String password, String certDir) throws Exception {
        HFClient client = HFClient.createNewInstance();
        CryptoSuite cs = CryptoSuite.Factory.getCryptoSuite();
        client.setCryptoSuite(cs);

        Properties prop = new Properties();
        prop.put("verify", false);
        HFCAClient caClient = HFCAClient.createNewInstance("http://localhost:7054", prop);
        caClient.setCryptoSuite(cs);

        // enrollment保存了证书和私钥
        Enrollment enrollment = caClient.enroll(username, password);
        System.out.println(enrollment.getCert());

        // 保存到本地文件
        CertUtils.saveEnrollment(enrollment, certDir, username);
    }


    private static Channel initChannel(HFClient client) throws Exception {
        CryptoSuite cs = CryptoSuite.Factory.getCryptoSuite();
        client.setCryptoSuite(cs);
        client.setUserContext(new AuthUser("admin", CertUtils.loadEnrollment("cert", "admin")));

        // 初始化channel
        Channel channel = client.newChannel("mychannel");
        channel.addPeer(client.newPeer("peer", "grpc://localhost:7051"));

        // 指定排序节点地址, 无论是后面执行查询还是更新都必须指定排序节点
        channel.addOrderer(client.newOrderer("orderer", "grpc://localhost:7050"));
        channel.initialize();
        return channel;
    }


}

class AuthUser implements User {
    private String name;
    private Enrollment enrollment;

    public AuthUser(String name, Enrollment enrollment) {
        this.name = name;
        this.enrollment = enrollment;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Set<String> getRoles() {
        return Collections.emptySet();
    }

    @Override
    public String getAccount() {
        return "";
    }

    @Override
    public String getAffiliation() {
        return "";
    }

    @Override
    public Enrollment getEnrollment() {
        return this.enrollment;
    }

    @Override
    public String getMspId() {
        return "Org1MSP";
    }
}
