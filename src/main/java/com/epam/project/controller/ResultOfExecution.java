package com.epam.project.controller;
public class ResultOfExecution {

    private String page;
    private Direction direction;
    private boolean isInvalidate;

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



}
