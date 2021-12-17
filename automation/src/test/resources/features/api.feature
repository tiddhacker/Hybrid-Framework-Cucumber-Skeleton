@API
Feature: API Automation Demo

  @pathParameter
  Scenario Outline: GET call
    Given baseURI is set
    And path parameter "<pathParameter>" is set
    And validates the response "<statusCode>"
    And validates response body id "<id>"

    Examples: 
      | pathParameter | statusCode | id |
      | /api/users/2  |        200 |  2 |

  # to run this scenario use base uri : https://samples.openweathermap.org/data/2.5
  @queryParameter
  Scenario Outline: GET call with query parameter in request
    Given baseURI is set
    And query parameter "<queryParameter>" is set
    And path parameter "<pathParameter>" is set
    And validates the response "<statusCode>"

    Examples: 
      | pathParameter | statusCode | id | queryParameter                                     |
      | /weather      |        200 |  2 | q=London,uk&appid=2b1fd2d7f77ccf1b7de9b441571b39b8 |

  @delayedResponse
  Scenario Outline: GET call test delayed response
    Given baseURI is set
    And query parameter "<queryParameter>" is set
    And path parameter "<pathParameter>" is set
    And validates the response "<statusCode>"

    Examples: 
      | pathParameter | statusCode | id | queryParameter |
      | /api/users    |        200 |  2 | delay=10       |
