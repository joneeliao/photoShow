/**
 * Created by Leo on 2017/7/30.
 */

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import xxct.base.domain.User;
import xxct.base.service.UserService;

public class TestSpring {
    @Test
    public void TestUserService() throws Exception {

        @SuppressWarnings("Resource")
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService us = (UserService) ac.getBean("userService");
        System.out.println("Get userService:" + us.toString());
        User user = new User();
        user.setPassword("1111111");
        user.setUserName(String.valueOf(System.currentTimeMillis()));
        user.setRoleName("1111111");
        //user.setId(11213);
        System.out.println("Beign to add user-->");
        int i = us.addUser(user);

    }
}

