package algorithms

interface ImageCreating {
    /**
     * Creates a 2 dimensional array representing the image with each string representing a pixel of
     * a hexadecimal color.
     */
    fun createImage(): Array<Array<String>>
}