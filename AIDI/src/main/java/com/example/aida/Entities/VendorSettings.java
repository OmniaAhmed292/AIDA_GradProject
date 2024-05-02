package com.example.aida.Entities;

import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Field;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Embeddable
public class VendorSettings {
    @Field("allow_late_emails")
    private Boolean AllowLateEmails = true;

    @Field("allow_new_emails")
    private Boolean AllowNewEmails = true;
}
