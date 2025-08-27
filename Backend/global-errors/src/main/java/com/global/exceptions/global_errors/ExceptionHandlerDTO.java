package com.global.exceptions.global_errors;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
public class ExceptionHandlerDTO {

    Map<String, String> error;
}
