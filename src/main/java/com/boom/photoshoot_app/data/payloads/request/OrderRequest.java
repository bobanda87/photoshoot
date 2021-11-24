package com.boom.photoshoot_app.data.payloads.request;

import com.boom.photoshoot_app.data.models.OrderState;
import com.boom.photoshoot_app.data.models.PhotoType;
import org.springframework.lang.Nullable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class OrderRequest {
    @NotBlank(message = "Name is mandatory")
    @NotNull
    private String name;

    @NotBlank(message = "Surname is mandatory")
    @NotNull
    private String surname;

    @Email
    @NotBlank(message = "Email is mandatory")
    @NotNull
    private String email;

    @NotBlank(message = "Cell number is mandatory")
    @NotNull
    private String cellNumber;

    @Enumerated(EnumType.STRING)
    @NotBlank(message = "Photo type is mandatory")
    @NotNull
    private PhotoType photoType;

    private String title;

    private String logisticInfo;

    @Nullable
    private LocalDateTime dateTime;

    public OrderRequest() {
    }

    public OrderRequest(String name, String surname, String email, String cellNumber, PhotoType photoType) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.cellNumber = cellNumber;
        this.photoType = photoType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

    public PhotoType getPhotoType() {
        return photoType;
    }

    public void setPhotoType(PhotoType photoType) {
        this.photoType = photoType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogisticInfo() {
        return logisticInfo;
    }

    public void setLogisticInfo(String logisticInfo) {
        this.logisticInfo = logisticInfo;
    }

    @Nullable
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(@Nullable LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
