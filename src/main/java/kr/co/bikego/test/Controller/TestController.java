package kr.co.bikego.test.Controller;

import kr.co.bikego.test.domain.entity.User;
import kr.co.bikego.test.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/demo")
public class TestController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/add.do")
    public @ResponseBody
    String addNewUser(@RequestParam String name, @RequestParam String email) {

        User n = new User();
        n.setName(name);
        n.setEmail(email);
        userRepository.save(n);
        return "saved";
    }

    @GetMapping(path = "/all.do")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

}
