# 🧮 Univocal Calculator

> A modern, cross-platform desktop calculator built with JavaFX featuring clean design and professional architecture.

[![Java](https://img.shields.io/badge/Java-24%2B-orange.svg)](https://openjdk.java.net/)
[![JavaFX](https://img.shields.io/badge/JavaFX-24-blue.svg)](https://openjfx.io/)
[![Maven](https://img.shields.io/badge/Maven-3.6%2B-red.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](https://opensource.org/licenses/MIT)

![Calculator Screenshot](https://via.placeholder.com/400x600/f8f9fa/2c3e50?text=Calculator+UI)

## ✨ Features

- 🎨 **Clean minimalist design** with modern white interface
- 🧮 **Complete mathematical operations** (basic + advanced functions)
- ⌨️ **Dual input support** (mouse clicks + keyboard shortcuts)
- 🌍 **Cross-platform** (Windows, macOS, Linux)
- 📦 **Self-contained executable** (no JavaFX installation required)
- 🏗️ **Professional architecture** following SOLID principles

## 🚀 Quick Start

### For Users

1. **Download** the latest `Calculator-1.0-SNAPSHOT.jar` from releases
2. **Run** the calculator:
   ```bash
   java -jar Calculator-1.0-SNAPSHOT.jar
   ```

### For Developers

```bash
# Clone the repository
git clone https://github.com/univocal/javafx-calculator.git
cd javafx-calculator

# Run in development mode
mvn javafx:run

# Build executable JAR
mvn clean package
```

## 📖 Usage

### Basic Operations
- **Numbers**: Click 0-9 or type on keyboard
- **Operators**: +, −, ×, ÷ for basic arithmetic
- **Equals**: = button or Enter key to calculate
- **Clear**: C button or Escape key to reset

### Advanced Functions
| Function | Button | Example | Result |
|----------|--------|---------|--------|
| Square Root | √ | 9 → √ | 3 |
| Square | x² | 5 → x² | 25 |
| Reciprocal | 1/x | 4 → 1/x | 0.25 |
| Percentage | % | 50 → % | 0.5 |
| Factorial | x! | 5 → x! | 120 |

### Keyboard Shortcuts
| Key | Function | Key | Function |
|-----|----------|-----|----------|
| `0-9` | Numbers | `+` | Addition |
| `.` | Decimal | `-` | Subtraction |
| `*` | Multiply | `/` | Division |
| `Enter` | Equals | `Escape` | Clear |
| `Backspace` | Delete | `Delete` | Clear Entry |

## 🏗️ Architecture

### Package Structure
```
org.univocal/
├── application/     # App entry point & launcher
├── controller/      # Business logic coordination  
├── logic/          # Pure mathematical operations
├── models/         # Data transfer objects
├── ui/             # User interface components
└── utils/          # Enums and utilities
```

### Design Principles
- ✅ **Single Responsibility**: Each class has one clear purpose
- ✅ **Separation of Concerns**: UI, business logic, and data separated
- ✅ **Interface Segregation**: Clean contracts between components
- ✅ **Dependency Inversion**: Abstractions over concrete implementations

## 💻 Development

### Prerequisites
- **Java JDK 11+** ([Download](https://adoptium.net/))
- **Maven 3.6+** ([Download](https://maven.apache.org/download.cgi))
- **IDE** (IntelliJ IDEA, Eclipse, or VS Code)

### Build Commands
```bash
# Development
mvn clean compile          # Compile only
mvn javafx:run            # Run in development mode
mvn test                  # Run tests

# Production
mvn clean package         # Create executable JAR
mvn clean install         # Install to local repository
```

### Project Structure
```
javafx-calculator/
├── pom.xml                           # Maven configuration
├── README.md                         # This file
├── LICENSE.txt                       # MIT License
└── src/
    ├── main/java/org/univocal/      # Source code
    └── test/java/org/univocal/      # Unit tests
```

## 📦 Distribution

### Executable JAR
The project creates a self-contained JAR with all dependencies:
- **File**: `target/Calculator-1.0-SNAPSHOT.jar`
- **Size**: ~50-60MB (includes JavaFX runtime)
- **Requirements**: Java 11+ on target machine

### Platform Support
| Platform | Status | Notes |
|----------|--------|-------|
| Windows 10+ | ✅ | Fully supported |
| macOS 10.14+ | ✅ | Fully supported |
| Linux Ubuntu 18.04+ | ✅ | Fully supported |

## 🧪 Testing

### Running Tests
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=CalculatorTest

# Run with coverage
mvn test jacoco:report
```

### Test Categories
- **Unit Tests**: Mathematical operations validation
- **Integration Tests**: Controller and UI component interaction
- **UI Tests**: Button and keyboard input verification

## 🤝 Contributing

We welcome contributions! Please follow these steps:

1. **Fork** the repository
2. **Create** a feature branch (`git checkout -b feature/amazing-function`)
3. **Commit** your changes (`git commit -m 'Add amazing function'`)
4. **Push** to the branch (`git push origin feature/amazing-function`)
5. **Open** a Pull Request

### Contribution Guidelines
- Follow existing code style and patterns
- Add comprehensive tests for new features
- Update documentation for new functionality
- Ensure all tests pass before submitting PR

## 📄 License

This project is licensed under the **MIT License** - see the [LICENSE.txt](LICENSE.txt) file for details.

## 📞 Support

- 🐛 **Bug Reports**: [GitHub Issues](https://github.com/univocal/javafx-calculator/issues)
- 💬 **Questions**: [GitHub Discussions](https://github.com/univocal/javafx-calculator/discussions)
- 📧 **Email**: dev@univocal.org
- 🌐 **Website**: [univocal.org](https://www.univocal.org)

## 🎯 Roadmap

### Version 1.1 (Planned)
- [ ] Memory functions (MS, MR, MC, M+)
- [ ] Scientific notation display toggle
- [ ] History of calculations
- [ ] Themes and customization

### Version 2.0 (Future)
- [ ] Graphing capabilities
- [ ] Unit conversions
- [ ] Programmable functions
- [ ] Multi-calculator tabs

## 🙏 Acknowledgments

- **JavaFX Team** for the excellent UI framework
- **Maven Community** for build automation tools
- **Open Source Community** for inspiration and best practices

---

<div align="center">

**Built with ❤️ by the Univocal Team**

[Website](https://www.univocal.org) • [Issues](https://github.com/univocal/javafx-calculator/issues) • [Releases](https://github.com/univocal/javafx-calculator/releases)

</div>