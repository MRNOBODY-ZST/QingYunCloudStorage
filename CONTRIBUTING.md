# Contributing to [Project Name]

Thank you for your interest in contributing to our project! We welcome contributions from the community and are pleased to have you join us. This document provides guidelines and steps for contributing.

## Table of Contents
- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
- [Development Setup](#development-setup)
- [Making Changes](#making-changes)
- [Submitting Changes](#submitting-changes)
- [Code Style](#code-style)
- [Testing Guidelines](#testing-guidelines)
- [Documentation](#documentation)

## Code of Conduct

By participating in this project, you agree to abide by our [Code of Conduct](CODE_OF_CONDUCT.md). Please read it before contributing.

## Getting Started

1. Fork the repository
2. Clone your fork:
   ```bash
   git clone https://github.com/your-username/QingYunCloudStorage.git
   cd QingYunCloudStorage
   ```
3. Add the upstream repository:
   ```bash
   git remote add upstream https://github.com/MRNOBODY-ZST/QingYunCloudStorage.git
   ```
4. Create a new branch for your changes:
   ```bash
   git checkout -b feature/your-feature-name
   ```

## Development Setup

### Prerequisites
- JDK 17 or later
- Gradle 7.x or later
- Your favorite IDE (IntelliJ IDEA recommended)

### Building the Project
1. Navigate to the project directory
2. Run:
   ```bash
   ./gradlew clean build
   ```

## Making Changes

1. Make sure your branch is up to date:
   ```bash
   git fetch upstream
   git rebase upstream/main
   ```
2. Make your changes
3. Follow the [Code Style](#code-style) guidelines
4. Add tests for new functionality
5. Ensure all tests pass:
   ```bash
   ./gradlew test
   ```

## Submitting Changes

1. Commit your changes:
   ```bash
   git add .
   git commit -m "Brief description of your changes"
   ```
2. Push to your fork:
   ```bash
   git push origin feature/your-feature-name
   ```
3. Create a Pull Request:
   - Go to the original repository
   - Click "New Pull Request"
   - Select your fork and branch
   - Fill in the PR template
   - Submit the PR

### Pull Request Guidelines
- Use a clear, descriptive title
- Include the purpose of changes and context
- Reference any related issues
- Update documentation as needed
- Ensure all checks pass

## Code Style

- Follow standard Java coding conventions
- Use 4 spaces for indentation
- Keep lines under 120 characters
- Write clear, descriptive variable and method names
- Add comments for complex logic
- Follow Spring Boot best practices
- Use lombok annotations where appropriate
- Write clean, maintainable code

## Testing Guidelines

- Write unit tests for new functionality
- Maintain test coverage above 80%
- Use meaningful test names
- Follow the AAA pattern (Arrange, Act, Assert)
- Mock external dependencies
- Test edge cases and error scenarios

## Documentation

- Update README.md if needed
- Document new features or API changes
- Include Javadoc for public methods
- Update API documentation if applicable
- Add comments explaining complex logic

## Questions or Problems?

- Check existing issues
- Create a new issue with a clear description
- Join our community discussions
- Contact the maintainers

Thank you for contributing to our project! Your efforts help make this project better for everyone.
