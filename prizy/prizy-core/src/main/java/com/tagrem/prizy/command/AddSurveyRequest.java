package com.tagrem.prizy.command;

import com.tagrem.prizy.model.Survey;

public class AddSurveyRequest {

    private final Survey current;
    private final String productId;

    public AddSurveyRequest(Survey latest, String productId) {
        this.productId = productId;
        this.current = latest;
    }

    public Survey getCurrent() {
        return current;
    }

    public String getProductId() {
        return productId;
    }
}
