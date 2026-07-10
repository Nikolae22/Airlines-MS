package com.payload.request;

import com.domain.AncillaryMetadata;
import com.enums.AncillaryType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AncillaryRequest {


    @NotNull(message = "Ancillary type is requried")
    private AncillaryType type;

    @Size(max = 100, message = "Subtype max 100ch")
    private String subType;

    @Size(max = 10, message = "Subtype max 10ch")
    private String rfisc;

    @NotBlank(message = "Name is required")
    @Size(message =  "Name max 200ch",max = 200)
    private String name;

    @Size(message =  "Description max 1g",max = 1000)
    private String description;

    @Size(message =  "Ircon url  max 500ch",max = 500)
    private String iconUrl;

    private AncillaryMetadata metadata;

    private Integer displayOrder;

}
