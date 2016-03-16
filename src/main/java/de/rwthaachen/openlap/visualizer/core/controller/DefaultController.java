package de.rwthaachen.openlap.visualizer.core.controller;

/**
 * Controller which contains the code to send as a response for any unmapped requests
@RestController
public class DefaultController {
    @RequestMapping("/**")
    public void unmappedRequest(HttpServletRequest request) {
        String uri = request.getRequestURI();
        throw new UnmappedURIException("The endpoint " + uri + " is not supported");
    }

    @ExceptionHandler(UnmappedURIException.class)
    public ResponseEntity<Object> handleUnmappedURIException(UnmappedURIException exception, HttpServletRequest request) {
        //create a client response
        BaseErrorDTO error = BaseErrorDTO.createBaseErrorDTO(exception.getMessage(),"","");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(error,headers,HttpStatus.NOT_IMPLEMENTED);
    }
}
*/