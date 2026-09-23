package dev.kasckepperd.finance;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
        private CategoryRepository categoryRepository;

        public CategoryController(CategoryRepository categoryRepository) {
            this.categoryRepository = categoryRepository;
        }


        @PostMapping("/register")
        public Category register(@RequestBody Category category) {
            return categoryRepository.save(category);
        }

        @GetMapping("/show")
        public List<Category> show() {
            return categoryRepository.findAll();
        }
}
