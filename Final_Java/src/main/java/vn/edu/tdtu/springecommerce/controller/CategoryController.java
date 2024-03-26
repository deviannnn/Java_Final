package vn.edu.tdtu.springecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.tdtu.springecommerce.model.Category;
import vn.edu.tdtu.springecommerce.service.CategoryService;

import java.util.List;

@Controller
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping(produces = "application/json")
    @ResponseBody
    public List<Category> listAllCategory() {
        return categoryService.findAll();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    @ResponseBody
    public Category findById(@PathVariable("id") int id) {
        return categoryService.findById(id);
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("category") Category item, RedirectAttributes model) {
        try {
            categoryService.save(item);
            model.addFlashAttribute("messageForAdd", "Successfully added feature.");
            model.addFlashAttribute("colorForAdd", "green");
        } catch (Exception e) {
            model.addFlashAttribute("messageForAdd", "Add feature failed. Please try again later.");
            model.addFlashAttribute("colorForAdd", "red");
        }
        return "redirect:/admin/category";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        try {
            categoryService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted feature");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Delete feature failed. Please try again later.");
        }
    }

    @PostMapping("/update")
    public String edit(@ModelAttribute("category") Category item, RedirectAttributes model) {
        try {
            categoryService.save(item);
            model.addFlashAttribute("messageForUpdate", "Successfully updated feature.");
            model.addFlashAttribute("colorForUpdate", "green");
        } catch (Exception e) {
            model.addFlashAttribute("messageForUpdate", "Updated feature failed. Please try again later.");
            model.addFlashAttribute("colorForUpdate", "red");
        }
        return "redirect:/admin/category";
    }
}
