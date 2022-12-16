package net.phadata.rabbitmq.controller;

import net.phadata.rabbitmq.service.RabbitManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author felix
 */
@Deprecated
@Controller
public class RabbitPageController {


    @Autowired
    private RabbitManagementService rabbitManagementService;

    @GetMapping(value = {"/", "/exchanges"})
    public String exchange(Model model) {
        model.addAttribute("exchanges", rabbitManagementService.findAllExchange());
        return "exchange";
    }

    @GetMapping(value = {"/login"})
    public String login(Model model) {
        return "login";
    }

    @GetMapping(value = "/queues")
    public String queue(Model model) {
        model.addAttribute("queues", rabbitManagementService.findAllQueue());
        return "queue";
    }

    @GetMapping(value = "/bind")
    public String bind(Model model) {
        model.addAttribute("exchanges", rabbitManagementService.findAllExchange());
        model.addAttribute("queues", rabbitManagementService.findAllQueue());
        model.addAttribute("bindings", rabbitManagementService.findAllBinding());
        return "binding";
    }

    @GetMapping(value = "/unbind")
    public String unbind(@RequestParam("unbind") Integer unbind) {
        rabbitManagementService.unBinding(unbind);
        return "redirect:bind";
    }
}
