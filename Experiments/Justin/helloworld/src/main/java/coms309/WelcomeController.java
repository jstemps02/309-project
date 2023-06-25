package coms309;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.bind.annotation.*;

@RestController
class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "Hello, try to POST s1, s2, and t1 as a JSON and see if you can find a pattern!" + "\n" + "And Maybe there is a secret....";
    }

    @GetMapping("/Secret")
    public String welcome2() {
        return "t1 key: 1 is Add, 2 is Sub, 3 is Mult, 4 is Div, and 5 is Mod";
    }



    @PostMapping("/")
    public String welcome(@RequestBody Calculator s) {
        return s.mainCalc();
    }
}

