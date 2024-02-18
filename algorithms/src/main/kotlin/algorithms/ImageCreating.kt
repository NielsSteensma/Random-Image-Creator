package algorithms

interface ImageCreating {
    /**
     * Creates a 2 dimensional array representing the Bitmap with each string representing
     * hexadecimal color.
     */
    fun createBitmap(): Array<Array<String>>
}