package com.akcl.dpms.svc_email.payload;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails {
    public String recipient;
    public String message;
    public String subject;
}
