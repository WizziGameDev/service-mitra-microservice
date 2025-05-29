package com.mitra.koptani.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MitraRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be at most 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9+\\- ]{6,20}$", message = "Phone number format is invalid")
    private String phoneNumber;

    @NotBlank(message = "Address is required")
    @Size(max = 255, message = "Address must be at most 255 characters")
    private String address;

    @NotBlank(message = "Type is required")
    @Pattern(regexp = "supplier|distributor|retailer", flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Type must be supplier, distributor, or retailer")
    private String type;

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "active|nonactive", flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Status must be active or nonactive")
    private String status;
}
