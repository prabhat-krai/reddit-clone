package com.prabhat.springit.controller;

import com.prabhat.springit.domain.Comment;
import com.prabhat.springit.domain.Link;
import com.prabhat.springit.domain.User;
import com.prabhat.springit.repository.UserRepository;
import com.prabhat.springit.service.CommentService;
import com.prabhat.springit.service.LinkService;
import com.prabhat.springit.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class LinkController {

    public LinkService linkService;
    public CommentService commentService;
    public UserService userService;

    public LinkController(LinkService linkService, CommentService commentService, UserService userService) {
        this.linkService = linkService;
        this.commentService = commentService;
        this.userService = userService;
    }

    private static final Logger logger = LoggerFactory.getLogger(LinkController.class);

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("links", linkService.findAll());
        return "link/list";
    }

    @GetMapping("/link/{id}")
    public String read(@PathVariable Long id, Model model) {
        Optional<Link> link = linkService.findById(id);
        if ( link.isPresent() ) {
            Link currentLink = link.get();
            Comment comment = new Comment();
            comment.setLink(currentLink);
            model.addAttribute("comment", comment);
            model.addAttribute("link", currentLink);
            model.addAttribute("success", model.containsAttribute("success"));
            return "link/view";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/link/submit")
    public String newLinkForm(Model model) {
        model.addAttribute("link",new Link());
        return "link/submit";
    }

    @PostMapping("/link/submit")
    public String createLink(@Valid Link link, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            logger.info("Validation errors were found while submitting a link");
            model.addAttribute("link", link);
            return "link/submit";
        } else {
            linkService.save(link);
            Optional<User> user = userService.findByEmail(link.getCreatedBy());
            user.ifPresent(link::setUser);
            linkService.save(link);
            logger.info("New link was saved successfully");
            redirectAttributes
                    .addAttribute("id", link.getId())
                    .addFlashAttribute("success", true);
            return "redirect:/link/{id}";
        }
    }

    @Secured({"ROLE_USER"})
    @PostMapping("/link/comments")
    public String addComment(@Valid Comment comment, BindingResult bindingResult) {
        if ( bindingResult.hasErrors() ) {
            logger.error("Comment creation failed");
        } else {
            commentService.save(comment);
            logger.info("New comment was saved");
        }

        return "redirect:/link/" +comment.getLink().getId();
    }
}
