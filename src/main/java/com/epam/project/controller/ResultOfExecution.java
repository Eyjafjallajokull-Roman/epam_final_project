package com.epam.project.controller;

import com.epam.project.SendEmail;

/**
 * Object of ResultOfExecution Class is result of Command execution that contains page and Direction
 */
public class ResultOfExecution {

    private String page;
    private Direction direction;
    private boolean isInvalidate;
    private SendEmail sendEmail;

    public String getPage() {
        return page;
    }

    public ResultOfExecution setPage(String page) {
        this.page = page;
        return this;
    }

    public Direction getDirection() {
        return direction;
    }

    public ResultOfExecution setDirection(Direction direction) {
        this.direction = direction;
        return this;
    }

    public boolean isInvalidate() {
        return isInvalidate;
    }

    public ResultOfExecution setInvalidate(boolean invalidate) {
        isInvalidate = invalidate;
        return this;
    }

    public void invalidateSession() {
        isInvalidate = true;
    }

    public SendEmail getSendEmail() {
        return sendEmail;
    }

    public ResultOfExecution setSendEmail(SendEmail sendEmail) {
        this.sendEmail = sendEmail;
        return this;
    }
}
