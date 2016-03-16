package de.rwthaachen.openlap.visualizer.core.controller;

import org.codehaus.jettison.json.JSONException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by bas on 2/11/16.
 */
@RestController
public class ClientController {
    @RequestMapping(value="index",method = RequestMethod.GET, produces = "text/html")
    public String printHello() throws JSONException{
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "    <meta charset=\"utf-8\">" +
                "    <title>Visualizer Client</title>" +
                "    <link rel=\"stylesheet\" href=\"http://localhost:63342/Visualizer_Client/bower_components/bootstrap/dist/css/bootstrap.css\">" +
                "    <link rel=\"stylesheet\" href=\"http://localhost:63342/Visualizer_Client/css/dropzone.css\">" +
                "    <script type=\"text/javascript\" src=\"http://localhost:63342/Visualizer_Client/bower_components/angular/angular.js\"></script>" +
                "    <script type=\"text/javascript\" src=\"http://localhost:63342/Visualizer_Client/bower_components/jquery/dist/jquery.js\"></script>" +
                "    <script type=\"text/javascript\" src=\"http://localhost:63342/Visualizer_Client/js/fileBrowseDropzone.js\"></script>" +
                "    <script type=\"text/javascript\" src=\"http://localhost:63342/Visualizer_Client/js/app.js\"></script>" +
                "    <script type=\"text/javascript\" src=\"http://localhost:63342/Visualizer_Client/bower_components/bootstrap/dist/js/bootstrap.min.js\"></script>" +
                "</head>" +
                "<hr>" +
                "<div id=\"jar_file_drop_zone\">Drop your JAR bundle here!</div>" +
                "<p>Or, Browse and select it!</p>" +
                "<input type=\"file\" id=\"jar_file_select\"/>" +
                "<div class=\"alert hidden\" id=\"status_msg\">" +
                "</div>" +
                "<p>List of frameworks:</p>" +
                "<select id=\"list_of_frameworks\"></select>" +
                "<br/>" +
                "<p>List of Methods:</p>" +
                "<select id=\"list_of_methods\"></select>" +
                "<hr>" +
                "<div class=\"row\">" +
                "    <div class=\"col-xs-6 col-md-6\">" +
                "        <button class=\"btn btn-success btn-block btn-lg\" id=\"generate_vis_code\">Generate Visualization Code</button>" +
                "    </div>" +
                "</div>" +
                "<h2>Vis Code</h2>" +
                "<br/>" +
                "</body>" +
                "</html>");
        return stringBuilder.toString();
    }
}
