package de.rwthaachen.olap.visualizer.models.error;

/**
 * A base class which represents the model(contents) of the error that will be sent back to the client.
 * For further modifications extend this class.
 */
public class BaseError {

    private String httpStatusCode; // the HTTP status code of the error
    private String errorMessage; // a human readable message summarizing the cause of the error
    private String moreInfoURL; // a url pointing to more information on the error
    private String apiVersionNumber; // representing the current version number of the API that the System is running on

    public BaseError() {
    }

    public BaseError(String httpStatusCode, String errorMessage, String moreInfoURL, String apiVersionNumber) {
        this.httpStatusCode = httpStatusCode;
        this.errorMessage = errorMessage;
        this.moreInfoURL = moreInfoURL;
        this.apiVersionNumber = apiVersionNumber;
    }

    public String getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(String httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getMoreInfoURL() {
        return moreInfoURL;
    }

    public void setMoreInfoURL(String moreInfoURL) {
        this.moreInfoURL = moreInfoURL;
    }

    public String getApiVersionNumber() {
        return apiVersionNumber;
    }

    public void setApiVersionNumber(String apiVersionNumber) {
        this.apiVersionNumber = apiVersionNumber;
    }
}
