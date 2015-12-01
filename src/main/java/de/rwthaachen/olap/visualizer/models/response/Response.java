package de.rwthaachen.olap.visualizer.models.response;

/**
 * The super class of all the responses sent back by the Modular Visualization Framework.
 * Extend this class and add details of the responses
 */
public class Response<T> {

    private T responseBodyContent;

    public T getResponseBodyContent() {
        return responseBodyContent;
    }

    public void setResponseBodyContent(T responseBodyContent) {
        this.responseBodyContent = responseBodyContent;
    }
}
