package com.randomimagecreator

import android.content.ContentResolver
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import com.randomimagecreator.common.BitmapCreator
import com.randomimagecreator.common.ImageSaver
import com.randomimagecreator.configuration.Configuration
import com.randomimagecreator.configuration.ImagePattern
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any

class ImageCreatorTests {
    private lateinit var imageSaverMock: ImageSaver
    private lateinit var bitmapCreatorMock: BitmapCreator
    private lateinit var contentResolverMock: ContentResolver
    private lateinit var documentFileMock: DocumentFile
    private lateinit var imageCreator: ImageCreator
    private lateinit var saveDirectoryMock: Uri

    @Before
    fun before() {
        imageSaverMock = mockk()
        every { imageSaverMock.saveBitmap(any(), any(), any(), any()) }.answers { mockk() }
        bitmapCreatorMock = mockk()
        every { bitmapCreatorMock.create(any(), any(), any()) }.answers { mockk() }
        bitmapCreatorMock.create(any(), any(), any())
        contentResolverMock = mockk()
        documentFileMock = mockk()
        saveDirectoryMock = mockk()
        imageCreator = ImageCreator(imageSaverMock, bitmapCreatorMock)
    }

    @Test
    fun `create, happy case, returns success result`() = runTest {
        val configuration = createValidConfiguration()
        val result = imageCreator.create(contentResolverMock, documentFileMock, configuration)
        Assert.assertTrue(result.isSuccess)
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `create, happy case, correctly emits image saves`() = runTest(UnconfinedTestDispatcher()) {
        val configuration = createValidConfiguration()

        var nrOfEmits = 0
        backgroundScope.launch {
            imageCreator.bitmapSafeNotifier.collectLatest {
                nrOfEmits++
            }
        }
        imageCreator.create(contentResolverMock, documentFileMock, configuration)
        Assert.assertEquals(2, nrOfEmits)
    }

    @Test
    fun `create, when image creation algorithm throws an exception, returns image creating algorithm error`() =
        runTest {
            val configuration = createInvalidConfiguration()
            val result = imageCreator.create(contentResolverMock, documentFileMock, configuration)
            Assert.assertTrue(result.exceptionOrNull() is ImageCreatingAlgorithmError)
        }

    @Test
    fun `create, when algorithm conversion to Android bitmap throws an exception, returns bitmap creation error`() =
        runTest {
            every {
                bitmapCreatorMock.create(
                    any(),
                    any(),
                    any()
                )
            }.throws(IllegalArgumentException())
            val configuration = createValidConfiguration()
            val result = imageCreator.create(contentResolverMock, documentFileMock, configuration)
            Assert.assertTrue(result.exceptionOrNull() is BitmapCreationError)
        }

    @Test
    fun `create, when bitmap saving on device throws an exception, returns bitmap creation error`() =
        runTest {
            every { imageSaverMock.saveBitmap(any(), any(), any(), any()) }.throws(Exception())
            val configuration = createValidConfiguration()
            val result = imageCreator.create(contentResolverMock, documentFileMock, configuration)
            Assert.assertTrue(result.exceptionOrNull() is BitmapSaveError)
        }

    private fun createValidConfiguration() =
        Configuration(2, 24, 24, saveDirectory = saveDirectoryMock)

    private fun createInvalidConfiguration() = Configuration(
        2,
        1,
        1,
        pattern = ImagePattern.SIERPINSKI_CARPET,
        saveDirectory = saveDirectoryMock
    )
}