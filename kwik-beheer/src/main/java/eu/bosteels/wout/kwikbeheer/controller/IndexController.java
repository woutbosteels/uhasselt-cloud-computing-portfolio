package eu.bosteels.wout.kwikbeheer.controller;

import jakarta.websocket.server.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IndexController {

    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/chat")
    public String chat() {
        return "chat";
    }

    @GetMapping("room/{room_id}/temperature")
    public String temperature(@PathVariable("room_id") Long id, Model model) {
        log.debug(String.valueOf(id));
        model.addAttribute("ws_uri", String.format("/temp?room_id=%d", id));
        return "temperature";
    }

    @GetMapping("/rooms")
    public String rooms(@PathParam("building") String building, Model model){
        model.addAttribute("building", building);
        return "rooms";
    }

}

