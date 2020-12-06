package com.blog.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class RollDiceController {

    @GetMapping("/roll-dice")
    public String rollDice() {
        return "roll-dice";
    }

    @GetMapping("/roll-dice/{guess}")
    public String rollDice(@PathVariable int guess, Model model){
        model.addAttribute("guess", guess);
        Random random = new Random();
        List rolls = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            rolls.add(random.nextInt(5) + 1);
        }
        model.addAttribute("rolls", rolls);
        int numCorrect = 0;
        for (int j = 0; j < rolls.size(); j++) {
            if (rolls.get(j).equals(guess)) {
                numCorrect++;
            }
        }
        model.addAttribute("numCorrect", numCorrect);

        return "roll-dice";
    }
}
