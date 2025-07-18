class TriangleDrawer2 {
    companion object {
        @JvmStatic
        fun drawTriangle() {
            val size = 5
            for (i in 0 until size) {
                for (j in 0 until i + 1) {
                    print("*")
                }
                println()
            }
        }

        @JvmStatic
        fun main() {
            drawTriangle()
        }
    }
}
