import cn.edu.bit.utils.Sha256Util;
import net.sf.json.JSONObject;

import java.util.Date;


public class t1 {

    public static void main(String[] args) {
//        Date date = new Date();
//        String addTime = date.toString();
//        System.out.println(addTime);
//        JSONObject object = new JSONObject();
//        object.put("mpk", "0127b9d60de9d9e37a32c9d664ccca43c33bb0566d4392d1aade73197db288c4230687d7eeca6fa2b430970c9a76a741b7256b60a15ad99607dfb9cfeb812577690a9063fcee9bdcc66b0cc6569daf3d36538c92eed32e7aaaed69a2cc1da27194552b4dc9bac1fbb885a8b2aab6610695e8c575d97b072413b1119001b883a8ee");
//        object.put("m1", "bbbbaaaaacccc");
//        System.out.println(object.toString());
//        Date date = new Date();
//        System.out.println(date.getTime());
//        sign2();
//        genInfoJson();
        System.out.println(new Date());
    }

    public static void sign2() {
        JSONObject object = new JSONObject();
        object.put("accesserIdentity", "tsinghua.edu.cn");
        object.put("accessedDevice", "watch.tsinghua.edu.cn");
        object.put("time", "1599026889379");
        object.put("action", "open");
        String hashString = Sha256Util.getSHA256(object.toString());
        System.out.println(hashString);
    }

    public static void genInfoJson() {
        JSONObject object = new JSONObject();
        object.put("belongto", "");
        object.put("identity", "tsinghua.edu.cn");
        object.put("zone", "edu.cn");
        object.put("idCount", 0);
        object.put("deviceCount", 0);
        object.put("publicParameter", "0197d9144ba59f7f2f175fc4a7890406c4a5c5f90c235b632e849c7c3410fc1fe43ba31b914b366d0be77b5c3353ae61aa142cb5b033b3af23da0771b9c9c4fd699d5191167b7d71e31b7eff8cefe201d9f82d28b839564b09fa72e6acfe6f51b10cd578c737def1a7b62712a77b579350a6898165d45dba1b7c44ddfe06233646");
        object.put("addTime", "1599029263981");
//        String hashString = Sha256Util.getSHA256(object.toString());
        System.out.println(object.toString());
    }

}
