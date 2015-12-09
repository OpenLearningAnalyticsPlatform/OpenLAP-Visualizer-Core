package de.rwthaachen.olap.visualizer.dtos.error;

import de.rwthaachen.olap.visualizer.Application;
import de.rwthaachen.olap.visualizer.exceptions.BaseException;

import java.time.LocalDateTime;

/**
 * A base class which represents the model(contents) of the error that will be sent back to the client.
 * For further modifications inherit this class.
 */
public class BaseErrorDTO {

    private BaseException errorDetails; // the internal details of the error
    private String errorMessage; // a human readable message summarizing the cause of the error
    private String moreInfoURL; // a url pointing to more information on the error
    private String apiVersionNumber; // representing the current version number of the API that the System is running on
    private String timestamp; // current timestamp

    protected BaseErrorDTO(BaseException errorDetails, String errorMessage, String moreInfoURL, String apiVersionNumber) {
        this.errorDetails = errorDetails;
        this.errorMessage = errorMessage;
        this.moreInfoURL = moreInfoURL;
        this.apiVersionNumber = apiVersionNumber;
        this.timestamp = LocalDateTime.now().toString();
    }

    public static BaseErrorDTO createBaseErrorDTO(BaseException errorDetails, String errorMessage, String moreInfoURL, String apiVersionNumber){
        if(apiVersionNumber==null || apiVersionNumber.isEmpty())
            apiVersionNumber = Application.API_VERSION_NUMBER;//use the default api version number
        return new BaseErrorDTO(errorDetails, errorMessage, moreInfoURL, apiVersionNumber);
    }

    public BaseException getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(BaseException errorDetails) {
        this.errorDetails = errorDetails;
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

    public String getTimestamp() {
        return timestamp;
    }
}
