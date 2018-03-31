package xxct.base.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import xxct.base.dao.UserDao;
import xxct.base.domain.User;
import xxct.base.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    public User login(User user) {
        return userDao.login(user);
    }

    public List<User> findUser(Map<String, Object> map) {
        return userDao.findUsers(map);
    }

    public int updateUser(User user) {
        if ("admin".equals(user.getUserName())) {
            return 0;
        }
        return userDao.updateUser(user);

    }

    public Long getTotalUser(Map<String, Object> map) {
        return userDao.getTotalUser(map);
    }

    public int addUser(User user) {
        return userDao.addUser(user);
    }

    public int deleteUser(Integer id) {
        if (2 == id) {
            return 0;
        }
        return userDao.deleteUser(id);
    }

}
