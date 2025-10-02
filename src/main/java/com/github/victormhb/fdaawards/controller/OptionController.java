package com.github.victormhb.fdaawards.controller;

import com.github.victormhb.fdaawards.dto.option.OptionDTO;
import com.github.victormhb.fdaawards.dto.option.OptionUpdateDTO;
import com.github.victormhb.fdaawards.model.Option;
import com.github.victormhb.fdaawards.service.OptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/options")
public class OptionController {

    private OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OptionDTO> updateOption(@PathVariable Long id, @RequestBody OptionUpdateDTO dto) {
        OptionDTO updatedOption = optionService.updateOption(id, dto);
        return ResponseEntity.ok(updatedOption); //Retorna 200
    }
}
