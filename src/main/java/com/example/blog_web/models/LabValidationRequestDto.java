package com.example.blog_web.models;

import jakarta.validation.constraints.NotBlank;

public class LabValidationRequestDto {
    @NotBlank(message = "Lab type is required")
    private String labType;

    @NotBlank(message = "Input is required")
    private String input;

    public String getLabType() {
        return labType;
    }

    public void setLabType(String labType) {
        this.labType = labType;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }
}
