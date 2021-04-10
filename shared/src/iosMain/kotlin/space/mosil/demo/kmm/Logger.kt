package space.mosil.demo.kmm

internal actual class Logger {
    actual fun info(tag: String, message: String) {
        print("Log info >>>>> $tag: $message")
    }

    actual fun debug(tag: String, message: String) {
        print("Log Debug >>>>> $tag: $message")
    }

    actual fun warn(tag: String, message: String) {
        print("Log Warn >>>>> $tag: $message")
    }

    actual fun error(tag: String, message: String) {
        print("Log Error >>>>> $tag: $message")
    }
}