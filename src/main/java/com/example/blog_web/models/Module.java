package com.example.blog_web.models;

/**
 * Entity đại diện cho một học phần (Module) trong hệ thống.
 * <p>
 * Một module chứa chủ đề, mô tả và trạng thái tiến độ hiện tại của người dùng.
 * </p>
 */
public class Module {
    private Long id;
    private String name;
    private String description;
    private String status; // Example : "Completed", "In Progress", "Not Started"

    public Module(Long id, String name, String description, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
