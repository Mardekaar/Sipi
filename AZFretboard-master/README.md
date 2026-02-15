# Fretboard Viewer – Build Instructions

This document explains how to compile **Fretboard Viewer** into a native executable/installer for **macOS, Windows, and Linux** using `jpackage` (JDK 14+).

> ✅ The **same JAR file** works on all platforms – you only need to run `jpackage` **on the target OS**.

---

## 📦 Prerequisites (All Platforms)

1. **JDK 14 or later** – `jpackage` is included.  
   - Download from [Adoptium](https://adoptium.net/) or [Oracle](https://www.oracle.com/java/technologies/downloads/).
2. **Your compiled JAR file** – e.g., `FretboardViewer.jar`.  
   - Export from IntelliJ:  
     `File` → `Project Structure` → `Artifacts` → `+` → `JAR` → `From modules with dependencies` → choose `FretboardViewer` as main class.

---

## 🍎 macOS

### Build a standalone `.app` bundle
```bash
cd /path/to/your/jar-folder
jpackage --input . \
         --dest ~/Desktop \
         --temp /tmp/jpackage-temp \
         --name "FretboardViewer" \
         --main-jar FretboardViewer.jar \
         --main-class FretboardViewer \
         --type app-image
```
- Output: `~/Desktop/FretboardViewer.app` – double‑click to run.  
- Rename the `.app` in Finder if you want spaces (e.g., `AZ Fretboard.app`).

### Build a `.dmg` installer
```bash
jpackage --input . --dest ~/Desktop --name "FretboardViewer" \
         --main-jar FretboardViewer.jar --main-class FretboardViewer \
         --type dmg
```

---

## 🪟 Windows

### Prerequisites
- **WiX Toolset v3.0 or later** – [Download](https://wixtoolset.org/)
- After installing, add `"C:\Program Files (x86)\WiX Toolset v3\bin"` to your `PATH`.

### Build an `.exe` launcher
```cmd
cd C:\path\to\your\jar-folder
jpackage --input . --dest . --name "FretboardViewer" ^
         --main-jar FretboardViewer.jar --main-class FretboardViewer ^
         --win-shortcut --win-menu
```

### Build an `.msi` installer
```cmd
jpackage --input . --dest . --name "FretboardViewer" ^
         --main-jar FretboardViewer.jar --main-class FretboardViewer ^
         --type msi --win-shortcut --win-menu
```

- Output: `FretboardViewer.exe` or `FretboardViewer.msi` in the current folder.

---

## 🐧 Linux

### Prerequisites
- **Debian/Ubuntu**: `sudo apt install fakeroot`
- **Fedora/RHEL**: `sudo dnf install rpm-build`

### Build a `.deb` package (Ubuntu/Debian)
```bash
cd /path/to/your/jar-folder
jpackage --input . --dest . --name "fretboardviewer" \
         --main-jar FretboardViewer.jar --main-class FretboardViewer \
         --type deb --linux-shortcut
```

### Build an `.rpm` package (Fedora/RHEL)
```bash
jpackage --input . --dest . --name "fretboardviewer" \
         --main-jar FretboardViewer.jar --main-class FretboardViewer \
         --type rpm --linux-shortcut
```

- Output: `.deb` or `.rpm` file in the current folder.  
- Install with your system’s package manager.

---

## 📦 Distribution Notes

- The generated apps/installers are **self-contained** – the user **does not need Java** installed.
- On **macOS**, first launch may show *“unidentified developer”*.  
  **Fix:** Right‑click → **Open** → **Open** (once).  
- Zip the `.app` before sending to preserve permissions.
- The same JAR works everywhere; you only need to run `jpackage` **natively** on each OS.

---

## ❓ Troubleshooting

| Error | Solution |
|-------|----------|
| `jpackage: command not found` | Use JDK 14+ and ensure `JAVA_HOME` is set. |
| `File name too long` (macOS) | Use `--temp /tmp/...` – never inside `~/Desktop` or project folder. |
| `WiX not found` (Windows) | Install WiX and add it to `PATH`. |
| `fakeroot not found` (Linux) | Install with `sudo apt install fakeroot` (Debian/Ubuntu). |

---
If you encounter any platform‑specific issues, check the official [jpackage documentation](https://docs.oracle.com/en/java/javase/14/jpackage/packaging-overview.html).
