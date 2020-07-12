package com.worldnavigator.web.controllers;

import com.worldnavigator.web.services.MazeTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("mazes")
public class MazeTemplateController {

    private final MazeTemplateService mazeTemplateService;

    @Autowired
    public MazeTemplateController(MazeTemplateService mazeTemplateService) {
        this.mazeTemplateService = mazeTemplateService;
    }

    @PostMapping
    public String create(@RequestParam MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        //storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }
}
