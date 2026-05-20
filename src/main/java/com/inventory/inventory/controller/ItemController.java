package com.inventory.inventory.controller;

import com.inventory.inventory.model.Item;
import com.inventory.inventory.service.FileService;
import jakarta.validation.Valid;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class ItemController {

    private final FileService fileService;

    public ItemController(FileService fileService) {
        this.fileService = fileService;
    }

    // ---------------- HOME ----------------
    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }

    // ---------------- DASHBOARD ----------------
    @GetMapping("/dashboard")
    public String dashboard(Model model,
                            @RequestParam(value = "search", required = false) String search)
            throws IOException {

        List<Item> items = fileService.getAllItems();

        if (search != null && !search.isEmpty()) {
            items = items.stream()
                    .filter(i -> i.getName() != null &&
                            i.getName().toLowerCase().contains(search.toLowerCase()))
                    .toList();
        }

        model.addAttribute("items", items);
        return "dashboard";
    }

    // ---------------- ADD PAGE ----------------
    @GetMapping("/add")
    public String addPage(Model model) {
        model.addAttribute("item", new Item());
        return "add-item";
    }

    // ---------------- ADD ITEM (ONLY ONE POST MAPPING FIX) ----------------
    @PostMapping("/add")
    public String addItem(@Valid @ModelAttribute Item item,
                          BindingResult result) throws IOException {

        if (result.hasErrors()) {
            return "add-item";
        }

        fileService.saveItem(item);

        return "redirect:/dashboard?success=added";
    }

    // ---------------- DELETE ITEM (FIXED TYPE) ----------------
    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable String id) throws IOException {

        fileService.deleteItem(id);

        return "redirect:/dashboard?success=deleted";
    }

    // ---------------- EDIT PAGE (FIXED TYPE) ----------------
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model) throws IOException {

        Item item = fileService.findById(id);

        if (item == null) {
            return "redirect:/dashboard?error=notfound";
        }

        model.addAttribute("item", item);
        return "edit-item";
    }

    // ---------------- UPDATE ITEM ----------------
    @PostMapping("/update")
    public String updateItem(@ModelAttribute Item item) throws IOException {

        fileService.updateItem(item);

        return "redirect:/dashboard?success=updated";
    }

    // ---------------- PROFILE ----------------
    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    // ---------------- REPORT ----------------
    @GetMapping("/report")
    public ResponseEntity<FileSystemResource> downloadReport() throws Exception {

        String filePath = fileService.generatePdfReport();

        File file = new File(filePath);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=inventory-report.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_PDF)
                .body(new FileSystemResource(file));
    }
}