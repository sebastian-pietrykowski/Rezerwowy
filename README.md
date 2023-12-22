# Rezerwowy

The Football Match Reservation System is a Java-based web application developed using the Spring Boot framework. This application allows users to make reservations and purchase tickets for football matches. It uses various technologies, including Spring Boot, Lombok, Spring Data, and testing frameworks like JUnit, AssertJ, and Mockito.

## Table of Contents

- [Features](#features)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Usage](#usage)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)

## Features

The Football Match Reservation System offers the following features:

- User registration and authentication.
- View a list of available football matches.
- Reserve and purchase tickets for matches.
- View reservation history.
- Cancel reservations.

## Getting Started

### Prerequisites

Before you start, ensure you have the following software and tools installed:

- Java Development Kit (JDK) 8 or higher
- Apache Maven
- Git
- Your preferred integrated development environment (IDE), e.g., IntelliJ IDEA or Eclipse

### Installation

1. Clone the repository to your local machine using Git:

   ```bash
   git clone https://github.com/yourusername/football-match-reservation.git
   ```

2. Open the project in your chosen IDE.

3. Build the project using Maven:

   ```bash
   mvn clean install
   ```

4. Run the application:

   ```bash
   mvn spring-boot:run
   ```

The application will be accessible at [http://localhost:8080](http://localhost:8080).

## Usage

1. Register or log in to your account.

2. Browse the list of available football matches.

3. Select a match and choose the number of tickets you want to reserve.

4. Confirm and pay for your reservation.

5. You can view your reservation history and cancel reservations if needed.

## Testing

The project uses various testing libraries for ensuring code quality and correctness:

- JUnit: The primary testing framework for unit and integration tests.
- AssertJ: For fluent and expressive assertions.
- Mockito: For mocking dependencies in unit tests.

To run tests, use the following command:

```bash
mvn test
```

## Contributing

If you'd like to contribute to this project, please follow these steps:

1. Fork the repository on GitHub.

2. Clone your fork to your local machine.

3. Create a new branch for your feature or bug fix.

4. Make your changes and commit them.

5. Push the changes to your fork.

6. Create a pull request to the main repository.

Please make sure to follow the project's coding standards and conventions.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.
