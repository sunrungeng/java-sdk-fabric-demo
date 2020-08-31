import net.sf.json.JSONObject;

import java.util.Date;

public class t1 {

    public static void main(String[] args) {
//        Date date = new Date();
//        String addTime = date.toString();
//        System.out.println(addTime);
        JSONObject object = new JSONObject();
        object.put("mpk", "0127b9d60de9d9e37a32c9d664ccca43c33bb0566d4392d1aade73197db288c4230687d7eeca6fa2b430970c9a76a741b7256b60a15ad99607dfb9cfeb812577690a9063fcee9bdcc66b0cc6569daf3d36538c92eed32e7aaaed69a2cc1da27194552b4dc9bac1fbb885a8b2aab6610695e8c575d97b072413b1119001b883a8ee");
        object.put("m1", "bbbbaaaaacccc");
        System.out.println(object.toString());
    }

}
