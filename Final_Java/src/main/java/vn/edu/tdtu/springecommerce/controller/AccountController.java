package vn.edu.tdtu.springecommerce.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.tdtu.springecommerce.model.Account;
import vn.edu.tdtu.springecommerce.model.Customer;
import vn.edu.tdtu.springecommerce.service.AccountService;
import vn.edu.tdtu.springecommerce.service.CustomerService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.tdtu.springecommerce.service.EmailService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private EmailService emailService;

    @GetMapping(produces = "application/json")
    @ResponseBody
    public List<Customer> listAllCustomer() {
        return customerService.findAll();
    }

    @PostMapping("/register")
    public String register(@RequestParam("name") String name, @RequestParam("username") String username,
                           @RequestParam("password") String password, @RequestParam("repassword") String repassword,
                           HttpSession session, RedirectAttributes model) {
        model.addFlashAttribute("name", name);
        model.addFlashAttribute("username", username);
        model.addFlashAttribute("password", password);
        model.addFlashAttribute("repassword", repassword);
        if (name.equals("") || username.equals("") || password.equals("") || repassword.equals("")) {
            model.addFlashAttribute("registerFail", "Sorry. Information is incomplete.");
            return "redirect:/register";
        } else if (accountService.findByUsername(username) != null) {
            model.addFlashAttribute("registerFail", "Sorry. This username has existed.");
            return "redirect:/register";
        } else if (password.length() < 6) {
            model.addFlashAttribute("registerFail", "Sorry. Password must have at least 6 characters.");
            return "redirect:/register";
        } else if (!password.equals(repassword)) {
            model.addFlashAttribute("registerFail", "Sorry. Confirm password didn't correct.");
            return "redirect:/register";
        } else {
            String code = UUID.randomUUID().toString();
            session.setAttribute("name", name);
            session.setAttribute("username", username);
            session.setAttribute("password", password);
            session.setAttribute("code", code);
            String link = "http://localhost:8080/api/account/verify?code=" + code;
            String message = "Hi " + name + ", here is the confirmation link to register your account: " + link;
            emailService.sendEmail(username, "Confirm Register", message);
            model.addFlashAttribute("registerSuccess", "A confirmation link has been sent to your email. Please confirm to register your account!");
            return "redirect:/login";
        }
    }

    @GetMapping("/verify")
    public String verify(@RequestParam("code") String code, HttpSession session, Model model) {
        if (code == null || code.isEmpty()) {
            model.addAttribute("registerFail", "Sorry. Invalid verification code.");
            return "notice";
        } else if (!code.equals(session.getAttribute("code"))) {
            model.addAttribute("registerFail", "Sorry. Invalid verification code.");
            return "notice";
        } else {
            String name = (String) session.getAttribute("name");
            String username = (String) session.getAttribute("username");
            String password = (String) session.getAttribute("password");
            model.addAttribute("registerSuccess", "Congratulations, you have successfully registered");
            accountService.register(username, password, name);
            session.removeAttribute("name");
            session.removeAttribute("username");
            session.removeAttribute("password");
            session.removeAttribute("code");
            return "notice";
        }
    }


    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password,
                        RedirectAttributes model, HttpSession session) {
        model.addFlashAttribute("username", username);
        model.addFlashAttribute("password", password);
        if (username.equals("") || password.equals("")) {
            model.addFlashAttribute("loginFail", "Sorry. Information is incomplete.");
            return "redirect:/login";
        }
        Account loginAccount = accountService.login(username, password);
        if (loginAccount == null) {
            model.addFlashAttribute("loginFail", "Sorry. Username or password didn't correct.");
            return "redirect:/login";
        }
        int permission = loginAccount.getPermission();
        session.setAttribute("isLogged", true);
        session.setAttribute("permission", permission);
        if (permission >= 2) {
            return "redirect:/admin";
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String today = formatter.format(date);

            Customer currentCustomer = customerService.findByAccount_Id(loginAccount.getId());
            currentCustomer.setLastActive(today);

            session.setAttribute("customer", currentCustomer);
            return "redirect:/";
        }
    }

    @PostMapping("/update/{id}")
    public String updatePermission(@PathVariable("id") int accountId, @RequestParam("permission") int permission) {
        accountService.updatePermit(accountId, permission);
        return "redirect:/admin/account";
    }

}
