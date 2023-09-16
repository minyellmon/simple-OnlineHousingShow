package com.example.onlinehousingshowsample.model.dto.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class OwnerForm {
    private String ownerUserName;
    private String ownerName;
    private String ownerEmail;
    private String ownerPassword;

    public OwnerForm() {
    }

    public OwnerForm(String ownerUserName, String ownerName, String ownerEmail, String ownerPassword) {
        this.ownerUserName = ownerUserName;
        this.ownerName = ownerName;
        this.ownerEmail = ownerEmail;
        this.ownerPassword = ownerPassword;
    }
}
