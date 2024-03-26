package vn.edu.tdtu.springecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.tdtu.springecommerce.model.Category;
import vn.edu.tdtu.springecommerce.model.Feature;
import vn.edu.tdtu.springecommerce.service.FeatureService;

import java.util.List;

@Controller
@RequestMapping("/api/feature")
public class FeatureController {
    @Autowired
    private FeatureService featureService;

    @GetMapping(produces = "application/json")
    @ResponseBody
    public List<Feature> listAllFeature() {
        return featureService.findAll();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    @ResponseBody
    public Feature findById(@PathVariable("id") int id) {
        return featureService.findById(id);
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("feature") Feature item, RedirectAttributes model) {
        try {
            featureService.save(item);
            model.addFlashAttribute("messageForAdd", "Successfully added feature.");
            model.addFlashAttribute("colorForAdd", "green");
        } catch (Exception e) {
            model.addFlashAttribute("messageForAdd", "Add feature failed. Please try again later.");
            model.addFlashAttribute("colorForAdd", "red");
        }
        return "redirect:/admin/feature";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        try {
            featureService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted feature");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Delete feature failed. Please try again later.");
        }
    }

    @PostMapping("/update")
    public String edit(@ModelAttribute("feature") Feature item, RedirectAttributes model) {
        try {
            featureService.save(item);
            model.addFlashAttribute("messageForUpdate", "Successfully updated feature.");
            model.addFlashAttribute("colorForUpdate", "green");
        } catch (Exception e) {
            model.addFlashAttribute("messageForUpdate", "Updated feature failed. Please try again later.");
            model.addFlashAttribute("colorForUpdate", "red");
        }
        return "redirect:/admin/feature";
    }
}
