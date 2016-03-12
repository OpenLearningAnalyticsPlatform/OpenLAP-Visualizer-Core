# OpenLAPVisualizer

## Introduction

The OpenLAP-Visualizer-Core project contains the endpoints which make up the REST API of the Visualizer component. In addition, this project also includes the Service and DataAccess layer
of the Visualizer. At runtime the clients can use the REST API of the Visualizer to perform various operations such as upload new `VisualizationFrameworks` as stated in the guide here, or 
perform CRUD operations on Visualization Suggestions and Methods. 

## REST API
The following table lists the endpoints exposed by the Visualizer.

# Spring Artifacts

* * *

<div id="components">

## Components

### Component

| [de.rwthaachen.openlap.visualizer.core.service.DBConfiguration](./de/rwthaachen/openlap/visualizer/core/service/DBConfiguration.html) | `Development configuration class which makes it possible to access h2 database web console` |

### Service

| [de.rwthaachen.openlap.visualizer.core.service.ConfigurationService](./de/rwthaachen/openlap/visualizer/core/service/ConfigurationService.html) | `Service which contains the configuration parameters of the System.` |
| [de.rwthaachen.openlap.visualizer.core.service.FileManager](./de/rwthaachen/openlap/visualizer/core/service/FileManager.html) | `A service which provides functions to perform operations on the system's storage.` |
| [de.rwthaachen.openlap.visualizer.core.service.VisualizationEngineService](./de/rwthaachen/openlap/visualizer/core/service/VisualizationEngineService.html) | `Service which provides methods to generate visualization code.` |
| [de.rwthaachen.openlap.visualizer.core.service.VisualizationFrameworkService](./de/rwthaachen/openlap/visualizer/core/service/VisualizationFrameworkService.html) | `A service which provides functions to perform CRUD operations on the Visualization Frameworks or Visualization Methods.` |
| [de.rwthaachen.openlap.visualizer.core.service.VisualizationSuggestionService](./de/rwthaachen/openlap/visualizer/core/service/VisualizationSuggestionService.html) | `A service which provides functions to perform CRUD operations on the VisualizationSuggestions` |

</div>

* * *

<div id="request-mappings">

## Request Mappings

| Method | URL Template | Class | Description |
| GET | "/frameworks/list" | [de.rwthaachen.openlap.visualizer.core.controller.VisualizationFrameworkController](./de/rwthaachen/openlap/visualizer/core/controller/VisualizationFrameworkController.html) |
| POST | "/frameworks/upload" | [de.rwthaachen.openlap.visualizer.core.controller.VisualizationFrameworkController](./de/rwthaachen/openlap/visualizer/core/controller/VisualizationFrameworkController.html) |
| DELETE | "/frameworks/{idOfFramework}" | [de.rwthaachen.openlap.visualizer.core.controller.VisualizationFrameworkController](./de/rwthaachen/openlap/visualizer/core/controller/VisualizationFrameworkController.html) |
| GET | "/frameworks/{idOfFramework}" | [de.rwthaachen.openlap.visualizer.core.controller.VisualizationFrameworkController](./de/rwthaachen/openlap/visualizer/core/controller/VisualizationFrameworkController.html) |
| GET | "/frameworks/{idOfFramework}/methods/{idOfMethod}" | [de.rwthaachen.openlap.visualizer.core.controller.VisualizationFrameworkController](./de/rwthaachen/openlap/visualizer/core/controller/VisualizationFrameworkController.html) |
| PUT | "/frameworks/{idOfFramework}/methods/{idOfMethod}" | [de.rwthaachen.openlap.visualizer.core.controller.VisualizationFrameworkController](./de/rwthaachen/openlap/visualizer/core/controller/VisualizationFrameworkController.html) | `update only the attributes such as description, data transformer of the method` |
| GET | "/frameworks/{idOfFramework}/methods/{idOfMethod}/configuration" | [de.rwthaachen.openlap.visualizer.core.controller.VisualizationFrameworkController](./de/rwthaachen/openlap/visualizer/core/controller/VisualizationFrameworkController.html) |
| POST | "/frameworks/{idOfFramework}/methods/{idOfMethod}/validateConfiguration" | [de.rwthaachen.openlap.visualizer.core.controller.VisualizationFrameworkController](./de/rwthaachen/openlap/visualizer/core/controller/VisualizationFrameworkController.html) |
| PUT | "/frameworks/{idOfFramework}/update" | [de.rwthaachen.openlap.visualizer.core.controller.VisualizationFrameworkController](./de/rwthaachen/openlap/visualizer/core/controller/VisualizationFrameworkController.html) |
| POST | "/generateVisualizationCode" | [de.rwthaachen.openlap.visualizer.core.controller.VisualizationEngineController](./de/rwthaachen/openlap/visualizer/core/controller/VisualizationEngineController.html) |
| POST | "/suggestions/list" | [de.rwthaachen.openlap.visualizer.core.controller.VisualizationSuggestionController](./de/rwthaachen/openlap/visualizer/core/controller/VisualizationSuggestionController.html) |
| POST | "/suggestions/new" | [de.rwthaachen.openlap.visualizer.core.controller.VisualizationSuggestionController](./de/rwthaachen/openlap/visualizer/core/controller/VisualizationSuggestionController.html) |
| GET | "/suggestions/{idOfSuggestion}" | [de.rwthaachen.openlap.visualizer.core.controller.VisualizationSuggestionController](./de/rwthaachen/openlap/visualizer/core/controller/VisualizationSuggestionController.html) |
| PUT | "/suggestions/{idOfSuggestion}" | [de.rwthaachen.openlap.visualizer.core.controller.VisualizationSuggestionController](./de/rwthaachen/openlap/visualizer/core/controller/VisualizationSuggestionController.html) |
| DELETE | "/suggestions/{idOfSuggestion}" | [de.rwthaachen.openlap.visualizer.core.controller.VisualizationSuggestionController](./de/rwthaachen/openlap/visualizer/core/controller/VisualizationSuggestionController.html) |
| GET | "index" | [de.rwthaachen.openlap.visualizer.core.controller.ClientController](./de/rwthaachen/openlap/visualizer/core/controller/ClientController.html) |

</div>

* * *

Generated by [SpringDoclet](http://scottfrederick.github.com/springdoclet/)



