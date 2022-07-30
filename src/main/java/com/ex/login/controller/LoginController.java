package com.ex.login.controller;

import com.ex.board.service.BoardService;
import com.ex.login.SessionConst;
import com.ex.login.model.UserVO;
import com.ex.login.service.UserService;
import com.mysql.cj.PreparedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@Slf4j
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

    @GetMapping("/loginPage")
    public String getUserInfo(HttpServletResponse response, HttpServletRequest request, Model model) throws Exception {

            UserVO userVO = (UserVO) request.getAttribute(SessionConst.SESSION_ID);

            if (userVO == null) {
                return "login/login";
            }

        return "redirect:/board/getBoardList";
    }

    @GetMapping("/userSign")
    public String userSign(Model model) throws Exception {
        model.addAttribute("userInfo", new UserVO());
        return "login/sign";
    }

    @PostMapping("/saveUser")
    public String saveUser(UserVO userVO, Model model, RedirectAttributes rttr) throws Exception {

        if ("".equals(userVO.getUserId()) || userVO.getUserId() == null || "".equals(userVO.getUserPass()) || userVO.getUserPass() == null) {
            return "redirect:/login/userSign";
        }

        UserVO userInfo = userService.getUserInfo(userVO.getUserId());
        log.info("user = {}", userInfo);
        if (userInfo == null) {
            userVO.setLoginYn("Y");
            int saveInfo = userService.saveInfo(userVO);
            model.addAttribute("saveInfo", saveInfo);
            return "redirect:/login/loginPage";
        } else {
            rttr.addFlashAttribute("errorMsg", "이미 가입된 아이디 입니다.");
            return "redirect:/login/userSign";
        }

    }

    @PostMapping("/loginCheck")
    public String loginCheck(@Valid @ModelAttribute("userVO") UserVO userVO, BindingResult bindingResult,
                             Model model, HttpServletRequest request, HttpServletResponse response,
                             RedirectAttributes rttr) throws Exception {

        if (bindingResult.hasErrors()) {
            bindingResult.reject("failedUser", "미인증 사용자 입니다.");
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.SESSION_ID, userVO.getUserId());
        UserVO userInfo = userService.getUserInfo(userVO.getUserId());

        if (userInfo == null) {
            rttr.addFlashAttribute("noIdPw", "아이디와 비밀번호 입력은 필수입니다.");
            return "redirect:/login/loginPage";
        }

        if (userVO.getUserId() == null || "".equals(userVO.getUserId()) || "".equals(userVO.getUserPass()) || userVO.getUserPass() == null) {
            rttr.addFlashAttribute("noIdPw", "아이디와 비밀번호 입력은 필수입니다.");
            return "redirect:/login/loginPage";
        }

        if (!userInfo.getUserId().equals(userVO.getUserId()) || !userInfo.getUserPass().equals(userVO.getUserPass())) {
            rttr.addFlashAttribute("noUser", "등록되지 않은 사용자 입니다.");
            return "redirect:/login/loginPage";
        }

        Cookie cookie = new Cookie(SessionConst.SESSION_ID, userVO.getUserId());
        response.addCookie(cookie);


        return "redirect:/board/getBoardList";

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return "redirect:/login/loginPage";
    }
}
