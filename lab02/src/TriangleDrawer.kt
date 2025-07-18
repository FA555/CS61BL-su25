class TriangleDrawer {
    companion object {
        @JvmStatic
        fun drawTriangle() {
            val size = 5
            var row = 0
            while (row < size) {
                var col = 0
                while (col <= row) {
                    print("*")
                    col++
                }
                println()
                row++
            }
        }

        @JvmStatic
        fun main() {
            drawTriangle()
        }
    }
}
