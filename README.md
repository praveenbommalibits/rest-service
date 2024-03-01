# Your Task
Our company has released a beta version of **String Reply Service** and it has been a huge success.
In the current implementation (as part of boilerplate code), the **String Reply Service** takes in an input string (in the format of `[a-z0-9]*`)
and returns the input in a JSON object.

For example,

```
GET /reply/kbzw9ru
{
    "data": "kbzw9ru"
}
```

As the service is widely adopted, there have been increasing feature requests.
Our project manager has come back with the following requirements for V2 of the service:

The input string will now be comprised of two components, a rule and a string, separated by a dash (-).
Rules **always** contain two numbers. Each number represents a string operation.

The supported numbers are:

- `1`: reverse the string

   E.g. `kbzw9ru` becomes `ur9wzbk`

- `2`: encode the string via MD5 hash algorithm and display as hex

   E.g. `kbzw9ru` becomes `0fafeaae780954464c1b29f765861fad`

The numbers are applied in sequence, i.e. the output of the first rule will
serve as the input of the second rule. The numbers can also be repeated,
i.e. a rule of 11 would mean reversing the string twice, resulting in no change to the string.

Giving a few examples,

```
GET /v2/reply/11-kbzw9ru
{
    "data": "kbzw9ru"
}
```
```
GET /v2/reply/12-kbzw9ru
{
    "data": "5a8973b3b1fafaeaadf10e195c6e1dd4"
}
```
```
GET /v2/reply/22-kbzw9ru
{
    "data": "e8501e64cf0a9fa45e3c25aa9e77ffd5"
}
```

## What you need to do
Use the boilerplate given and implement the above requirement.
Your implementation should also consider:

- Maintain the existing endpoint for backward compatibility.
- Implement V2 endpoint for the above new requirements.
- Additional rules are expected in future releases. The updates in rule set
should have minimal code changes and impact to existing functionality.
- Testability for individual rule and the application.
Unit tests are highly recommended.
- Endpoints should return correct status code and response message.
For invalid request, it should return status code `400`
with message `"Invalid input"`, for example:
   ```
   GET /v2/reply/13-kbzw9ru
   {
       "message": "Invalid input"
   }
   ```

Upon completing the task, please feel free to (though not required):

- host your code on Github
- include any readme to explain your setup/environment
- add/implement anything you think would be beneficial

## Build project

To build the project, simply run
```
./gradlew build
```

## Start project

To start the project, simply run
```
./gradlew bootRun
```

Once the service started, the endpoint will be available at `localhost:8080`, so you can make request to the service endpoint

```json
GET localhost:8080/reply/helloworld

{
    message: "helloword"
}
```

# String Reply Service V2 API

The String Reply Service V2 API provides enhanced functionality to process input strings based on specified rules and return the processed output. This version supports additional rules and provides a modular and extensible architecture to accommodate future requirements.

## Overview

The String Reply Service V2 API builds upon the functionality of the V1 API by introducing support for processing input strings based on specified rules. Rules are applied sequentially to the input string, with each rule representing a string operation. Currently, the supported rules are:

- Rule 1: Reverse the string.
- Rule 2: Encode the string via MD5 hash algorithm and display as hex.
`In future we can introduce new rules, we can able to adopt them with minimal code changes`

## Endpoints

### V2 Endpoint

- **GET /v2/reply/{rule}-{input}**: Processes the input string based on the specified rule and returns the processed output.

Supported rules:
- `1`: Reverse the string.
- `2`: Encode the string via MD5 hash algorithm and display as hex.

Example usage:
```
GET /v2/reply/11-kbzw9ru
{
"data": "ur9wzbk"
}

GET /v2/reply/12-kbzw9ru
{
"data": "0fafeaae780954464c1b29f765861fad"
}
```


## Implementation Details

### Controller

The `ReplyV2Controller` class is responsible for handling requests to the V2 API endpoints. It invokes the `ReplyService` to process the input string based on the specified rule and constructs the response.

### Service

The `ReplyService` class contains the business logic for processing input strings based on rules. It maintains a map of rule processors, where each rule is associated with a specific string operation. This design allows for easy addition of new rule processors to support future requirements.

### Rule Processors

Rule processors are implemented as functions within the `ReplyService` class. Each rule processor performs a specific string operation, such as reversing the string or encoding it via MD5. New rule processors can be added to support additional rules in `future releases`.

### Error Handling

The API performs robust error handling to handle invalid inputs and rules. If an invalid input or rule is provided, the API returns an appropriate error response with a status code of 400 and a message indicating the error.

## Enhancements and Future Support

The String Reply Service V2 API is designed to be extensible and adaptable to future requirements. Key enhancements and features include:

- **Modular Architecture**: The service uses a modular architecture, allowing easy addition of new rule processors and features with minimal code changes.
- **Rule Processor Pattern**: Rules are processed using a rule processor pattern, enabling easy addition of new rule processing logic.
- **Robust Error Handling**: The API provides robust error handling for invalid inputs and rules, ensuring proper validation and error reporting.

## Testing

Unit tests are provided to ensure the correctness of individual rule processing logic and API endpoints. The tests cover various scenarios, including valid inputs, invalid inputs, and invalid rules, to validate the functionality of the service.


## Authors

- Praveen Kumar Bommali
- praveenkumarbommali@gmail.com

