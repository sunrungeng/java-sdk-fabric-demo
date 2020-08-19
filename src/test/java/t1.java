import cn.edu.bit.common.JsonView;
import cn.edu.bit.entity.AuthUser;

public class t1 {

    public static void main(String[] args) {
        AuthUser user = new AuthUser("test", "tee");
        System.out.println(JsonView.render(user));
    }

}
